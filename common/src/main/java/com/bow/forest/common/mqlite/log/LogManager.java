package com.bow.forest.common.mqlite.log;

import com.bow.forest.common.mqlite.ServerConfig;
import com.bow.forest.common.mqlite.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class LogManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogManager.class);

    final ServerConfig config;

    final File logDir;

    final int flushInterval;

    private RollingStrategy rollingStrategy;

    final int numPartitions;

    private final ConcurrentMap<String, ConcurrentMap<Integer, Log>> logs = new ConcurrentSkipListMap();

    public LogManager(ServerConfig config) {
        this.config = config;
        this.numPartitions = config.getNumPartitions();
        this.logDir = Utils.getCanonicalFile(new File(config.getLogDir()));
        this.flushInterval = config.getFlushInterval();
    }

    public void load() throws IOException {
        if (this.rollingStrategy == null) {
            this.rollingStrategy = new FixedSizeRollingStrategy(config.getLogFileSize());
        }
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        if (!logDir.isDirectory() || !logDir.canRead()) {
            throw new IllegalArgumentException(logDir.getAbsolutePath() + " is not a readable log directory.");
        }
        File[] subDirs = logDir.listFiles();
        if (subDirs != null) {
            for (File dir : subDirs) {
                if (!dir.isDirectory()) {
                    LOGGER.warn("Skipping unexplainable file '" + dir.getAbsolutePath() );
                } else {
                    LOGGER.info("Loading log from " + dir.getAbsolutePath());
                    final String topicNameAndPartition = dir.getName();
                    if (-1 == topicNameAndPartition.indexOf('-')) {
                        throw new IllegalArgumentException("error topic directory: " + dir.getAbsolutePath());
                    }
                    final KV<String, Integer> topicPartion = Utils.getTopicPartition(topicNameAndPartition);
                    final String topic = topicPartion.k;
                    final int partition = topicPartion.v;
                    Log log = new Log(dir, partition, this.rollingStrategy, flushInterval);

                    logs.putIfAbsent(topic, new ConcurrentSkipListMap<Integer, Log>());
                    ConcurrentMap<Integer, Log> parts = logs.get(topic);

                    parts.put(partition, log);
                }
            }
        }


    }

    public void setRollingStrategy(RollingStrategy rollingStrategy) {
        this.rollingStrategy = rollingStrategy;
    }
}
