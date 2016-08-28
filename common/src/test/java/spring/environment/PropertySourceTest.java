/**
 * @FileName: PropertySourceTest.java
 * @Package spring.environment
 * all rights reserved by Hill team
 * @version v1.3
 */
package spring.environment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;

/**
 * @ClassName: PropertySourceTest
 * @Description: PropertySource 属性源;
 *
 *               PropertySources即为PropertySource的集合;
 *
 *               PropertyResolver 属性解析器,解析propertySources
 * @author ViVi
 * @date 2015年8月24日 下午9:33:50
 */

public class PropertySourceTest {

    @Test
    public void testPropertySource() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("encoding", "gbk");
        PropertySource propertySource1 = new MapPropertySource("map", map);
        PropertySource propertySource2 = new ResourcePropertySource("resource",
                "classpath:spring/environment/resources.properties");
        System.out.println(propertySource2.getProperty("name_key"));

        //CompositePropertySource 可以将多种PropertySource聚合在一起
        CompositePropertySource compositePropertySource = new CompositePropertySource("composite");
        compositePropertySource.addPropertySource(propertySource1);
        compositePropertySource.addPropertySource(propertySource2);
        System.out.println(compositePropertySource.getProperty("encoding"));

        // MutablePropertySources 是PropertySource的集合
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource1);
        propertySources.addLast(propertySource2);
        System.out.println(propertySources.get("resource").getProperty("author"));
    }

    /**
     *
     * @Description: 解析propertySources,除了能从集合中获取指定key的值外，还可以解析占位符
     * @throws IOException
     */
    @Test
    public void testPropertyResolver() throws IOException {
        PropertySource propertySource = new ResourcePropertySource("resource",
                "classpath:spring/environment/resources.properties");
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(propertySource);
        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);

        System.out.println(propertyResolver.getProperty("name_key", "default"));
        System.out.println(propertyResolver.resolvePlaceholders("value must be ${name1}"));// 占位符
    }

    @Test
    public void testEnvironment() {
        Environment env = new StandardEnvironment();
        System.out.println(env.getProperty("file.encoding"));
    }
}
