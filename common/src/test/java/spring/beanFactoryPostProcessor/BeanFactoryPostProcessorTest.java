/**  
 * @FileName: PropertyConfigurerDemo.java 
 * @Package beanfactory 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.beanFactoryPostProcessor;

import org.junit.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @ClassName: BeanFactoryPostProcessorTest
 * @Description: 说明 BeanFactoryPostProcessor
 *               是当beanFactory初始化完后，用来对beanFactory进行修改 BeanFactoryPostProcessor
 *               实际就是容器beanFactory的一个扩这点
 * @author ViVi
 * @date 2015年5月24日 下午8:53:16
 */

public class BeanFactoryPostProcessorTest {

    /**
     * @Description: spring自带的一个BeanFactoryPostProcessor--
     *               PropertyPlaceholderConfigurer 当发现${} 就从属性文件中取值代替
     */
    @Test
    public void testPostProcesssor() {
        ConfigurableListableBeanFactory bf = new XmlBeanFactory(new ClassPathResource(
                "spring/beanFactoryPostProcessor/postprocessor.xml"));
        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor) bf.getBean("bfpp");
        // 用BeanFactoryPostProcessor的实现类(PropertyPlaceholderConfigurer)去更改beanFactory;
        bfpp.postProcessBeanFactory(bf);
        System.out.println(bf.getBean("simpleBean"));
    }

    @Test
    public void testMyPostProcesssor() {
        ConfigurableListableBeanFactory bf = new XmlBeanFactory(new ClassPathResource(
                "spring/beanFactoryPostProcessor/postprocessor.xml"));
        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor) bf.getBean("mybfpp");
        bfpp.postProcessBeanFactory(bf);
        System.out.println(bf.getBean("simpleBean"));
    }

    /**
     * @Description: 自定义一个BeanFactoryPostProcessor 用来对容器中所有的bean的属性进行检查
     *               发现肮脏的词汇就换成'*'
     */
    @Test
    public void testCustomeizePostProcessor() {
        ConfigurableListableBeanFactory bf = new XmlBeanFactory(new ClassPathResource(
                "spring/beanFactoryPostProcessor/postprocessor.xml"));
        BeanFactoryPostProcessor bfpp = (BeanFactoryPostProcessor) bf.getBean("obscenePostProcessor");
        bfpp.postProcessBeanFactory(bf);
        System.out.println(bf.getBean("sensltiveBean"));
    }

    @Test
    public void testModifyBeanDefinitionPostProcessor() {
        ConfigurableListableBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
                "spring/beanFactoryPostProcessor/postprocessor.xml"));
        BeanDefinition bd = beanFactory.getBeanDefinition("simpleBean");
        MutablePropertyValues mpvs = bd.getPropertyValues();
        if (mpvs.contains("password")) {
            PropertyValue pv = mpvs.getPropertyValue("password");
            Object source = pv.getSource();
            PropertyValue npv = new PropertyValue("password", "123");
            npv.setSource(source);
            mpvs.removePropertyValue("password");

            // 修改后重新添加
            mpvs.add("password", npv);
        }
        System.out.println(beanFactory.getBean("simpleBean"));
    }
}
