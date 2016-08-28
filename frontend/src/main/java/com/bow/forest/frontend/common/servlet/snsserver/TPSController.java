package com.bow.forest.frontend.common.servlet.snsserver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TPS控制器
 * <p>
 * Title: TPS控制器
 * </p>
 * 
 * <p>
 * Description: 使用阻塞的方式进行TPS控制
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: huawei
 * </p>
 * 
 * @author jiangtong
 * @version 1.0
 */
public class TPSController
{
    /** 定时器，使用同一个定时器 */
    private static Timer timer = new Timer();

    /** 父控制器 */
    private TPSController _parent = null;

    /** 平均值计算到小数点后2位 */
    private static final int AVERAGE_DIGIT = 100;


    /** 重新计算一次TPS周期 */
    public int _period = 5;

    /** 任务 */
    private TPSClearTask task;
    
    /** 上一个计算的TPS */
    private int lastTPS;

    /** 最大TPS */
    private int limitTPS;


    /** 曾经达到的最大TPS */
    private int maxTPS;

    /** 当前TPS */
    private int curTPS;
    

    /** 累计事务 */
    private long totalTrans;

    /** 平均TPS */
    private int averageTPS;


    /** TPS计算访问锁 */
    private Object lock = new Object();

    /**
     * 构造方法     * 
     * @param limitTPS
     *            限制TPS，小于0时表示不限制
     * @param period
     *            计算TPS周期
     */
    public TPSController(int limitTPS, int period)
    {
        this(limitTPS, period, null);
    }

    /**
     * 更新限制TPS     * 
     * @param _limitTPS
     *            限制TPS
     */
    public void setLimitTPS(int _limitTPS)
    {
        this.limitTPS = _limitTPS;
    }
    
    /**
     * 构造方法     * 
     * @param limitTPS
     *            限制的TPS，小于0时表示不限制
     * @param period
     *            计算TPS周期
     * @param parent
     *            父控制器
     */
    public TPSController(int limitTPS, int period, TPSController parent)
    {
        if (period <= 0)
        {
            this._period = 1;
        }
        else
        {
            this._period = period;
        }
        this.limitTPS = limitTPS;
        this.curTPS = 0;
        this.lastTPS = 0;
        this.maxTPS = 0;
        this.totalTrans = 0;

        this._parent = parent;
        // 启动任务
        task = new TPSClearTask(this);
        timer.schedule(task, this._period * 1000, this._period * 1000);
    }

    /**
     * 获取限制的TPS
     * 
     * @return TPS
     */
    public double getLimitTPS()
    {
        return ((double) limitTPS) / _period;
    }

    /**
     * 获取限制的TPS
     * 
     * @return TPS
     */
    public int getLimitIntTPS()
    {
        return limitTPS / _period;
    }

    

    /**
     * 获取最大的TPS
     * 
     * @return TPS
     */
    public int getMaxIntTPS()
    {
        return maxTPS / _period;
    }

    /**
     * 获取上一个计算的TPS
     * 
     * @return TPS
     */
    public int getLastIntTPS()
    {
        return lastTPS / _period;
    }
    
    /**
     * 获取最大的TPS
     * 
     * @return TPS
     */
    public double getMaxTPS()
    {
        return ((double) maxTPS) / _period;
    }

    

    /**
     * 获取上一个计算的TPS
     * 
     * @return TPS
     */
    public double getLastTPS()
    {
        return ((double) lastTPS) / _period;
    }

    /**
     * 获取上一个计算的TPS
     * 
     * @return TPS
     */
    public double getAverageTPS()
    {
        return ((double) (averageTPS + 5)) / AVERAGE_DIGIT / _period;
    }
    
    /**
     * 获取上一个计算的TPS
     * 
     * @return TPS
     */
    public int getAverageIntTPS()
    {
        return (averageTPS + 5) / AVERAGE_DIGIT / _period;
    }

   

    /**
     * 获取当前TPS
     * 
     * @return 当前TPS
     */
    public int getCurIntTPS()
    {
        return curTPS / _period;
    }

    /**
     * 获取累计事务数
     * 
     * @return 累计事务数
     */
    public long getTotalTransCount()
    {
        return totalTrans;
    }
    
    
    /**
     * 获取当前TPS
     * 
     * @return 当前TPS
     */
    public double getCurTPS()
    {
        return ((double) curTPS) / _period;
    }

    

    /**
     * 增加一个任务
     */
    public void inc(int count)
    {
        if (null != _parent)
        {
            // 父控制器不为空
            _parent.inc(count);
        }

        // 计算TPS
        synchronized (lock)
        {
            while ((limitTPS > 0) && (curTPS >= limitTPS))
            {
                // 达到了TPS的限制
                try
                {
                    lock.wait();
                }
                catch (InterruptedException e)  {
                }
            }

            // 增加TPS
            curTPS += count;
            if (Long.MAX_VALUE == totalTrans)
            {
                totalTrans = 0;
            }
            else            {
                totalTrans += count;
            }
        }
    }
    
    /**
     * 增加一个任务
     */
    public void inc()
    {
        inc(1);
    }

    /**
     * 是否达到TPS限制
     * 
     * @return 是否达到限制
     */
    public boolean isReachLimit()
    {
        return ((limitTPS > 0) && (curTPS >= limitTPS));
    }

   

    /**
     * 计算周期到达，重新开始计算TPS
     */
    void recompute()
    {
        synchronized (lock)
        {
            if (curTPS > maxTPS)
            {
                // 保存最大值
                maxTPS = curTPS;
            }
            // 保存上一个值
            lastTPS = curTPS;
            // 计算平均值
            if (curTPS > 0)
            {
                // 平均值计算公式
                // A = aA+(1-a)C
                if (averageTPS > 0)
                {
                    // 四舍五入
                    averageTPS = (averageTPS * 9 + curTPS * AVERAGE_DIGIT + 5) / 10;
                }
                else
                {
                    averageTPS = curTPS * AVERAGE_DIGIT;
                }
            }

            // 重新计算
            curTPS = 0;
            // 唤醒
            lock.notifyAll();
        }
    }
    
    /**
     * 累计值清0
     */
    public void clear()
    {
        synchronized (lock)
        {
            totalTrans = 0;
            maxTPS = 0;
        }
    }
    
    /**
     * 内部类：定时清空上一个TPS值
     * 
     * @author j65521
     * 
     */
    class TPSClearTask extends TimerTask
    {
        /** 控制器 */
        private TPSController controller;

        /**
         * 任务执行方法
         */
        public void run()
        {
            controller.recompute();
        }
        
        /**
         * 构造方法
         * 
         * @param controller
         *            TPS控制器
         */
        TPSClearTask(TPSController controller)
        {
            this.controller = controller;
        }

        
    }
}
