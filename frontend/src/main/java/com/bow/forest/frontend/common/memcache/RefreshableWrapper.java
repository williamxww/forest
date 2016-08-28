package com.bow.forest.frontend.common.memcache;

public abstract class RefreshableWrapper implements Refreshable
{
    private Refreshable value;
    
    public RefreshableWrapper(Refreshable value)
    {
        this.value = value;
    }
    
    public Refreshable getValue()
    {
        return value;
    }
    
    /**
     * 判断当前被包装的value是否需要刷新
     * @return
     *      true，需要刷新则， false，不需要刷新
     */
    public abstract boolean needRefresh();
}
