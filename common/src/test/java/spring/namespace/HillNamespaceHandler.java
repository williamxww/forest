package spring.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月21日
 * @since SDP V300R003C10
 */
public class HillNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("application", new HillBeanDefinitionParser(ApplicationConfig.class));
    }
}
