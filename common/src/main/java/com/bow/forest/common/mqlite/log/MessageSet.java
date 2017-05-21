package com.bow.forest.common.mqlite.log;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;

/**
 * @author vv
 * @since 2017/5/21.
 */
public interface MessageSet extends Iterable<MessageAndOffset> {

    long getSizeInBytes();

    /**
     * Append this message to the message set
     * 
     * @param messages message to append
     * @return the written size
     * @throws IOException file write exception
     */
    long append(MessageSet messages) throws IOException;

    /**
     * Write the messages in this set to the given channel starting at the given
     * offset byte. Less than the complete amount may be written, but no more
     * than maxSize can be. The number of bytes written is returned
     * 
     * @param channel IOChannel
     * @param offset offset of message
     * @param maxSize size of message
     * @throws IOException any exception
     * @return size of result
     */
    long writeTo(GatheringByteChannel channel, long offset, long maxSize) throws IOException;

    /**
     * read message from file
     *
     * @param readOffset offset in this channel(file);not the message offset
     * @param size max data size
     * @return messages sharding data with file log
     * @throws IOException reading file failed
     */
    MessageSet read(long readOffset, long size) throws IOException;

}
