package com.bow.forest.frontend.common.memcache;

import com.bow.forest.frontend.common.servlet.snsserver.Util;

import java.util.Map;


/**
 * 读取配置参数
 * 
 * @author KF47558
 * 
 */
public class MemcachedConfigBean
{
    /**
     * 缓存参数
     */
    private Map<String, MemcachedEntity> cacheParams;

    @Override
    public String toString()
    {
        StringBuilder sbTmp = new StringBuilder(1024);
        sbTmp.append("cacheParams:");
        Map<String, MemcachedEntity> tmpMap = this.getCacheParams();
        if (!Util.isEmpty(tmpMap))
        {
            for (Map.Entry<String, MemcachedEntity> tmpEntry : tmpMap.entrySet())
            {
                MemcachedEntity memcachedEntity = tmpEntry.getValue();
                sbTmp.append(tmpEntry.getKey());
                sbTmp.append(memcachedEntity.getCacheServerList());
                sbTmp.append(memcachedEntity.getObjectList());
            }
        }

        return sbTmp.toString();
    }

    /**
     * 取得cacheParams
     * 
     * @return 返回cacheParams。
     */
    public Map<String, MemcachedEntity> getCacheParams()
    {
        return cacheParams;
    }

    /**
     * 设置cacheParams
     * 
     * @param cacheParams
     *            要设置的cacheParams。
     */
    public void setCacheParams(Map<String, MemcachedEntity> cacheParams)
    {
        this.cacheParams = cacheParams;
    }
}
