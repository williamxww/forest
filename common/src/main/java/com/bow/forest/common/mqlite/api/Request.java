package com.bow.forest.common.mqlite.api;

import java.nio.ByteBuffer;

/**
 * @author vv
 * @since 2017/5/20.
 */
public interface Request {

    /**
     * request type
     *
     * @return request type
     * @see RequestKeys
     */
    RequestKeys getRequestKey();

    /**
     * write the request data to buffer
     *
     * @param buffer data buffer
     */
    void writeTo(ByteBuffer buffer);
}
