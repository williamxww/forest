package com.bow.forest.common.mqlite;

import java.nio.ByteBuffer;

/**
 * @author vv
 * @since 2017/5/20.
 */
public interface Transmission {
    /**
     * check task whether it has been finished
     *
     * @return true while finished or false
     */
    boolean complete();

    ByteBuffer buffer();

}
