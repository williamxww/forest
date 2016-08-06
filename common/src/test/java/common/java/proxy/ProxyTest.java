package common.java.proxy;

import org.junit.Test;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月1日
 * @since SDP V300R003C10
 */
public class ProxyTest
{
    @Test
    public void testJdkProxy()
    {
        JdkProxy proxy = new JdkProxy();
        Handler handler = (Handler)proxy.bind(new HandlerImpl());
        handler.handle();
    }
    
    @Test
    public void testCglibProxy()
    {
        CglibProxy proxy = new CglibProxy();
        HandlerNoInterface handler = (HandlerNoInterface)proxy.getInstance(new HandlerNoInterface());
        handler.calc();
    }
}
