/**  
 * @FileName: PhoneNumberModel.java 
 * @Package spring.springmvc 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package spring.springmvc;

/** 
 * @ClassName: PhoneNumberModel 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年8月24日 下午10:24:06  
 */

public class PhoneNumberModel {

    private String areaCode;

    private String phoneNumber;

    /**
     * @return the areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * @param areaCode
     *            the areaCode to set
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     *            the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
