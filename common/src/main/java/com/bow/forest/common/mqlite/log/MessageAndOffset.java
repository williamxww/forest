package com.bow.forest.common.mqlite.log;

/**
 * @author vv
 * @since 2017/5/21.
 */
public class MessageAndOffset {

    public final Message message;

    public final long offset;

    public MessageAndOffset(Message message, long offset) {
        this.message = message;
        this.offset = offset;
    }

    @Override
    public String toString() {
        return String.format("MessageAndOffset [offset=%s, message=%s]", offset, message);
    }

}
