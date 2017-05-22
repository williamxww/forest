package com.bow.forest.common.mqlite.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一个Log对应文件夹 topic-partitionNum ，其内部包含一堆segment文件
 * 
 * @author vv
 * @since 2017/5/21.
 */
public class Log implements ILog {

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    private static final String FileSuffix = ".jafka";

    /**
     * 文件夹 topic-partitionNum
     */
    public final File dir;

    /**
     * like mytopic-0
     */
    public final String name;

    private final SegmentList segments;

    private final RollingStrategy rollingStrategy;

    final int flushInterval;

    public final int partition;

    public Log(File dir, int partition, RollingStrategy rollingStrategy, int flushInterval) throws IOException {
        this.dir = dir;
        this.name = dir.getName();
        this.partition = partition;
        this.rollingStrategy = rollingStrategy;
        this.flushInterval = flushInterval;
        segments = loadSegments();
    }

    @Override
    public MessageSet read(long offset, int length) throws IOException {
        return null;
    }

    @Override
    public List<Long> append(MessageSet messages) {
        return null;
    }

    private SegmentList loadSegments() throws IOException {
        List<LogSegment> accum = new ArrayList<LogSegment>();
        File[] ls = dir.listFiles(new FileFilter() {

            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith(FileSuffix);
            }
        });
        LOGGER.info("loadSegments files from [" + dir.getAbsolutePath() + "]: " + ls.length);
        int n = 0;
        for (File f : ls) {
            n++;
            String filename = f.getName();
            long start = Long.parseLong(filename.substring(0, filename.length() - FileSuffix.length()));
            final String logFormat = "LOADING_LOG_FILE[%2d], start(offset)=%d, size=%d, path=%s";
            LOGGER.info(String.format(logFormat, n, start, f.length(), f.getAbsolutePath()));
            FileMessageSet messageSet = new FileMessageSet(f, false);
            accum.add(new LogSegment(f, messageSet, start));
        }
        if (accum.size() == 0) {
            // no existing segments, create a new mutable segment
            File newFile = new File(dir, Log.nameFromOffset(0));
            FileMessageSet fileMessageSet = new FileMessageSet(newFile, true);
            accum.add(new LogSegment(newFile, fileMessageSet, 0));
        } else {
            // there is at least one existing segment, validate and recover
            // them/it
            // sort segments into ascending order for fast searching
            Collections.sort(accum);
        }
        //
        LogSegment last = accum.remove(accum.size() - 1);
        last.getMessageSet().close();
        LogSegment mutable = new LogSegment(last.getFile(), new FileMessageSet(last.getFile(), true), last.start());
        accum.add(mutable);
        return new SegmentList(name, accum);
    }

    /**
     * 构建 segment 文件的名字
     * 
     * @param offset offset
     * @return segment 文件的名字
     */
    public static String nameFromOffset(long offset) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumIntegerDigits(20);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        return nf.format(offset) + Log.FileSuffix;
    }
}
