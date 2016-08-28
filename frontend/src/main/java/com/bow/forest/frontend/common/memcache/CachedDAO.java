package com.bow.forest.frontend.common.memcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedDAO
{
    private static final Logger LOG = LoggerFactory.getLogger(CachedDAO.class);
    
    /** 缓存失效时同步刷新缓存*/
    public static final int REFRESH_MODE_SYNC = 1;
    /** 缓存失效时异步刷新缓存*/
    public static final int REFRESH_MODE_ASYNC = 2;
    
    /** 异步刷新时使用的线程池对象*/
    public static ExecutorService pool;
    
    /** 缓存对象*/
    protected Cache cache;
    
    /** 数据获取对象，一般为数据库的DAO*/
    protected DBDataLoader dataLoader;
    
    public void setCache(Cache cache)
    {
        this.cache = cache;
    }
    
    public Cache getCache()
    {
        return cache;
    }

    public void setDataLoader(DBDataLoader loader)
    {
        this.dataLoader = loader;
    }
    
    public Object get(String key)
    {
        return cache.get(key);
    }
    
    public void put(String key, Object value)
    {
        if (value == null)
        {
            cache.delete(key);
        }
        else
        {
            cache.put(key, value);
        }
    }
    
    public boolean putWithResult(String key, Object value)
    {
        if (value == null)
        {
            return cache.deleteWithResult(key);
        }
        else
        {
            return cache.putWithResult(key, value);
        }
    }
    
    public void delete(String key)
    {
        cache.delete(key);
    }
    
    public void refresh(String key, int mode)
    {
        if (mode == REFRESH_MODE_SYNC)
        {
            DataLoadResult result = dataLoader.load(key);
            if (result.isSuccess())
            {
                put(key, result.getData());
            }
        }
        else
        {
            startAsyncRefresh(key);
        }
    }
    
    protected void startAsyncRefresh(final String key)
    {
        if (pool == null)
        {
            pool = Executors.newCachedThreadPool();
        }
        
        try
        {
            pool.execute(new Runnable()
            {
                public void run()
                {
                    DataLoadResult result = dataLoader.load(key);
                    if (result.isSuccess())
                    {
                        put(key, result.getData());
                    }
                }
            });
        }
        catch (Exception e)
        {
            LOG.error("", e);
        }
    }
}
