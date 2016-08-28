/*
 * 文 件 名:  Utils.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  吴飞00106856
 * 修改时间:  2009-5-10
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 吴飞00106856
 * @version [1.0, 2009-5-10]
 * @see [相关类/方法]
 * @since [iRead/Server1.0]
 */
public final class Utils
{
    public final static String LINE_SEP = System.getProperty("line.separator");
    
    public final static String FILE_SEP = System.getProperty("file.separator");
    
    public final static int FILL_LEFT = 0;// 左填充
    
    public final static int FILL_RIGHT = 1;// 右填充
    
    /**
     * 检验字符串的内容是否是在整型范围内的非负整数 <功能详细描述>
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotNegativeInt(String str)
    {
        int checkInt;
        try
        {
            checkInt = Integer.parseInt(str);
            if (0 > checkInt)
            {
                return false;
            }
            return true;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 检验字符串的内容是否是在整形范围内的数字 <功能详细描述>
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLong(String str)
    {
        try
        {
            Long.parseLong(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /**
     * 检验字符串的内容是否是浮点格式 <功能详细描述>
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isFloat(String str)
    {
        try
        {
            Float.parseFloat(str);
            return true;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /**
     * 给字符串去掉空格 <功能详细描述>
     * 
     * @param arg
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String trim(String arg)
    {
        if (null == arg)
        {
            return "";
        }
        else
        {
            return arg.trim();
        }
    }
    
    /**
     * 检查字符串是否为空，字符串为null，或者长度为0都认为为空 <功能详细描述>
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String str)
    {
        if (null == str)
        {
            return true;
        }
        
        if (0 == str.trim().length())
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断指定的对象是否为空
     * 
     * @param obj
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object obj)
    {
        if (null == obj)
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断指定的对象是否为空
     * 
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        if ((null == map) || (map.isEmpty()))
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断指定的字符串数组是否为空或长度为0 <功能详细描述>
     * 
     * @param strArr
     *            字符串数组
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(String[] strArr)
    {
        if ((null == strArr) || (strArr.length < 1))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断指定的对象数组是否为空
     * 
     * @param objArr
     *            对象数组
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Object[] objArr)
    {
        if ((null == objArr) || (objArr.length < 1))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断指定的对象列表是否为空
     * 
     * @param lst
     *            指定的对象列表
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(List<?> lst)
    {
        if ((null == lst) || (lst.isEmpty()))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断指定对象集合是否为空
     * 
     * @param set
     *            指定对象集合
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmpty(Set<?> set)
    {
        if ((null == set) || (set.isEmpty()))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 检查字符串是否为空，字符串为null，或者长度为0都认为为空 <功能详细描述>
     * 
     * @param str
     * @return [参数说明]
     * 
     * @return boolean [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static boolean isInt(String str)
    {
        if (isEmpty(str))
        {
            return false;
        }
        
        try
        {
            Integer.parseInt(str.trim());
        }
        catch (NumberFormatException ex)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * 计算指定字符串的MD5摘要
     * 
     * @param strSrc
     *            指定的原字符串
     * @return 计算后的字节数组
     */
    public static byte[] getMD5(String strSrc)
    {
        if (null == strSrc)
        {
            return null;
        }
        
        byte[] bArrRst = null;// 返回的字节数组
        try
        {
            byte[] passwdtmp = strSrc.getBytes("UTF-8");
            MessageDigest md5 = MessageDigest.getInstance("MD5");// MD5
            md5.update(passwdtmp, 0, passwdtmp.length);
            bArrRst = md5.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return bArrRst;
    }
    
    /**
     * 对指定的字符串进行格式化，如果待格式化的字符串大于指定的长度则直接返回，否则按照指定的填充位置进行填充
     * 
     * @param strSrc
     *            待格式的字符串
     * @param len
     *            格式化后的长度
     * @param fillChar
     *            填充字符
     * @param fillFlag
     *            填充标志,0:左填充,1或其他:右填充
     * @return [参数说明] 格式化后的字符串
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static String format(String strSrc, int len, char fillChar, int fillFlag)
    {
        if (isEmpty(strSrc))
        {
            strSrc = "";
        }
        else
        {
            strSrc = strSrc.trim();
            if (strSrc.length() >= len)
            {
                return strSrc;
            }
        }
        
        StringBuilder sbTmp = new StringBuilder(len + 2);
        sbTmp.append(strSrc);// 添加需要格式化的源字符串
        int iLen = len - strSrc.length();// 需要填充的长度
        char[] fillCharArr = new char[iLen];
        Arrays.fill(fillCharArr, fillChar);// 填充指定的字符数组
        if (FILL_LEFT == fillFlag)
        {
            sbTmp.insert(0, fillCharArr);
        }
        else if (FILL_RIGHT == fillFlag)
        {
            sbTmp.append(fillCharArr);
        }
        else
        // 异常情况记录错误
        {
            // log.error("fillFlag is invalid");
        }
        
        return sbTmp.toString();
    }
    
    /**
     * 获取指定名称资源的输入流
     * 
     * @param name
     *            指定的资源名称
     * @param clz
     *            指定的class
     * @return [参数说明]
     * 
     * @return InputStream [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    public static InputStream getResourceAsStream(String name, Class<?> clz)
    {
        ClassLoader loader = null;
        InputStream retval = null;
        
        try
        {
            loader = Thread.currentThread().getContextClassLoader();
            if (loader != null)
            {
                retval = loader.getResourceAsStream(name);
                if (retval != null)// 如果使用当前的类加载器获取到了输入流则直接返回
                {
                    return retval;
                }
            }
        }
        catch (Throwable t)
        {
        }
        
        if (clz != null)// 如果参数中传递的类不为空则使用当前的类获取资源的输入流
        {
            try
            {
                loader = clz.getClassLoader();
                if (loader != null)
                {
                    retval = loader.getResourceAsStream(name);
                    if (retval != null)
                    {
                        return retval;
                    }
                }
            }
            catch (Throwable t)
            {
            }
        }
        
        // 使用系统类加载其加载
        try
        {
            loader = ClassLoader.getSystemClassLoader();
            if (loader != null)
            {
                return loader.getResourceAsStream(name);
            }
        }
        catch (Throwable t)
        {
        }
        
        return retval;
    }
    
    private static Class<?> genClz(String clzName)
    {
        Class<?> clz = null;
        try
        {
            clz = Class.forName(clzName);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        
        return clz;
    }
    
    private Utils()
    {
        
    }
    
    private static String localAddress = null;
    
    /**
     * 获取本机第一个前缀匹配到的IP地址，匹配不到则返回null
     */
    public static String getMatchAddress(String prefix)
    {
        try
        {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements())
            {
                NetworkInterface netface = (NetworkInterface)e.nextElement();
                Enumeration eAddress = netface.getInetAddresses();
                
                while (eAddress.hasMoreElements())
                {
                    InetAddress inetAddress = (InetAddress)eAddress.nextElement();
                    String hostAddress = inetAddress.getHostAddress();
                    if (hostAddress != null && hostAddress.startsWith(prefix))
                    {
                        return hostAddress;
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        
        return null;
    }
    
    /**
     * 获取本机的IP地址，如没有则返回127.0.0.1
     */
    public static String getAddress()
    {
        if (localAddress != null)
        {
            return localAddress;
        }
        
        try
        {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            found: while (e.hasMoreElements())
            {
                NetworkInterface netface = (NetworkInterface)e.nextElement();
                Enumeration eAddress = netface.getInetAddresses();
                
                while (eAddress.hasMoreElements())
                {
                    InetAddress inetAddress = (InetAddress)eAddress.nextElement();
                    String hostAddress = inetAddress.getHostAddress();
                    
                    if (!inetAddress.isLoopbackAddress() && isIpAddress(hostAddress))
                    {
                        localAddress = hostAddress;
                        break found;
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        
        return (localAddress == null) ? "127.0.0.1" : localAddress;
    }
    
    /**
     * 判断是否为ip4地址
     */
    public static boolean isIpAddress(String ipAddress)
    {
        if (ipAddress == null)
        {
            return false;
        }
        
        String[] ipArray = ipAddress.trim().split("\\.");
        if (ipArray.length != 4)
        {
            return false;
        }
        
        for (int i = 0; i < 4; i++)
        {
            try
            {
                int num = Integer.parseInt(ipArray[i]);
                if (num < 0 || num > 255)
                {
                    return false;
                }
            }
            catch (Exception e)
            {
                return false;
            }
        }
        
        return true;
    }

    
    //计数
    private static Integer times = 0;
    


    
    private static String getCurrentTime()
    {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date());
        
    }
    
    /**
     * 获取Ip地址后面3位 <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String subIpStr()
    {
        // 获取本地Ip
        String ipStr = Utils.getAddress();
        if (null == ipStr || "".equals(ipStr))
        {
            return "000";
        }
        int index = ipStr.lastIndexOf(".");
        // 未找到
        if (-1 == index || (ipStr.length() - 1) == index)
        {
            return "000";
        }
        String subIpStr = ipStr.substring(index + 1);
        if (1 == subIpStr.length())
        {
            return "00" + subIpStr;
        }
        if (2 == subIpStr.length())
        {
            return "0" + subIpStr;
        }
        return subIpStr;
    }
}
