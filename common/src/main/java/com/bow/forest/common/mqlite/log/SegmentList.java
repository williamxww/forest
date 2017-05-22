package com.bow.forest.common.mqlite.log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Segment List
 * 
 * @author vv
 * @since 2017/5/22.
 */
public class SegmentList {

    private final AtomicReference<List<LogSegment>> contents;

    private final String name;

    public SegmentList(final String name, List<LogSegment> segments) {
        this.name = name;
        contents = new AtomicReference<List<LogSegment>>(segments);
    }

    /**
     * 将segment加入到contents中，此处使线程安全的
     * 
     * @param segment
     */
    public void append(LogSegment segment) {
        while (true) {
            List<LogSegment> curr = contents.get();
            List<LogSegment> updated = new ArrayList(curr);
            updated.add(segment);
            if (contents.compareAndSet(curr, updated)) {
                return;
            }
        }
    }

    /**
     * 删除此列表中序号比newStart小的 LogSegment
     * 
     * @param newStart
     * @return 被删除的 LogSegment
     */
    public List<LogSegment> trunc(int newStart) {
        if (newStart < 0) {
            throw new IllegalArgumentException("Starting index must be positive.");
        }
        while (true) {
            List<LogSegment> curr = contents.get();
            int newLength = Math.max(curr.size() - newStart, 0);
            List<LogSegment> updatedList = new ArrayList<LogSegment>(
                    curr.subList(Math.min(newStart, curr.size() - 1), curr.size()));
            if (contents.compareAndSet(curr, updatedList)) {
                return curr.subList(0, curr.size() - newLength);
            }
        }
    }
}
