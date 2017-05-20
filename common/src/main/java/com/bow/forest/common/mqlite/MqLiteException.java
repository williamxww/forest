package com.bow.forest.common.mqlite;

/**
 * Mq lite Exception
 * 
 * @author vv
 * @since 2016/8/19.
 */
public class MqLiteException extends RuntimeException {

    public MqLiteException() {
        super(MqLiteExceptionCode.fail.toString());
    }

    public MqLiteException(MqLiteExceptionCode code) {
        this(code.toString());
    }

    public MqLiteException(MqLiteExceptionCode code, String detailMessage) {
        this(code.toString() + " -- " + detailMessage);
    }

    public MqLiteException(MqLiteExceptionCode code, String detailMessage, Throwable e) {
        this(code.toString() + " -- " + detailMessage, e);
    }

    public MqLiteException(MqLiteExceptionCode code, Throwable e) {
        this(code.toString(), e);
    }

    public MqLiteException(String message) {
        super(message);
    }

    public MqLiteException(String message, Throwable e) {
        super(message, e);
    }

}
