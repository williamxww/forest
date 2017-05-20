package com.bow.forest.common.mqlite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;

import static java.lang.String.format;

/**
 * sizeBuffer indicate the buffer size; <br/>
 * contentBuffer store business data;
 * 
 * @author vv
 * @since 2017/5/20.
 */
public class BoundedByteBuffer implements Receive, Send {

    private final ByteBuffer sizeBuffer = ByteBuffer.allocate(4);

    private ByteBuffer contentBuffer = null;

    /**
     * finish to read or write;
     */
    private boolean done = false;

    public BoundedByteBuffer() {

    }

    public BoundedByteBuffer(ByteBuffer buffer) {
        this.contentBuffer = buffer;
        sizeBuffer.putInt(buffer.limit());
        sizeBuffer.rewind();
    }

    /**
     * this method maybe call many times;
     * 
     * @param channel
     * @return
     * @throws IOException e
     */
    @Override
    public int readFrom(ReadableByteChannel channel) throws IOException {
        expectIncomplete();

        int read = 0;
        if (sizeBuffer.remaining() > 0) {
            read += Utils.read(channel, sizeBuffer);
        }
        // sizeBuffer filled done and begin to fill contentBuffer
        if (contentBuffer == null && !sizeBuffer.hasRemaining()) {
            sizeBuffer.rewind();
            int size = sizeBuffer.getInt();
            if (size <= 0) {
                throw new MqLiteException(format("%d is not a valid request size.", size));
            }
            contentBuffer = ByteBuffer.allocate(size);
        }
        //
        if (contentBuffer != null) {
            read = Utils.read(channel, contentBuffer);
            //
            if (!contentBuffer.hasRemaining()) {
                contentBuffer.rewind();
                setCompleted();
            }
        }
        return read;
    }

    @Override
    public int writeTo(GatheringByteChannel channel) throws IOException {
        expectIncomplete();
        int written = 0;
        // try to write the size if we haven't already
        if (sizeBuffer.hasRemaining()) {
            written += channel.write(sizeBuffer);
        }

        // try to write the actual buffer itself
        if (!sizeBuffer.hasRemaining() && contentBuffer.hasRemaining()) {
            written += channel.write(contentBuffer);
        }

        // if we are done, mark it off
        if (!contentBuffer.hasRemaining()) {
            setCompleted();
        }

        return written;
    }

    @Override
    public int writeCompletely(GatheringByteChannel channel) throws IOException {
        int written = 0;
        while (!complete()) {
            written += writeTo(channel);
        }
        return written;
    }

    @Override
    public ByteBuffer buffer() {
        expectComplete();
        return contentBuffer;
    }

    @Override
    public boolean complete() {
        return done;
    }

    public void setCompleted() {
        this.done = true;
    }

    public void expectIncomplete() {
        if (complete()) {
            throw new IllegalStateException("This api cannot be completed on a complete request.");
        }
    }

    public void expectComplete() {
        if (!complete()) {
            throw new IllegalStateException("This api cannot be completed on an incomplete request.");
        }
    }
}
