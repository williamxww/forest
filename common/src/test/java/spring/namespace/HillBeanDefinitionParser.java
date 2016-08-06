package spring.namespace;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月21日
 * @since SDP V300R003C10
 */
public class HillBeanDefinitionParser implements BeanDefinitionParser
{
    public HillBeanDefinitionParser(Class<?> beanClass)
    {
        this.beanClass = beanClass;
    }
    
    private Class<?> beanClass;
    
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        String id = element.getAttribute("id");
        String name = element.getAttribute("name");
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("name", name);
        return beanDefinition;
    }
    
}
