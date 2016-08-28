/*
 * 文  件  名：CacheMapValue.java
 * 版        权：Copyright 2011-2013 Huawei Tech.Co.Ltd. All Rights Reserved.
 * 描        述：
 * 修  改  人：s00215221
 * 修改时间：2012-8-17
 * 修改内容：新增
 */
package com.bow.forest.frontend.common.memcache;

/**
 *  添加类的描述
 * @author s00215221
 * @version CMR14 2012-8-17
 * @since V100R001C03LCMR14
 */
public class CacheMapValue{
    
    /**
     * 配置名称
     */
    private String configName;
    
    /**
     * 前缀名
     */
    private String prefixName;
    
    public CacheMapValue()
    {
    }
    
    public CacheMapValue(String configName, String prefixName)
    {
        this.configName = configName;
        this.prefixName = prefixName;
    }
    /**
     * 取得configName
     * @return 返回configName。
     */
    public String getConfigName()
    {
        return configName;
    }
    /**
     * 设置configName
     * @param configName 要设置的configName。
     */
    public void setConfigName(String configName)
    {
        this.configName = configName;
    }
    /**
     * 取得prefixName
     * @return 返回prefixName。
     */
    public String getPrefixName()
    {
        return prefixName;
    }
    /**
     * 设置prefixName
     * @param prefixName 要设置的prefixName。
     */
    public void setPrefixName(String prefixName)
    {
        this.prefixName = prefixName;
    }
    
}
