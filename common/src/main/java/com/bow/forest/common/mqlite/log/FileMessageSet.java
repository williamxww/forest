package com.bow.forest.common.mqlite.log;

import com.bow.forest.common.mqlite.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;

/**
 * message set ,消息集合的映射。通过此类可以读取或是追加消息到文件
 * 
 * @author vv
 * @since 2017/5/21.
 */
public class FileMessageSet implements MessageSet, Closeable {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMessageSet.class);

    private final FileChannel channel;

    private final long offset;

    private final boolean mutable;

    /**
     * 此 MessageSet的大小，即下一条消息的offset
     */
    private final AtomicLong setSize = new AtomicLong();

    private final AtomicLong setHighWaterMark = new AtomicLong();

    public FileMessageSet(FileChannel channel, long offset, long limit, boolean mutable) throws IOException {
        this.channel = channel;
        this.offset = offset;
        this.mutable = mutable;

        if (mutable) {
            // 准备在最后面添加message
            setSize.set(channel.size());
            setHighWaterMark.set(getSizeInBytes());
            channel.position(channel.size());
        } else {
            setSize.set(Math.min(channel.size(), limit) - offset);
            setHighWaterMark.set(getSizeInBytes());
        }
    }

    public FileMessageSet(FileChannel channel, boolean mutable) throws IOException {
        this(channel, 0, Long.MAX_VALUE, mutable);
    }

    public FileMessageSet(File file, boolean mutable) throws IOException {
        this(Utils.openChannel(file, mutable), mutable);
    }

    @Override
    public long getSizeInBytes() {
        // message set's size
        return setSize.get();
    }

    @Override
    public long append(MessageSet messages) throws IOException {
        long written = 0L;
        while (written < messages.getSizeInBytes()) {
            written += messages.writeTo(channel, 0, messages.getSizeInBytes());
        }
        long beforeOffset = setSize.getAndAdd(written);
        return written;
    }

    /**
     * zero-copy 将文件指定位置的内容写入到destChannel
     * 
     * @param destChannel
     * @param writeOffset
     * @param maxSize size of message
     * @return
     * @throws IOException
     */
    @Override
    public long writeTo(GatheringByteChannel destChannel, long writeOffset, long maxSize) throws IOException {
        return channel.transferTo(offset + writeOffset, Math.min(maxSize, getSizeInBytes()), destChannel);
    }

    public void flush() throws IOException {
        channel.force(true);
        setHighWaterMark.set(getSizeInBytes());
        LOGGER.debug("flush high water mark:" + highWaterMark());
    }

    @Override
    public MessageSet read(long readOffset, long size) throws IOException {
        return new FileMessageSet(channel, this.offset + readOffset,
                Math.min(this.offset + readOffset + size, highWaterMark()), false);
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

    public long highWaterMark() {
        return setHighWaterMark.get();
    }

    @Override
    public void close() throws IOException {
        if (mutable) {
            flush();
        }
        channel.close();
    }
}
