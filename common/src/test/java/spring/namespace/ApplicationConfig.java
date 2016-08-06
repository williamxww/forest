package spring.namespace;

/**
 * TODO 添加类的描述
 *
 * @author acer
 * @version C10 2016年2月21日
 * @since SDP V300R003C10
 */
public class ApplicationConfig
{
    private String id;

    private String name;
    
    private String version;
    
    /**
     * 取得id
     * 
     * @return 返回id。
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * 设置id
     * 
     * @param id 要设置的id。
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * 取得version
     * 
     * @return 返回version。
     */
    public String getVersion()
    {
        return version;
    }
    
    /**
     * 设置version
     * 
     * @param version 要设置的version。
     */
    public void setVersion(String version)
    {
        this.version = version;
    }

    /**
     * 取得name
     * 
     * @return 返回name。
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * 设置name
     * 
     * @param name 要设置的name。
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
