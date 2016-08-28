package com.bow.forest.frontend.common.memcache;



public class RefreshableDAO extends CachedDAO
{
    private SharedLock sharedLock = new SharedLock();
    
    /**
     * 加载数据需要旋锁控制
     */
    private boolean needLock;
    
    /** 
     * 数据刷新时加载方式1：同步，2：异步
     */
    private int refreshMode; 
    
    /**
     * 取得refreshMode
     * @return 返回refreshMode。
     */
    public int getRefreshMode()
    {
        return refreshMode;
    }

    /**
     * 设置refreshMode
     * @param refreshMode 要设置的refreshMode。
     */
    public void setRefreshMode(int refreshMode)
    {
        this.refreshMode = refreshMode;
    }

    /**
     * {@inheritDoc}
     * 从缓存获取对象，如果对象失效，则从新从数据库加载。
     * 对象失效包括获取的对象为null，或者对象通过wapper判断已失效(needRefresh返回true)
     * 返回值
     *      返回获取的缓存对象，可返回null, 返回的不是包装的容器wrapper
     * 
     */
    public Object get(String key)
    {
        // 从缓存中读取原始或包装对象
        Object value = cache.get(key);
        if (value == null)
        {
            // 旋锁控制
            LockHandle handle = null;
            if (needLock)
            {
                handle = sharedLock.lock(key);
            }
            
            try
            {
                value = cache.get(key);
                if (value == null)
                {
                    // 读取到的对象未null，必须同步加载
                    value = fetchNewValueSync(key, null);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (handle != null)
                {
                    handle.release();
                }                
            }
        }
        else
        {
            // 缓存对象非空，但需要刷新
            if (needRefresh(value))
            {
                // 旋锁控制
                LockHandle handle = null;
                if (needLock)
                {
                    handle = sharedLock.lock(key);
                }
                
                try
                {
                    Object secondValue = cache.get(key);
                    if (needRefresh(secondValue))
                    {
                        if (refreshMode == REFRESH_MODE_SYNC)
                        {
                            value = fetchNewValueSync(key, value);
                        }
                        else
                        {
                            fetchNewValueAsync(key, value);
                        }
                    }
                }
                finally
                {
                    if (handle != null)
                    {
                        handle.release();
                    }                
                }
            }
        }
        
        if (value instanceof RefreshableWrapper)
        {
            RefreshableWrapper wrapper = (RefreshableWrapper)value;
            return wrapper.getValue();
        }
        
        return value;
    }
    
    /**
     * 取得needLock
     * @return 返回needLock。
     */
    public boolean isNeedLock()
    {
        return needLock;
    }

    /**
     * 设置needLock
     * @param needLock 要设置的needLock。
     */
    public void setNeedLock(boolean needLock)
    {
        this.needLock = needLock;
    }

    private boolean needRefresh(Object value)
    {
        if (value == null)
        {
            return true;
        }
        
        if (value instanceof RefreshableWrapper)
        {
            RefreshableWrapper wrapper = (RefreshableWrapper)value;
            if (wrapper.needRefresh())
            {
                return true;
            }
        }
        return false;
    }
    
    private Object fetchNewValueSync(String key, Object oldValue)
    {
        DataLoadResult result = dataLoader.load(key);
        if (result.isSuccess())
        {
            Object newVal = result.getData();
            put(key, result.getData());
            return newVal;
        }
        return oldValue;
    }
    
    private void fetchNewValueAsync(String key, Object oldValue)
    {
        // 把老的对象取出，更新时间戳后并放回缓存,保证旋锁的第二次判断失败，从而不用重复刷新
        if (oldValue instanceof RefreshableWrapper)
        {
            RefreshableWrapper wrapper = (RefreshableWrapper)oldValue;
            put(key, wrapper.getValue());
        }
        
        // 非包装装对象则无需重新放入缓存
        
        // 启动异步加载刷新
        startAsyncRefresh(key);
    }
    
    public void put(String key, Object value)
    {
        if (value != null)
        {
            Refreshable wrapperRefreshable = ((Refreshable)value).createWrapper();
            cache.put(key, wrapperRefreshable);
        }
        else
        {
            cache.delete(key);
        }
    }
}
