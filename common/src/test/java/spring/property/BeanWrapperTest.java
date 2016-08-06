/**  
 * @FileName: BeanWrapperTest.java 
 * @Package spring.property 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.property;

import org.junit.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * @ClassName: BeanWrapperTest 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年8月26日 下午8:30:24  
 */

public class BeanWrapperTest {

    @Test
    public void testBeanWrapper() {
        BeanWrapper company = new BeanWrapperImpl(new Company());
        // setting the company name..
        company.setPropertyValue("name", "BOW");
        // ... can also be done like this:
        PropertyValue value = new PropertyValue("name", "bow.com");
        company.setPropertyValue(value);

        // ok, let's create the director and tie it to the company:
        Person p = new Person();
        p.setAge(25);
        BeanWrapper vv = new BeanWrapperImpl(p);
        vv.setPropertyValue("name", "vv");
        company.setPropertyValue("employee", vv.getWrappedInstance());

        // retrieving the salary of the managingDirector through the company
        int age = (int) company.getPropertyValue("employee.age");
        System.out.println(age);
    }

    /**
     * 
     * @Description: xml中给Company的employee注入的是
     *               字符串，通过propertyEditor转换成Person对象然后注入
     * 
     *               注意：在此过程中是通过PropertyEditorRegistrar进行editor注册的
     */
    @Test
    public void testPropertyEditor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "spring/property/applicationContext-property.xml" });
        Company company = (Company) context.getBean("sample");
        System.out.println(company.getEmployee().getName());
    }

}
