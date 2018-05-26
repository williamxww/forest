package common.java.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月1日
 * @since SDP V300R003C10
 */
public class CglibProxy implements MethodInterceptor
{
    private Object target;
    
    public Object getInstance(Object target)
    {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
        throws Throwable
    {
        System.out.println(">> enter cglib");
        proxy.invokeSuper(obj, args);
        return null;
    }
    
}
