package com.bow.forest.common.mqlite;

import java.util.Properties;

import static com.bow.forest.common.mqlite.Utils.getInt;
import static com.bow.forest.common.mqlite.Utils.getIntInRange;
import static com.bow.forest.common.mqlite.Utils.getString;

/**
 * @author vv
 * @since 2017/5/21.
 */
public class ServerConfig {

    protected final Properties props;

    public ServerConfig(Properties props) {
        this.props = props;
    }

    public String getHostName() {
        return getString(props, "hostname", null);
    }

    public int getPort() {
        return getInt(props, "port", 9092);
    }

    public String getLogDir() {
        return getString(props, "log.dir");
    }

    public int getNumPartitions() {
        return getIntInRange(props, "num.partitions", 1, 1, Integer.MAX_VALUE);
    }

    public int getLogRetentionHours() {
        return getIntInRange(props, "log.retention.hours", 24 * 7, 1, Integer.MAX_VALUE);
    }
}
