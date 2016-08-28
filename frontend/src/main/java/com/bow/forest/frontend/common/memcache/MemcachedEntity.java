package com.bow.forest.frontend.common.memcache;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;


/**
 * 缓存参数类
 * 
 * @author KF47558
 * 
 */
public class MemcachedEntity
{
    /**
     * NIO的连接大小 默认为5
     */
    private String connectSize = "5";

    /**
     * 合并因子 默认值为150
     */
    private String mergeFactor = "150";

    /**
     * 对象压缩因子的限制 单位：字节 默认值为2048
     */
    private String compressionThreshold = "2048";

    /**
     * 是否设置默认的序列化
     */
    private String isDefaultTranscode = "false";

    /** 初始化时连接的超时时间 */
    private long connectTimeout = 1000;

    /**
     * 初始缓存区大小
     * 
     */
    private int initBufSize = 20;

    /**
     * 最大缓冲区大小
     */
    private int maxBufSize = 1024;

    /**
     * 缓存对象列表
     */
    private String objectList;

    /**
     * 缓存服务器列表
     */
    private String cacheServerList;

    // modify by kf56385 at 2011-12-26 for IRD-21877 begin
    /**
     * 缓存对象列表,list类型
     */
    private List<String> objList;
    
    /**
     * 是否为主缓存组，不是则为备缓存组
     */
    private String isMaster = "true";

    public String getIsMaster()
    {
        return isMaster;
    }

    public void setIsMaster(String isMaster)
    {
        this.isMaster = isMaster;
    }

    public List<String> getObjList()
    {
        return objList;
    }

    public void setObjList(List<String> objList)
    {
        this.objList = objList;
    }

    // modify by kf56385 at 2011-12-26 for IRD-21877 end

    /**
     * 获取缓存对象列表
     * 
     * @return 缓存对象列表
     */
    public String getObjectList()
    {
        return objectList;
    }

    // modify by kf56385 at 2011-12-26 for IRD-21877 begin
    /**
     * 设置缓存对象列表
     * 
     * @param objectList
     *            缓存对象列表
     */
    public void setObjectList(String objectList)
    {
        this.objectList = objectList;
        // 给objList设值
        if (StringUtils.isNotEmpty(objectList))
        {
            List<String> objList = Arrays.asList(StringUtils.split(objectList,","));
            this.setObjList(objList);
        }
    }

    // modify by kf56385 at 2011-12-26 for IRD-21877 end

    /**
     * 获取缓存服务器列表
     * 
     * @return 缓存服务器列表
     */
    public String getCacheServerList()
    {
        return cacheServerList;
    }

    /**
     * 设置缓存服务器列表
     * 
     * @param cacheServerList
     *            缓存服务器列表
     */
    public void setCacheServerList(String cacheServerList)
    {
        this.cacheServerList = cacheServerList;
    }

    /**
     * 取得connectSize
     * 
     * @return 返回connectSize。
     */
    public String getConnectSize()
    {
        return connectSize;
    }

    /**
     * 设置connectSize
     * 
     * @param connectSize
     *            要设置的connectSize。
     */
    public void setConnectSize(String connectSize)
    {
        this.connectSize = connectSize;
    }

    /**
     * 取得mergeFactor
     * 
     * @return 返回mergeFactor。
     */
    public String getMergeFactor()
    {
        return mergeFactor;
    }

    /**
     * 设置mergeFactor
     * 
     * @param mergeFactor
     *            要设置的mergeFactor。
     */
    public void setMergeFactor(String mergeFactor)
    {
        this.mergeFactor = mergeFactor;
    }

    /**
     * 取得compressionThreshold
     * 
     * @return 返回compressionThreshold。
     */
    public String getCompressionThreshold()
    {
        return compressionThreshold;
    }

    /**
     * 设置compressionThreshold
     * 
     * @param compressionThreshold
     *            要设置的compressionThreshold。
     */
    public void setCompressionThreshold(String compressionThreshold)
    {
        this.compressionThreshold = compressionThreshold;
    }

    /**
     * 取得isDefaultTranscode
     * 
     * @return 返回isDefaultTranscode。
     */
    public String getIsDefaultTranscode()
    {
        return isDefaultTranscode;
    }

    /**
     * 设置isDefaultTranscode
     * 
     * @param isDefaultTranscode
     *            要设置的isDefaultTranscode。
     */
    public void setIsDefaultTranscode(String isDefaultTranscode)
    {
        this.isDefaultTranscode = isDefaultTranscode;
    }

    /**
     * 取得connectTimeout
     * 
     * @return 返回connectTimeout。
     */
    public long getConnectTimeout()
    {
        return connectTimeout;
    }

    /**
     * 设置connectTimeout
     * 
     * @param connectTimeout
     *            要设置的connectTimeout。
     */
    public void setConnectTimeout(long connectTimeout)
    {
        this.connectTimeout = connectTimeout;
    }

    /**
     * 取得initBufSize
     * 
     * @return 返回initBufSize。
     */
    public int getInitBufSize()
    {
        return initBufSize;
    }

    /**
     * 设置initBufSize
     * 
     * @param initBufSize
     *            要设置的initBufSize。
     */
    public void setInitBufSize(int initBufSize)
    {
        this.initBufSize = initBufSize;
    }

    /**
     * 取得maxBufSize
     * 
     * @return 返回maxBufSize。
     */
    public int getMaxBufSize()
    {
        return maxBufSize;
    }

    /**
     * 设置maxBufSize
     * 
     * @param maxBufSize
     *            要设置的maxBufSize。
     */
    public void setMaxBufSize(int maxBufSize)
    {
        this.maxBufSize = maxBufSize;
    }

}
