package com.bow.forest.common.mqlite.log;

/**
 * @author vv
 * @since 2017/5/21.
 */
public interface RollingStrategy {

    boolean check(LogSegment lastSegment);
}
