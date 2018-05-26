package serialize;

import com.esotericsoftware.kryo.Kryo;


public interface KryoTranscoderRegister
{
    /**
     * 注册类
     * 
     * @param kryo
     */
    void register(Kryo kryo);
}
