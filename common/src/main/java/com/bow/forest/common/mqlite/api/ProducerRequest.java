package com.bow.forest.common.mqlite.api;

import com.bow.forest.common.mqlite.Utils;

import java.nio.ByteBuffer;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class ProducerRequest implements Request{
    @Override
    public RequestKeys getRequestKey() {
        return RequestKeys.PRODUCE;
    }

    @Override
    public void writeTo(ByteBuffer buffer) {
//        Utils.writeShortString(buffer, topic);
//        buffer.putInt(partition);
//        final ByteBuffer sourceBuffer = messages.serialized();
//        buffer.putInt(sourceBuffer.limit());
//        buffer.put(sourceBuffer);
//        sourceBuffer.rewind();
    }
}
