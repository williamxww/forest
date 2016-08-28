package com.bow.forest.frontend.common.serialize;

import com.esotericsoftware.kryo.Kryo;

/**
 * 注册类信息接口的定义
 * 
 * @author c59623
 * 
 */
public interface KryoTranscoderRegister
{
    /**
     * 注册类
     * 
     * @param kryo
     */
    void register(Kryo kryo);
}
