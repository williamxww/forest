/**  
 * @FileName: Person.java 
 * @Package spring.property 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.property;

/** 
 * @ClassName: Person 
 * @author ViVi
 * @date 2015年8月26日 下午8:17:35  
 */

public class Person {

    private String name;

    private int age;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
