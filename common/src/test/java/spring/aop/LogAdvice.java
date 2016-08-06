/**  
 * @FileName: LogAdvice.java 
 * @Package aop 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * @ClassName: LogAdvice
 * @Description: 测试AOP
 * @author ViVi
 * @date 2015年6月7日 下午3:47:04
 */

public class LogAdvice implements MethodBeforeAdvice {


    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("MethodBeforeAdvice");

    }

}
