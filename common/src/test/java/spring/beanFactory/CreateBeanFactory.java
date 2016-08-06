/**  
 * @FileName: CreateBeanFactory.java 
 * @Package spring.beanFactory 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.beanFactory;

import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName: CreateBeanFactory
 * @Description: 代码创建IOC容器
 * @author ViVi
 * @date 2015年8月22日 下午2:45:36
 */

public class CreateBeanFactory {

    @Test
    public void testCreate() {
        ClassPathResource resource = new ClassPathResource("spring/beanFactory/beans.xml");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(resource);

    }
}
