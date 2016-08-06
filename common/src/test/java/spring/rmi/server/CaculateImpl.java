package spring.rmi.server;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月28日
 * @since SDP V300R003C10
 */
public class CaculateImpl implements Caculate
{

    @Override
    public int addOne(int a)
    {
        return a + 1;
    }
    
}
