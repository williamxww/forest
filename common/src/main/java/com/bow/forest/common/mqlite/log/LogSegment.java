package com.bow.forest.common.mqlite.log;

import java.io.File;

/**
 * A segment is a file.
 * 
 * @author vv
 * @since 2017/5/21.
 */
public class LogSegment {

    private final File file;

    private final FileMessageSet messageSet;

    private volatile boolean deleted;

    public LogSegment(File file, FileMessageSet messageSet, long start) {
        this.file = file;
        this.messageSet = messageSet;
        this.deleted = false;
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
}
