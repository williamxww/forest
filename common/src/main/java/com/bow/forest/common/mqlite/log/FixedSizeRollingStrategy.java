package com.bow.forest.common.mqlite.log;

/**
 * @author vv
 * @since 2017/5/21.
 */
public class FixedSizeRollingStrategy implements RollingStrategy {

    private final int maxFileSize;

    public FixedSizeRollingStrategy(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @Override
    public boolean check(LogSegment lastSegment) {
        return lastSegment.getMessageSet().getSizeInBytes() > maxFileSize;
    }
}
