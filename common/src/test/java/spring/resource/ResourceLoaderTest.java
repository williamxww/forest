/**  
 * @FileName: ResourceLoaderTest.java 
 * @Package spring.resource 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.resource;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @ClassName: ResourceLoaderTest
 * @Description: 解释resource resourceLoader
 * @author ViVi
 * @date 2015年8月22日 下午8:37:32
 */

public class ResourceLoaderTest {

    @Test
    public void testResource() throws IOException {
        Resource resource = new ClassPathResource("spring/resource/resource.txt");
        String fileName = resource.getFilename();
        System.out.println(fileName);
        File f = resource.getFile(); // 获取资源对应的文件
        System.out.println(f.getName());
        URL u = resource.getURL(); // 获取资源对应的URL
        System.out.println(u.getPath());
    }
    
    @Test
    public void testResourceLoader(){
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = null;
        // 注意这里要用classpath: 原因看源码
        resource = loader.getResource("classpath:spring/resource/resource.txt");
        System.out.println(resource instanceof ClassPathResource); // true
    }

    /**
     * 
     * @Description: ResourcePatternResolver
     *               也是一种resourceLoader(因为他实现了ResourceLoader接口)
     *               只不过其功能更强大，能够用ant表达式匹配(AntMatcher的缘故)
     * @throws IOException
     */
    @Test
    public void testResourcePatternResolver() throws IOException {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:spring/*/*.class");
        for (Resource r : resources) {
            System.out.println(r.getURL());
        }
    }
}
