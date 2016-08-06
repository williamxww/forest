/**  
 * @FileName: SimpleBean.java 
 * @Package beanfactory 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.beanFactoryPostProcessor;

/**
 * @ClassName: SimpleBean
 * @Description: 普通bean
 * @author ViVi
 * @date 2015年5月24日 下午8:57:15
 */

public class SimpleBean {
    private String connectionString;

    private String password;

    private String username;

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SimpleBean{connectionString=").append(this.connectionString);
        sb.append(",username=").append(this.username).append(",password=").append(this.password);
        return sb.toString();
    }
}
