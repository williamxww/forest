package com.bow.forest.common.mqlite.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;

/**
 * @author vv
 * @since 2017/5/21.
 */
public class FileMessageSet implements MessageSet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessageSet.class);

    private final FileChannel channel;

    private final long offset;

    public FileMessageSet(FileChannel channel, long offset) {
        this.channel = channel;
        this.offset = offset;
    }

    @Override
    public long getSizeInBytes() {
        return 0;
    }

    @Override
    public long append(MessageSet messages) throws IOException {
        return 0;
    }

    @Override
    public long writeTo(GatheringByteChannel channel, long offset, long maxSize) throws IOException {
        return 0;
    }

    @Override
    public MessageSet read(long readOffset, long size) throws IOException {
        return null;
    }

    @Override
    public Iterator<MessageAndOffset> iterator() {
        return new IteratorTemplate<MessageAndOffset>() {

            long location = offset;

            @Override
            protected MessageAndOffset makeNext() {
                try {

                    ByteBuffer sizeBuffer = ByteBuffer.allocate(4);
                    channel.read(sizeBuffer, location);
                    if (sizeBuffer.hasRemaining()) {
                        return allDone();
                    }
                    sizeBuffer.rewind();
                    int size = sizeBuffer.getInt();

                    ByteBuffer buffer = ByteBuffer.allocate(size);
                    channel.read(buffer, location + 4);
                    if (buffer.hasRemaining()) {
                        return allDone();
                    }
                    buffer.rewind();
                    location += size + 4;

                    return new MessageAndOffset(new Message(buffer), location);
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        };
    }
}
