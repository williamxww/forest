package common.java.jndi;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

/**
 * JNDI （ Java Naming and Directory Interface ）JNDI 包含了命名服务和目录服务两部分
 * <p>
 * 名字：通过名字我们可以来查找关联的对象，格式可能根据系统不同，命名规范也会有所不同。
 * <p>
 * 绑定：将名字和一个对象进行关联
 * <p>
 * 上下文：它在其中存储了名字和对象绑定的集合
 * 并且提供了统一绑定、取消绑定、查找、创建子上下文等常用操作，
 * 也就是说这里绑定的对象也可以是另外一个上下文，熟称子上下文。上下文存储结构可以理解成我们常用目录，
 * 目录中的文件就是我们的对象，当然目录中也可以再有目录。
 * Created by vv on 2016/8/27.
 */
public class JndiDemo {

    /**
     * 开启一个JNDI服务，将对象绑定到名字上面
     * 启动的就是基于RMI的JNDI服务
     */
    public void init() {
        try {
            LocateRegistry.createRegistry(3000);
            //可以通过properties的形式设置工厂类，也可以直接通过编码来设置
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            System.setProperty(Context.PROVIDER_URL, "rmi://localhost:3000");

            InitialContext ctx = new InitialContext();
            Person p = new Person();
            p.setName("vv");
            ctx.bind("person", p);
            ctx.close();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取JNDI服务商绑定的对象
     */
    public void find() {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
            Person person = (Person) ctx.lookup("person");
            System.out.println(person.toString());
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO
     */
    public void testFile() {

        Properties properties = new Properties();
        try {
            //
            properties.load(JndiDemo.class.getClassLoader().getResourceAsStream("common/java/jndi/jndi.properties"));
            properties.list(System.out);
            InitialContext context = new InitialContext(properties);

//            context.createSubcontext("/shine");
//            context.createSubcontext("/shine/services");
//            context.createSubcontext("/shine/consumers");
//
//            context.rebind("/shine/services/calculateService","calculateService");
//            context.rebind("/shine/services/helloService","helloService");
//
//            String service = (String)context.lookup("/shine/services/calculateService");
//            System.out.println(service);
            context.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        JndiDemo demo = new JndiDemo();
//        demo.init();
//        demo.find();
        demo.testFile();
    }
}
