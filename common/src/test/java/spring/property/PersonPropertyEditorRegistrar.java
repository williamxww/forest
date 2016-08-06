/**  
 * @FileName: PersonPropertyEditorRegistrar.java 
 * @Package spring.property 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.property;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

/** 
 * @ClassName: PersonPropertyEditorRegistrar 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年8月26日 下午9:33:43  
 */

public class PersonPropertyEditorRegistrar implements PropertyEditorRegistrar {

    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Person.class, new PersonEditor());
    }

}
