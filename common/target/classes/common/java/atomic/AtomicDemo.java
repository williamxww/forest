package common.java.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author vv
 * @since 2017/5/10.
 */
public class AtomicDemo {

    public static void compareAndSetMax(final AtomicLong target, final long value) {
        long prev = target.get();
        while (value > prev) {
            boolean updated = target.compareAndSet(prev, value);
            if (updated) {
                break;
            }
            prev = target.get();
        }
    }
}
