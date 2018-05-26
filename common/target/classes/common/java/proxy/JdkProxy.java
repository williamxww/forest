package common.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月1日
 * @since SDP V300R003C10
 */
public class JdkProxy implements InvocationHandler
{
    
    private Object target;
    
    public Object bind(Object target)
    {
        this.target = target;
        return Proxy.newProxyInstance(JdkProxy.class.getClassLoader(), target.getClass().getInterfaces(), this);
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        Object result = null;
        System.out.println(">> enter jdkProxy");
        result = method.invoke(target, args);
        return result;
    }
    
}
