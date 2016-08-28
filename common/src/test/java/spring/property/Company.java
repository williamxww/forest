/**  
 * @FileName: Company.java 
 * @Package spring.property 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.property;

/** 
 * @ClassName: Company 
 * @author ViVi
 * @date 2015年8月26日 下午8:29:00  
 */

public class Company {

    private String name;

    private Person employee;

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
     * @return the employee
     */
    public Person getEmployee() {
        return employee;
    }

    /**
     * @param employee
     *            the employee to set
     */
    public void setEmployee(Person employee) {
        this.employee = employee;
    }

}
