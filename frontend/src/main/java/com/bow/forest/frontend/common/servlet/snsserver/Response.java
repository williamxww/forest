package com.bow.forest.frontend.common.servlet.snsserver;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;

public interface Response extends Serializable
{
    /**
     * 设置XStream的别名，注意该方法只会被调用一次，因为所设置的XStream对象会被缓存起来以备使用，
     * 
     * 这意味着该方法的实现中不能包含值可变化的条件判断。
     * 
     * @param xstream
     *            [需要设置的XStream对象]
     * 
     */
    public void setAlias(XStream xstream);
}
