package com.bow.forest.common.mqlite;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;

/**
 * @author vv
 * @since 2017/5/20.
 */
public interface Send extends Transmission {

    int writeTo(GatheringByteChannel channel) throws IOException;

    int writeCompletely(GatheringByteChannel channel) throws IOException;
}
