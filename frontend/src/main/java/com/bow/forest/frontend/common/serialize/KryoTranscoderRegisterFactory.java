package com.bow.forest.frontend.common.serialize;

/**
 * 注册工厂的定义
 * 
 * @author c59623
 * 
 */
public class KryoTranscoderRegisterFactory
{

    /**
     * 获取注册对象
     * 
     * @return KryoTranscoderRegister
     */
    public static KryoTranscoderRegister getRegister()
    {

        return new KryoTranscoderRegisterImpl();
    }
}
