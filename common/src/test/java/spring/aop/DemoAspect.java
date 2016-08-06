package spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月21日
 * @since SDP V300R003C10
 */
public class DemoAspect
{
    public void handleAround(ProceedingJoinPoint pjp)
    {
        Signature signature = pjp.getSignature();
        String methodName = signature.getName();
        System.out.println("methodName:" + methodName);
        try
        {
            Object result = pjp.proceed();
            System.out.println("result:" + result);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }

    }
}
