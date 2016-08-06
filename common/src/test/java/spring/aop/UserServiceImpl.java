/**  
 * @FileName: UserServiceImpl.java 
 * @Package proxy 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.aop;

/**
 * @ClassName: UserServiceImpl
 * @Description: JDK动态代理
 * @author ViVi
 * @date 2015年6月3日 下午10:54:04
 */

public class UserServiceImpl implements UserService {

    /*
     * (非 Javadoc)
     * 
     * 
     * @see proxy.UserService#addUser()
     */
    @Override
    public void addUser() {
        System.out.println("增加用户");

    }
    
    @Override
    public void deleteUser()
    {
        System.out.println("删除用户");
    }

}
