/*
 * 文 件 名:  snsserverConfig.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  吴飞00106856
 * 修改时间:  2009-4-3
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import javax.servlet.ServletContext;


public final class ServerConfig
{
    private static ServerConfig instance = new ServerConfig();

    private String serverHome = "";

    /** 本地IP地址，如：192.168.12.12 */
    private String localIP = "127.0.0.1";

    /**
     * 防止外部实例化
     */
    private ServerConfig()
    {

    }

    public static ServerConfig getInstance()
    {
        return instance;
    }

    public void init(ServletContext context)
    {
        serverHome = context.getRealPath("/");
        localIP = Utils.getAddress();
    }

    public void init(String serverHome)
    {
        this.serverHome = serverHome;
        localIP = Utils.getAddress();
    }
    /**
     * @return 返回 snsserverHome
     */
    public String getServerHome()
    {
        return serverHome;
    }

    /**
     * @return 返回 localIP
     */
    public String getLocalIP()
    {
        return localIP;
    }

}
