package com.bow.forest.common.mqlite;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * accept new channel
 * 
 * @author vv
 * @since 2017/5/20.
 */
public class Acceptor extends AbstractServerThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(Acceptor.class);

    private int port = 9001;
    private int sendBufferSize = 1024;
    private int receiveBufferSize = 1024;

    @Override
    public void run() {
        final ServerSocketChannel serverChannel;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.register(getSelector(), SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            LOGGER.error("listener on port " + port + " failed.");
            throw new RuntimeException(e);
        }

        while(isRunning()) {
            int ready = -1;
            try {
                ready = getSelector().select(500L);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            if(ready<=0){
                continue;
            }
            Iterator<SelectionKey> iter = getSelector().selectedKeys().iterator();
            while(iter.hasNext() && isRunning()){
                try {
                    SelectionKey key = iter.next();
                    iter.remove();
                    //
                    if(key.isAcceptable()) {
//                        accept(key,processors[currentProcessor]);
                    }else {
                        throw new IllegalStateException("Unrecognized key state for acceptor thread.");
                    }
                    //
//                    currentProcessor = (currentProcessor + 1) % processors.length;
                } catch (Throwable t) {
                    LOGGER.error("Error in acceptor",t);
                }
            }

        }
    }


    private void accept(SelectionKey key, Processor processor) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        serverSocketChannel.socket().setReceiveBufferSize(receiveBufferSize);
        //
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.socket().setTcpNoDelay(true);
        socketChannel.socket().setSendBufferSize(sendBufferSize);
        //
        processor.accept(socketChannel);
    }
}
