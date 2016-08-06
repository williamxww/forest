/**  
 * @FileName: AopTest.java 
 * @Package aop 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.aop;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @ClassName: AopTest
 * @Description: testAop
 * @author ViVi
 * @date 2015年6月7日 下午4:06:34
 */

public class AopTest {
    
    /**
     * 
     * 用代理的方式做aop
     *
     * @author acer
     */
    @Test
    public void testAop() {
        @SuppressWarnings("resource")
        ApplicationContext context =
            new ClassPathXmlApplicationContext(
                new String[] { "classpath:spring/aop/aopContext.xml" });
        UserService service = (UserService)context.getBean("proxy");
        service.addUser();
    }
    
    @SuppressWarnings("resource")
    @Test
    public void testAroundAop()
    {
        ApplicationContext context =
            new ClassPathXmlApplicationContext(new String[] {"classpath:spring/aop/aopContext.xml"});
        SalaryService service = context.getBean("salaryService", SalaryService.class);
        service.addSalary();
    }
    
    @Test
    public void testAnnotationAop()
    {
        ApplicationContext context =
            new ClassPathXmlApplicationContext(new String[] {"classpath:spring/aop/aopContext.xml"});
        AnnotationAspectService service = context.getBean("annotationAspectService", AnnotationAspectService.class);
        service.test();
    }
}
