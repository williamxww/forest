package spring.proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Created by vv on 2016/8/14.
 */
public class MyInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        String info = methodInvocation.getMethod().getDeclaringClass()+ "." +
                methodInvocation.getMethod().getName() + "()";

        System.out.println(info);

        try{
            Object result = methodInvocation.proceed();
            return result;
        }
        finally{
            System.out.println(info);
        }
    }
}
