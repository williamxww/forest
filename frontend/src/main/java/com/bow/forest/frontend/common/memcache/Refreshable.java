package com.bow.forest.frontend.common.memcache;

public interface Refreshable
{
    /**
     * 获取包装对象的实例
     * @author r00138849
     * @return
     *     如果对象不要被包装则返回this，如果对象需要被包装，则返回对相应的RefreshableWrapper
     */
    Refreshable createWrapper();
}
