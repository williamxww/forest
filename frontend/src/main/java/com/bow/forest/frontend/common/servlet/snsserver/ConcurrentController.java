/*
 * 文 件 名:  ConcurrentController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright 2005-2008,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  j65521
 * 修改时间:  2008-3-28
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;

/**
 * 并发控制器 统计并限制并发请求
 * 
 * @author j65521
 * @version [版本号, 2012-2-28]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ConcurrentController
{
    /** 不限制并发请求数 */
    public static final int NO_LIMIT = -1;

    /** 一直等待 */
    public static final int ALWAYS_WAIT = -1;

    /** 不等待 */
    public static final int NO_WAIT = 0;

    /** 当前的并发请求计数 */
    private volatile int current = 0;

    /** 限制并发请求计数 */
    private volatile int limit;

    /** 最大的并发请求数 */
    private volatile int max = 0;

    /** 累计请求数 */
    private volatile int total = 0;

    /** 同步锁 */
    private Object lock = new Object();

    /** 唤醒线程 */
    private WakeupThread wakeupThread = new WakeupThread();

    /** 并发线程map */
    private Map<Thread, Entry> concurrentMap = new Hashtable<Thread, Entry>(128);

    /** 计算TPS */
    private TPSController tpsController = null;

    /** 父并发控制器 */
    private ConcurrentController parent = null;

    /** 等待队列 */
    private LinkedList<Entry> queue = new LinkedList<Entry>();

    /** 最大并发数变更时间 */
    private Date maxConcurrentChangeTime = new Date();
    
    /** 支持等待 */
    private boolean isWaitSupport = false;

    /**
     * <默认构造函数>
     */
    public ConcurrentController()
    {
        this(NO_LIMIT, null, false);
    }

    /**
     * <默认构造函数>
     * 
     * @param limit
     *            限制的并发请求数
     */
    public ConcurrentController(int limit)
    {
        this(limit, null, false);
    }
    
    public ConcurrentController(int limit, ConcurrentController parent)
    {
        this(limit, parent, false);
    }
    
    /**
     * <默认构造函数>
     * 
     * @param limit
     *            限制的并发请求数
     * @param parent
     *            TPS计算器
     */
    public ConcurrentController(int limit, ConcurrentController parent, boolean isWaitSupport)
    {
        this.current = 0;
        this.max = 0;
        this.limit = limit;
        this.parent = parent;
        
        this.isWaitSupport = isWaitSupport;
        if (this.isWaitSupport)
        {
            wakeupThread.start();
        }
    }

    /**
     * 开始请求事务 如果事务达到限制线程将等待，直到有事务被释放， 使用<tt>beginTransaction(long time)</tt>限制等待时间
     * 
     * @throws InterruptedException
     *             被打断异常
     */
    public boolean beginTransaction() throws InterruptedException
    {
        return beginTransaction(ALWAYS_WAIT);
    }

    /**
     * 开始一个事务 当事务达到限制线程将等待指定的时间； 如果在时间内有事务被释放，方法返回<tt>true</tt>，否则返回<tt>false</tt> 等待时间为<tt>-1</tt>时，表示一直等待，同
     * <tt>beginTransaction()</tt>方法
     * 
     * @throws InterruptedException
     *             被打断异常
     */
    public boolean beginTransaction(long time) throws InterruptedException
    {
        Entry entry = new Entry(Thread.currentThread());

        if (null != parent)
        {
            // 有父控制器，首先获取父控制器事务
            long curtime = System.currentTimeMillis();

            boolean result = parent.beginTransaction(time);

            if (result)
            {
                curtime = System.currentTimeMillis() - curtime;

                if (time > 0)
                {
                    if (curtime >= time)
                    {
                        time = NO_WAIT;
                    }
                    else
                    {
                        time = time - curtime;
                    }
                }
            }
            else
            {
                return result;
            }
        }

        if (tryIncTransaction(entry))
        {
            return true;
        }

        // 不等待
        if ((!this.isWaitSupport) || (NO_WAIT == time))
        {
            if (null != parent)
            {
                // 释放父事务
                parent.finishTransactionWithoutTotal(entry.thread);
            }

            return false;
        }
        
        // 放入等待队列
        synchronized (queue)
        {
            // if (null == tail)
            // {
            // tail = queue;
            // }
            // entry.prev = tail;
            // tail.next = entry;
            // tail = entry;

            // 放入等待队列
            queue.add(entry);

        }

        synchronized (entry)
        {
            try
            {
                if (time > 0)
                {
                    // 等待指定时间
                    entry.wait(time);

                    // 被唤醒的
                    if (entry.notified)
                    {
                        // entry.running = true;

                        // 放入map
                        // concurrentMap.put(entry.thread, entry);
                        return true;
                    }
                    else
                    {
                        if (null != parent)
                        {
                            // 释放父事务
                            parent.finishTransactionWithoutTotal(entry.thread);
                        }

                        entry.canceled = true;

                        return false;
                    }
                }
                else
                {
                    while (entry.notified)
                    {
                        entry.wait();
                    }

                    return true;
                }
            }
            finally
            {
                synchronized (queue)
                {
                    // 移出等待队列
                    // entry.remove();
                    queue.remove(entry);
                }
            }
        }
    }

    /**
     * 结束请求事务
     */
    public void finishTransaction()
    {
        finishTransaction(Thread.currentThread(), true);
    }

    /**
     * 结束请求事务
     * 
     * @param thread
     *            等待线程
     */
    public void finishTransaction(Thread thread, boolean finishParent)
    {
        if (null == thread)
        {
            return;
        }

        synchronized (lock)
        {
            Entry entry = (Entry) concurrentMap.remove(thread);
            if (null == entry)
            {
                return;
            }
            else
            {
                // System.out.println("remove Thread:" + entry.thread.getName());
                if (entry.isRunning())
                {
                    current--;

                    wakeupThread.wakeup();
                }
            }
        }

        // 结束父事务
        if ((finishParent) && (null != parent))
        {
            parent.finishTransaction(thread, true);
        }
    }

    private void finishTransactionWithoutTotal(Thread thread)
    {
        finishTransaction(thread, false);

        if (null != parent)
        {
            parent.finishTransactionWithoutTotal(thread);
        }

        synchronized (lock)
        {
            total--;
        }
    }

    /**
     * 请求事务是否超过限制
     */
    public boolean isTransactionOutOfLimit()
    {
        return ((limit > 0) && (current >= limit));
    }

    /**
     * 获取所有当前的并发线程
     */
    public Collection<Entry> getConcurrentThread()
    {
        return concurrentMap.values();
    }

    /**
     * 获取父并发控制器对象
     */
    public ConcurrentController getParent()
    {
        return parent;
    }

    /**
     * 更新父并发控制器对象
     */
    public void setParent(ConcurrentController parent)
    {
        this.parent = parent;
    }

    /**
     * 获取一个等待的线程
     */
    private Entry getWaitingEntry()
    {
        return queue.removeFirst();
    }

    /**
     * 增加一个事务
     */
    private boolean tryIncTransaction(Entry entry)
    {
        if (!isTransactionOutOfLimit())
        {
            synchronized (lock)
            {
                if (!isTransactionOutOfLimit())
                {

                    current++;
                    total++;

                    if (current > max)
                    {
                        max = current;
                        maxConcurrentChangeTime = new Date();
                        // System.out.println("系统最大并发数改变时的系统时间： " + maxConcurrentChangeTime);
                    }

                    if (null != tpsController)
                    {
                        tpsController.inc();
                    }

                    entry.running = true;

                    // 放入map
                    // System.out.println(this + "add Thread:" + entry.thread.getName());
                    concurrentMap.put(entry.thread, entry);

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return 返回 current
     */
    public int getCurrent()
    {
        return current;
    }

    /**
     * @return 返回 limit
     */
    public int getLimit()
    {
        return limit;
    }

    /**
     * @param limit 对limit进行赋值
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    /**
     * @return 返回 max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @return 返回 tpsController
     */
    public TPSController getTpsController()
    {
        return tpsController;
    }

    /**
     * @param tpsController 对tpsController进行赋值
     */
    public void setTpsController(TPSController tpsController)
    {
        this.tpsController = tpsController;
    }

    /**
     * 等待的线程对象
     * 
     * @author j65521
     * @version [版本号, 2008-3-28]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public class Entry
    {
        /** 线程信息 */
        private Thread thread;

        /** 唤醒 */
        private volatile boolean notified = false;

        /** 事务运行中 */
        private volatile boolean running = false;

        /** 事务取消 */
        private volatile boolean canceled = false;

        /**
         * <默认构造函数>
         */
        Entry(Thread thread)
        {
            this.thread = thread;
        }

        /**
         * @return 返回 thread
         */
        public Thread getThread()
        {
            return thread;
        }

        /**
         * @return 返回 running
         */
        public boolean isRunning()
        {
            return running;
        }
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    /**
     * 
     * 唤醒线程
     * 
     * @author j65521
     * @version [版本号, 2008-3-28]
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    class WakeupThread extends Thread
    {
        /** 线程中止 */
        private boolean terminal = false;

        /** 线程运行标志 */
        private boolean running = true;

        /**
         * <默认构造函数>
         */
        WakeupThread()
        {
            super("ConcurrentController-WakeupThread");
        }

        /**
         * 主函数
         * 
         */
        public void run()
        {
            while (!terminal)
            {
                if (queue.isEmpty())
                {
                    // 队列空，自动停止
                    synchronized (this)
                    {
                        try
                        {
                            running = false;
                            this.wait();
                        }
                        catch (InterruptedException e)
                        {
                            // do nothing
                        }

                        continue;
                    }
                }

                Entry entry = getWaitingEntry();
                if (null != entry)
                {
                    synchronized (this)
                    {
                        while (isTransactionOutOfLimit())
                        {
                            try
                            {
                                running = false;
                                this.wait();
                            }
                            catch (InterruptedException e)
                            {
                                // do nothing
                            }
                        }
                    }

                    synchronized (entry)
                    {
                        if ((!entry.canceled) && (tryIncTransaction(entry)))
                        {
                            entry.notified = true;

                            entry.notifyAll();
                        }
                    }
                }
            }
        }

        /**
         * 中止线程
         */
        void terminal()
        {
            terminal = true;
            wakeup();
        }

        /**
         * 唤醒
         */
        void wakeup()
        {
            if (!running)
            {
                synchronized (this)
                {
                    if (!running)
                    {
                        running = true;
                        this.notifyAll();
                    }
                }
            }
        }
    }

    /**
     * 控制器最大并发数max改变时系统时间
     * 
     * @author lKF42842
     * @return
     */
    public long getMaxConcurrentChangeTime()
    {
        return maxConcurrentChangeTime.getTime();
    }

    public static void main(String args[])
    {
        // 单元测试代码
        /*
        final ConcurrentController parent = new ConcurrentController(10);// 设定一个父并发控制器，最大并发请求数为10
        ConcurrentController child1 = new ConcurrentController(5, parent);
        ConcurrentController child2 = new ConcurrentController(5, parent);
        ConcurrentController child3 = new ConcurrentController(5, parent);// 开启3个子并发控制器，最大并发请求数为5
        final ConcurrentController[] arr = new ConcurrentController[] {child1, child2, child3};
        final Object lock = new Object();
        final ArrayList<Object> list = new ArrayList<Object>();
        final java.util.Random r = new java.util.Random();
        for (int i = 0; i < 20; i++)
        {
            final int j = i; // 创造20个线程，创建完成之后立即启动
            new Thread("Thread-" + i)
            {
                int i = j;

                public void run()
                {
                    for (int l = 0; l < 100; l++)
                    {
                        int k = r.nextInt(3);// 随机产生一个数，在3以内，可能的值为0，1，2
                        ConcurrentController c = arr[k];// 随机选择一个控制器
                        int time1 = r.nextInt(4000);
                        int time2 = r.nextInt(200);
                        boolean result = false;
                        try
                        {
                            synchronized (lock)
                            {
                                System.out.print(new java.text.SimpleDateFormat("HH:mm:ss")
                                    .format(new java.util.Date()));
                                System.out.print("Thread[" + i + "]");
                                System.out.println("CHILD" + (k + 1) + " beginTransaction(" + time1 + ")");
                            }
                            result = c.beginTransaction(time1);
                            synchronized (lock)
                            {
                                System.out.print(new java.text.SimpleDateFormat("HH:mm:ss")
                                    .format(new java.util.Date()));
                                System.out.print("Thread[" + i + "]");
                                if (result)
                                {
                                    System.out.println("CHILD" + (k + 1) + " beginTransaction success, thread="
                                        + c.getCurrent() + "," + c.getConcurrentThread().size() + " parent="
                                        + parent.getCurrent() + "," + parent.getConcurrentThread().size() + " Total="
                                        + parent.getTotal());
                                }
                                else
                                {
                                    System.out.println("CHILD" + (k + 1) + " beginTransaction fail, thread="
                                        + c.getCurrent() + "," + c.getConcurrentThread().size() + " parent="
                                        + parent.getCurrent() + "," + parent.getConcurrentThread().size());
                                }
                            }

                            if (result)
                            {
                                synchronized (lock)
                                {
                                    System.out.print(new java.text.SimpleDateFormat("HH:mm:ss")
                                        .format(new java.util.Date()));
                                    System.out.print("Thread[" + i + "]");
                                    System.out.println("CHILD" + (k + 1) + " sleep(" + time2 + ")");
                                    list.add(new Object());
                                }
                                Thread.sleep(time2);
                            }
                        }
                        catch (InterruptedException e)
                        { // TODO Auto-generated catch block e.printStackTrace();

                        }
                        finally
                        {
                            c.finishTransaction();
                            synchronized (lock)
                            {
                                System.out.print(new java.text.SimpleDateFormat("HH:mm:ss")
                                    .format(new java.util.Date()));
                                System.out.print("Thread[" + i + "]");
                                System.out.println("CHILD" + (k + 1) + " finishTransaction(" + result + "), thread="
                                    + c.getCurrent() + "," + c.getConcurrentThread().size() + " parent="
                                    + parent.getCurrent() + "," + parent.getConcurrentThread().size());
                            }
                        }

                        try
                        {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e)
                        { // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    System.out.println("Total=" + list.size());
                }

            }.start();
        }

        while (true)
        {
            System.out.println("[PARENT]WAITING-QUEUE: " + parent.queue.size() + "; WAKEUP-THREAD is "
                + parent.wakeupThread.getState().toString());
            System.out.println("[CHILD1]WAITING-QUEUE: " + child1.queue.size() + "; WAKEUP-THREAD is "
                + child1.wakeupThread.getState().toString());
            System.out.println("[CHILD2]WAITING-QUEUE: " + child2.queue.size() + "; WAKEUP-THREAD is "
                + child2.wakeupThread.getState().toString());
            System.out.println("[CHILD3]WAITING-QUEUE: " + child3.queue.size() + "; WAKEUP-THREAD is "
                + child3.wakeupThread.getState().toString());
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            { // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
       */
    }
}
