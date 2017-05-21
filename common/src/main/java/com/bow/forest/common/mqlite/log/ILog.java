package com.bow.forest.common.mqlite.log;

import java.io.IOException;
import java.util.List;

/**
 * @author vv
 * @since 2017/5/21.
 */
public interface ILog {

    /**
     * read messages from log
     *
     * @param offset offset of messages
     * @param length the max messages size
     * @return message objects
     * @throws IOException any Exception
     */
    MessageSet read(long offset, int length) throws IOException;

    /**
     * append messages to log
     *
     * @param messages message set
     * @return all message offsets or null if not supported
     */
    List<Long> append(MessageSet messages);
}
