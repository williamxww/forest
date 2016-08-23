package spring.proxy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by vv on 2016/8/14.
 */
public class SpringProxyTest {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        new String[] { "classpath:spring/proxy/proxy.xml" });
        IHello service = (IHello)context.getBean("proxy");
        service.sayHello();
    }
    @Test
    public void testAop() {

    }
}
