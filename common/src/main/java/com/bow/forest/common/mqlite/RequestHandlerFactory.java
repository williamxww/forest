package com.bow.forest.common.mqlite;

import com.bow.forest.common.mqlite.api.RequestKeys;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class RequestHandlerFactory {

    private final ProducerHandler producerHandler;

    public RequestHandlerFactory(LogManager logManager){
        producerHandler = new ProducerHandler(logManager);
    }

    public RequestHandler mapping(RequestKeys id) {
        switch (id) {
            case PRODUCE:
                return producerHandler;
        }
        return null;
    }
}
