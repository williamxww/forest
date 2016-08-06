/**  
 * @FileName: PersonEditor.java 
 * @Package spring.property 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.property;

import java.beans.PropertyEditorSupport;

/**
 * @ClassName: PersonEditor
 * @Description: 将注入的String转换为Person对象，然后再进行注入
 * @author ViVi
 * @date 2015年8月26日 下午8:37:03
 */

public class PersonEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        Person p = new Person();
        p.setName(text);
        setValue(p);
    }
}
