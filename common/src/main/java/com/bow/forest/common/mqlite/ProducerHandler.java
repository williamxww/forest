package com.bow.forest.common.mqlite;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class ProducerHandler extends AbstractRequestHandler {

    public ProducerHandler(LogManager logManager) {
        super(logManager);
    }

    @Override
    public Send handle(Receive request) {
        return null;
    }
}
