/*
 * 文 件 名:  HandlerCenter.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  吴飞00106856
 * 修改时间:  2009-1-20
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;

/**
 * 请求处理方法控制类，实现为不同的请求提供相应的方法
 *
 */
public class HandlerCenter
{
    /** 配置文件根元素名称 */
    private static final String rootName = "snsserver";

    /** 处理单元节点名 */
    private static final String nodeName = "action";

    /** 节点属性 */
    private static final String actionAttr = "path";

    /** 单实例 */
    private static final HandlerCenter instance = new HandlerCenter();

    /** 路径与处理单元对应Map */
    private Map<String, HandlerElement> heMap = new HashMap<String, HandlerElement>();

    /** 类名与处理实例对应Map */
    private Map<String, Object> handlerMap = new HashMap<String, Object>();

    /*
     * 防止外部实例化
     */
    private HandlerCenter()
    {

    }

    /**
     * 获取本类的实例
     * 
     * @return HandlerCenter [本类的实例]
     */
    public static HandlerCenter getInstance()
    {
        return instance;
    }

    /**
     * 获取处理元素
     * 
     * @param name
     *            处理名称
     * @return HandlerElement [处理元素]
     */
    public HandlerElement getHandlerElement(String name)
    {
        return heMap.get(name);
    }

    /**
     * 处理对象的初始化方法，只能被servlet初始化时调用，处理过程： 读取并解析dispatcher-config.xml文件，将其转化为HandlerElement对象
     * 
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     *             找不到dispatcher-config.xml文件时抛出
     */
    public void init()
        throws InstantiationException,
            IllegalAccessException,
            SecurityException,
            NoSuchMethodException,
            ClassNotFoundException,
            FileNotFoundException
    {
        Config ireadConfig = getConfig("/snsserver/common/dispatcher-config.xml");

        dealConfig(ireadConfig); // 手机阅读
    }

    /**
     * 遍历Config对象，把HandlerElement对象放入单元对应heMap中 <功能详细描述>
     * 
     * @param config
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     *             [参数说明]
     * 
     * @return void [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private void dealConfig(Config config)
        throws InstantiationException,
            IllegalAccessException,
            SecurityException,
            NoSuchMethodException,
            ClassNotFoundException,
            FileNotFoundException
    {
        for (Element e : config.actions)
        {
            Class<?> rc = null;
            if (StringUtils.isNotEmpty(e.requestClass))
            {
                rc = Class.forName(e.requestClass.trim());
            }

            String className = e.handleClass.trim();

            Class<?> c = null;
            try
            {
                c = Class.forName(className);
            }
            catch (Exception ex)
            {
                // 出现异常，则表明缺少某些业务类，但不影响其他业务类的加载，方便开发时调试
                ex.printStackTrace();
                continue;
            }

            Object handler = getHandler(c);

            Method method;
            if (rc == null)
            {
                method = c.getMethod(e.handleMethod.trim());
            }
            else
            {
                method = c.getMethod(e.handleMethod.trim(), rc);
            }

            HandlerElement he = new HandlerElement(handler, method);

            if ((rc != null) && Request.class.isAssignableFrom(rc))
            {
                he.setRequestClass(rc);
            }

            // 将所有的HandlerElement对象都缓存起来
            heMap.put(e.path.trim(), he);
        }
    }

    // 读取并解析dispatcher-config.xml文件
    private Config getConfig(String path)
    {
        XStream xstream = new XStream();
        xstream.alias(rootName, Config.class);
        xstream.alias(nodeName, Element.class);
        xstream.useAttributeFor(actionAttr, String.class);

        InputStream in = null;
        try
        {
            in = HandlerCenter.class.getResourceAsStream(path);
            return (Config) xstream.fromXML(in);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    // 该异常无须处理
                }
            }
        }
    }

    /**
     * 获取handle-config.xml文件的处理实例
     * 
     *            类名
     * @param c
     *            类实例
     * @return 请求的处理实例
     * @throws InstantiationException
     * @throws IllegalAccessException
     *             [参数说明]
     * 
     * @return Object 真正的处理实例
     */
    private Object getHandler(Class<?> c) throws InstantiationException, IllegalAccessException
    {
        String className = c.getName();
        Object handler = handlerMap.get(className);

        // 为防止这些实例反复生成，故使用handlerMap将其缓存
        if (handler == null)
        {
            handler = c.newInstance();
            handlerMap.put(className, handler);
        }
        return handler;
    }

    private static class Config
    {
        public Config()
        {
        }

        public List<Element> actions;
    }

    private static class Element
    {
        public Element()
        {
        }

        public String path;

        public String handleClass;

        public String handleMethod;

        public String requestClass;
    }

}
