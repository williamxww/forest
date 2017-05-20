package com.bow.forest.common.mqlite;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author vv
 * @since 2017/5/20.
 */
public interface Receive extends Transmission{

    int readFrom(ReadableByteChannel channel) throws IOException;


}
