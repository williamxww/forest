/*
 * 文 件 名:  SystemConfig.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  h00101670
 * 修改时间:  2009-3-3
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

/**
 * 
 * 读取项目路径处理类
 * 
 * @author KF47558
 * @version CME01 May 16, 2011
 * @since V100R001C03LCME01
 */
public class SystemConfig
{
    /**
     * 创建一个私有的自身对象
     */
    private static SystemConfig instance = new SystemConfig();

    /**
     * 路径分割符号
     */
    public static final String FILE_SEPERATOR = System.getProperty("file.separator");

    /**
     * 项目路径
     */
    private String wwwroot = "";

    /**
     * 
     * 创建一个共有的方法返回自身的对象
     * 
     * @author KF47558
     * @return SystemConfig
     */
    public static SystemConfig getInstance()
    {
        return instance;
    }

    /**
     * 
     * 获取web容器目录
     * 
     * @author KF47558
     * @return String
     */
    public String getWwwroot()
    {
        return wwwroot;
    }

    /**
     * 
     * 设置web容器目录
     * 
     * @author KF47558
     * @param wwwroot
     *            web容器目录
     */
    public void setWwwroot(String wwwroot)
    {
        this.wwwroot = wwwroot;
    }

    /**
     * 
     * 配置文件目录
     * 
     * @author KF47558
     * @return String
     */
    public String getConfigPath()
    {
        return this.wwwroot + "WEB-INF" + FILE_SEPERATOR + "classes" + FILE_SEPERATOR + "snsserver" + FILE_SEPERATOR
            + "spring" + FILE_SEPERATOR + "import" + FILE_SEPERATOR + "memcached.xml";
    }

    /**
     * 获取系统家路径
     * 
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public String getPath()
    {
        String subStr = this.wwwroot;
        ;
        String seq = System.getProperty("file.separator");
        if (this.wwwroot.endsWith(seq))
        {
            subStr = this.wwwroot.substring(0, this.wwwroot.length() - 1);
        }
        int index = subStr.lastIndexOf(seq);
        return subStr.substring(index + 1);
    }
}
