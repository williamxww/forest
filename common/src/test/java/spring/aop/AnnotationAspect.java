package spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 完全通过注解来配置aop
 *
 * @author acer
 * @version C10 2016年4月24日
 * @since SDP V300R003C10
 */
@Aspect
public class AnnotationAspect
{
    @Pointcut("execution(* spring.aop.AnnotationAspectService.*(..))")
    public void anyMethod()
    {
    
    }
    
    @Before("anyMethod() && args(name)")
    public void doAccessCheck(String name)
    {
        System.out.println(name);
        System.out.println("前置通知");
    }
    
    @AfterReturning("anyMethod()")
    public void doAfter()
    {
        System.out.println("后置通知");
    }
    
    @After("anyMethod()")
    public void after()
    {
        System.out.println("最终通知");
    }
    
    @AfterThrowing("anyMethod()")
    public void doAfterThrow()
    {
        System.out.println("例外通知");
    }
    
    @Around("anyMethod()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp)
        throws Throwable
    {
        System.out.println("进入环绕通知");
        Object object = pjp.proceed();// 执行该方法
        System.out.println("退出方法");
        return object;
    }

}
