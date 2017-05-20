package com.bow.forest.common.mqlite;

/**
 * @author vv
 * @since 2017/5/20.
 */
public interface RequestHandler {

    Send handle(Receive request);
}
