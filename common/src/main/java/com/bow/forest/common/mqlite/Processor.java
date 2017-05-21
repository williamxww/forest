package com.bow.forest.common.mqlite;

import static java.lang.String.format;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.bow.forest.common.mqlite.api.RequestKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A processor is a thread;<br/>
 * Each processor has it's own {@link #selector} , handle read or write event;
 * 
 * @author vv
 * @since 2017/5/20.
 */
public class Processor extends AbstractServerThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class);

    private final BlockingQueue<SocketChannel> newConnections = new ArrayBlockingQueue(10);

    private RequestHandlerFactory requesthandlerFactory;

    public Processor(RequestHandlerFactory requesthandlerFactory) {
        this.requesthandlerFactory = requesthandlerFactory;
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                // setup any new connections that have been queued up
                configureNewConnections();

                final Selector selector = getSelector();
                int ready = selector.select(500);
                if (ready <= 0)
                    continue;
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext() && isRunning()) {
                    SelectionKey key = null;
                    try {
                        key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()) {
                            read(key);
                        } else if (key.isWritable()) {
                            write(key);
                        } else if (!key.isValid()) {
                            close(key);
                        } else {
                            throw new IllegalStateException("Unrecognized key state for processor thread.");
                        }
                    } catch (EOFException eofe) {
                        Socket socket = channelFor(key).socket();
                        LOGGER.debug(format("connection closed by %s:%d.", socket.getInetAddress(), socket.getPort()));
                        close(key);
                    } catch (Throwable t) {
                        Socket socket = channelFor(key).socket();
                        final String msg = "Closing socket for %s:%d becaulse of error %s";
                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.error(format(msg, socket.getInetAddress(), socket.getPort(), t.getMessage()), t);
                        } else {
                            LOGGER.info(format(msg, socket.getInetAddress(), socket.getPort(), t.getMessage()));
                        }
                        close(key);
                    }
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }

        }
    }

    public void accept(SocketChannel socketChannel) {
        newConnections.add(socketChannel);
        getSelector().wakeup();
    }

    /**
     * register read key on selector for new connection;
     * 
     * @throws ClosedChannelException e
     */
    private void configureNewConnections() throws ClosedChannelException {
        while (newConnections.size() > 0) {
            SocketChannel channel = newConnections.poll();
            channel.register(getSelector(), SelectionKey.OP_READ);
            LOGGER.debug("Listening to new connection from " + channel.socket().getRemoteSocketAddress());
        }
    }

    private SocketChannel channelFor(SelectionKey key) {
        return (SocketChannel) key.channel();
    }

    private void close(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        LOGGER.debug("Closing connection from " + channel.socket().getRemoteSocketAddress());
        Utils.closeQuietly(channel.socket());
        Utils.closeQuietly(channel);
        key.attach(null);
        key.cancel();
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = channelFor(key);
        Receive request = null;
        if (key.attachment() == null) {
            request = new BoundedByteBuffer();
            // attach request on the key, in case of can't complete reading once;
            key.attach(request);
        } else {
            request = (Receive) key.attachment();
        }
        int read = request.readFrom(socketChannel);

        if (read < 0) {
            close(key);
        } else if (request.complete()) {
            Send maybeResponse = handle(key, request);
            key.attach(null);
            // if there is a response, send it, otherwise do nothing
            if (maybeResponse != null) {
                key.attach(maybeResponse);
                key.interestOps(SelectionKey.OP_WRITE);
            }
        } else {
            // more reading
            key.interestOps(SelectionKey.OP_READ);
            getSelector().wakeup();
            LOGGER.trace("reading request not been done. " + request);
        }
    }

    private void write(SelectionKey key) throws IOException {
        Send response = (Send) key.attachment();
        SocketChannel socketChannel = channelFor(key);
        response.writeTo(socketChannel);
        if (response.complete()) {
            key.attach(null);
            key.interestOps(SelectionKey.OP_READ);
        } else {
            key.interestOps(SelectionKey.OP_WRITE);
            getSelector().wakeup();
        }
    }

    private Send handle(SelectionKey key, Receive request) {
        final short requestTypeId = request.buffer().getShort();
        final RequestKeys requestType = RequestKeys.valueOf(requestTypeId);
        if (requestType == null) {
            throw new MqLiteException("No mapping found for handler id " + requestTypeId);
        }
        if (LOGGER.isTraceEnabled()) {
            String logFormat = "Handling %s request from %s";
            LOGGER.trace(format(logFormat, requestType, channelFor(key).socket().getRemoteSocketAddress()));
        }
        RequestHandler handler = requesthandlerFactory.mapping(requestType);
        if (handler == null) {
            throw new MqLiteException("No handler found for request");
        }
        Send maybeSend = handler.handle(request);
        return maybeSend;
    }
}
