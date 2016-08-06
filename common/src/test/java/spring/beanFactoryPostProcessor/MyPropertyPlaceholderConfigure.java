/**  
 * @FileName: MyPropertyPlaceholderConfigure.java 
 * @Package spring.beanFactoryPostProcessor 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.beanFactoryPostProcessor;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/** 
 * @ClassName: MyPropertyPlaceholderConfigure 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年9月12日 下午1:45:44  
 */

public class MyPropertyPlaceholderConfigure extends PropertyPlaceholderConfigurer {

    @Override
    public String convertProperty(String propertyName, String propertyValue) {
        if ("password" == propertyName) {
            // 进行密码解码
        }
        return super.convertProperty(propertyName, "aaaa");
    }
}
