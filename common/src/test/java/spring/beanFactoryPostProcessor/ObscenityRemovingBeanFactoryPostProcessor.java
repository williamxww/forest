/**  
 * @FileName: ObscenityRemovingBeanFactoryPostProcessor.java 
 * @Package beanfactory 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.beanFactoryPostProcessor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.StringValueResolver;

/**
 * @ClassName: ObscenityRemovingBeanFactoryPostProcessor
 * @Description: 自定义BeanFactoryPostProcessor 对beanFactory进行肮脏字符过滤
 * @author ViVi
 * @date 2015年5月24日 下午9:22:26
 */

public class ObscenityRemovingBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    
    private Set<String> obscenties = new HashSet<String>();

    public ObscenityRemovingBeanFactoryPostProcessor(){
        
    }
    

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames=beanFactory.getBeanDefinitionNames();  
        for(String beanName:beanNames){  
            BeanDefinition bd = beanFactory.getBeanDefinition(beanName);

            // 定义StringValueResolver 当BeanDefinitionVisitor发现色情语句就替换
            StringValueResolver valueResover=new StringValueResolver() {  
                public String resolveStringValue(String strVal) {  
                    if (isObscene(strVal)) {
                        return "*****";
                    }
                    return strVal;  
                }  
            };  
            BeanDefinitionVisitor visitor=new BeanDefinitionVisitor(valueResover);  
            visitor.visitBeanDefinition(bd);
        }  

    }
    
    public boolean isObscene(Object value){  
        String potentialObscenity=value.toString().toUpperCase();  
        return this.obscenties.contains(potentialObscenity);  
    }  
    public void setObscenties(Set<String> obscenties) {  
        this.obscenties.clear();  
        for(String obscenity:obscenties){  
            this.obscenties.add(obscenity.toUpperCase());  
        }  
    }

}
