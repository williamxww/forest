/*
 * 文  件  名：SharedLock.java
 * 版        权：Copyright 2011-2013 Huawei Tech.Co.Ltd. All Rights Reserved.
 * 描        述：
 * 修  改  人：r00138849
 * 修改时间：2012-3-24
 * 修改内容：新增
 */
package com.bow.forest.frontend.common.memcache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * TODO 添加类的描述
 * @author r00138849
 * @version CMR11 2012-3-24
 * @since V100R001C03LCMR11
 */
public class SharedLock
{
    public class RefLock implements LockHandle
    {
        private ReentrantLock lock = new ReentrantLock();
        private int refCount = 0;
        private String key;
        public RefLock(String key)
        {
            this.key = key;
        }
        
        /**
         * {@inheritDoc}
         */
        public void release()
        {
            releaseLock(this);
        }
    }
    
    private ReentrantLock mapLock = new ReentrantLock();
    private Map<String, RefLock> lockMap = new HashMap<String, RefLock>();
    
    private void releaseLock(RefLock lock)
    {
        lock.lock.unlock();
        try
        {
            mapLock.lock();
            lock.refCount--;
            if (lock.refCount == 0)
            {
                lockMap.remove(lock.key);
            }
        }
        finally
        {
            mapLock.unlock();
        }
    }
    
    public LockHandle lock(String key)
    {
        RefLock refLock = null;
        try
        {
            mapLock.lock();
            refLock = lockMap.get(key);
            if (refLock == null)
            {
                refLock = new RefLock(key);
                lockMap.put(key, refLock);
            }
            refLock.refCount++;
        }
        finally
        {
            mapLock.unlock();
        }
        
        refLock.lock.lock();
        return refLock;
    }
    
    public static void main(String[] args)
    {
        SharedLock lock = new SharedLock();
        LockHandle handle = lock.lock(null);
        handle.release();
    }
}
