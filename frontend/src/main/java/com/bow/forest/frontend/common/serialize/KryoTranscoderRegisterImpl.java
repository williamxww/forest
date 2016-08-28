package com.bow.forest.frontend.common.serialize;

import com.esotericsoftware.kryo.Kryo;

/**
 * 序列化注册的实现类
 * 
 * @author c59623
 * 
 */
public class KryoTranscoderRegisterImpl implements KryoTranscoderRegister
{

    /**
     * 注册的方法
     */
    public void register(Kryo kryo)
    {

        //kryo.setRegistrationOptional(true);
    }

}
