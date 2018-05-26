package serialize;


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
