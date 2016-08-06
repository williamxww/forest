package spring.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月28日
 * @since SDP V300R003C10
 */
public class Client
{
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("classpath:spring/rmi/client/applicationContext-client.xml");
        Caculate c = context.getBean("caculateService", Caculate.class);
        System.out.println(c.addOne(2));
    }
}
