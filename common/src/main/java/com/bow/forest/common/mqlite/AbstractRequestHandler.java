package com.bow.forest.common.mqlite;

/**
 * @author vv
 * @since 2017/5/20.
 */
public abstract class AbstractRequestHandler implements RequestHandler {

    protected final LogManager logManager;

    public AbstractRequestHandler(LogManager logManager) {
        this.logManager = logManager;
    }
}
