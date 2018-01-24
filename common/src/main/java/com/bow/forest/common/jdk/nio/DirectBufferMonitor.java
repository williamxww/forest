package com.bow.forest.common.jdk.nio;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

/**
 * DirectByteBuffer的管理区域不是在java
 * heap上，不能通过Xmx控制，可以通过参数：-XX:MaxDirectMemorySize来控制
 * 
 * @author vv
 * @since 2018/1/24.
 */
public class DirectBufferMonitor {

    /**
     * 打印DirectMemory信息 Direct内存信息不同通过Runtime.getRuntime()获取到，但可以通过下面方法间接取到
     * 
     * @throws Exception e
     */
    private static void printDirectBufferInfo() throws Exception {
        Class<?> mem = Class.forName("java.nio.Bits");
        Field maxMemoryField = mem.getDeclaredField("maxMemory");
        maxMemoryField.setAccessible(true);
        Field reservedMemoryField = mem.getDeclaredField("reservedMemory");
        reservedMemoryField.setAccessible(true);
        Field totalCapacityField = mem.getDeclaredField("totalCapacity");
        totalCapacityField.setAccessible(true);
        Long maxMemory = (Long) maxMemoryField.get(null);
        AtomicLong reservedMemory = (AtomicLong) reservedMemoryField.get(null);
        AtomicLong totalCapacity = (AtomicLong) reservedMemoryField.get(null);
        System.out.println(
                "maxMemory=" + maxMemory + ",reservedMemory=" + reservedMemory + ",totalCapacity=" + totalCapacity);
    }

    /**
     * 显式清理 <br/>
     * 直接内存的释放并不是由普通gc控制的，而是由full gc来控制的，直接内存会自己检测情况而调用system.gc() <br/>
     * 但是如果参数中使用了DisableExplicitGC那么这是个坑了，所以啊， 这玩意，设置不设置都是一个坑<br/>
     * 那么full gc不触发，想自己释放这部分内存有方法吗？可以的。<br/>
     * 看它的源码中DirectByteBuffer发现有一个：Cleaner，是用来搞资源回收的。
     * 这个代码私有的，不能直接访问，需要通过反射来实现，通过调用cleaner()方法来获取它Cleaner对象，进而通过该对象，执行clean方法
     * 
     * @param byteBuffer
     */
    @SuppressWarnings("restriction")
    public static void clean(final ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((sun.nio.ch.DirectBuffer) byteBuffer).cleaner().clean();
        }
    } // 测试人工调用DirectBuffer的清理

    @Test
    public void testDirectBufferClean() throws Exception {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1000);
        System.out.println("start");
        Thread.sleep(5000);
        printDirectBufferInfo();
        clean(buffer);
        System.out.println("end");
        printDirectBufferInfo();
        Thread.sleep(5000);
    }

}
