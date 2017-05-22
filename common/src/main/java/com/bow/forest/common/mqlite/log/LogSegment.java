package com.bow.forest.common.mqlite.log;

import java.io.File;

/**
 * 一个LogSegment对应着一个 segment文件<br/>
 * 该文件的名字就是 第一条消息的 offset，也就是{@link #start}
 * 
 * @author vv
 * @since 2017/5/21.
 */
public class LogSegment implements Comparable<LogSegment> {

    private final File file;

    private final FileMessageSet messageSet;

    /**
     * 第一条消息的偏移量，也就是文件名
     */
    private final long start;

    private volatile boolean deleted;

    public LogSegment(File file, FileMessageSet messageSet, long start) {
        this.file = file;
        this.messageSet = messageSet;
        this.start = start;
        this.deleted = false;
    }

    public long start() {
        return start;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public File getFile() {
        return file;
    }

    public FileMessageSet getMessageSet() {
        return messageSet;
    }

    /**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int compareTo(LogSegment o) {
        return this.start > o.start ? 1 : this.start < o.start ? -1 : 0;
    }
}
