package com.bow.forest.common.mqlite.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author vv
 * @since 2017/5/21.
 */
public class Log implements ILog {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    private static final String FileSuffix = ".jafka";

    public final File dir;

    private final RollingStrategy rollingStrategy;

    final int flushInterval;

    public final int partition;

    private final int maxMessageSize;

    public Log(File dir, int partition, RollingStrategy rollingStrategy, int flushInterval, boolean needRecovery,
            int maxMessageSize) {
        this.dir = dir;
        this.partition = partition;
        this.rollingStrategy = rollingStrategy;
        this.flushInterval = flushInterval;
        this.maxMessageSize = maxMessageSize;
    }

    @Override
    public MessageSet read(long offset, int length) throws IOException {
        return null;
    }

    @Override
    public List<Long> append(MessageSet messages) {
        return null;
    }
}
