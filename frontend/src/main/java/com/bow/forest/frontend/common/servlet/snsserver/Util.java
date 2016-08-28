
package com.bow.forest.frontend.common.servlet.snsserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 提供常用操作
 * 
 * @author l65566
 * @version [版本号, 2010-9-4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class Util
{
    public final static String LINE_SEP = System.getProperty("line.separator");

    public final static String FILE_SEP = System.getProperty("file.separator");

    public final static String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + LINE_SEP;

    public final static int FILL_LEFT = 0;// 左填充

    public final static int FILL_RIGHT = 1;// 右填充

    /**
     * 防止外部实例化该类
     */
    private Util()
    {
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
     * 删除字符串两端的空格，如果字符串为空串则返回指定的缺省值
     * 
     * @param arg
     *            指定的字符串
     * @param def
     *            指定的缺省值
     * @return 处理后的字符串
     * @see [类、类#方法、类#成员]
     */
    public static String trim(String arg, String def)
    {
        if (isEmpty(arg))
        {
            return def;
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
     * 是否非空
     * @author l65566
     * @param str 待判断的字符串
     * @return 非空返回true，否则返回false
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
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
     * 判断指定的对象是否非空
     * @author l65566
     * @param obj 指定的对象
     * @return 非空返回true，否则返回false
     */
    public static boolean isNotEmpty(Object obj)
    {
        return !isEmpty(obj);
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
        boolean isMapEmpty = ((null == map) || map.isEmpty());
        if (isMapEmpty)
        {
            return true;
        }

        return false;
    }
    
    /**
     * 判断指定的对象是否非空
     * @author l65566 
     * @param map 指定的对象
     * @return 指定的对象为空返回true，否则返回false
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
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
        boolean isStrArrEmpty = ((null == strArr) || (strArr.length < 1));
        if (isStrArrEmpty)
        {
            return true;
        }
        return false;
    }
    
    public static boolean isNotEmpty(String[] strArr)
    {
        return !isEmpty(strArr);
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
        boolean isObjArrEmpty = ((null == objArr) || (objArr.length < 1));
        if (isObjArrEmpty)
        {
            return true;
        }
        return false;
    }

    
    public static boolean isNotEmpty(Object[] objArr)
    {
        return !isEmpty(objArr);
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
        boolean isListEmpty = ((null == lst) || lst.isEmpty());
        if (isListEmpty)
        {
            return true;
        }
        return false;
    }
    
    public static boolean isNotEmpty(List<?> lst)
    {
        return !isEmpty(lst);
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
        boolean isSetEmpty = ((null == set) || set.isEmpty());
        if (isSetEmpty)
        {
            return true;
        }
        return false;
    }
    
    public static boolean isNotEmpty(Set<?> set)
    {
        return !isEmpty(set);
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
     * 检查字符串是否为双精度数
     * 
     * @param str
     *            指定的待检测字符串
     * @return 是双精度数返回true，否则返回false
     * @see [类、类#方法、类#成员]
     */
    public static boolean isDouble(String str)
    {
        if (isEmpty(str))
        {
            return false;
        }

        try
        {
            Double.parseDouble(str.trim());
        }
        catch (NumberFormatException ex)  {
            return false;
        }
        return true;
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

        return sbTmp.toString();
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
     * 获取指定名称资源的输入流     * 
     * @param name
     *            指定的资源名称
     * @param clz
     *            指定的class
     * @return [参数说明]     * 
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
            } catch (Throwable t)
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

    public static void main(String[] args) throws IOException
    {
        List<Long> testLst = new ArrayList<Long>();
        testLst.add(Long.valueOf("1000"));
        testLst.add(Long.valueOf("99"));
        testLst.add(Long.valueOf("999"));
        testLst.add(Long.valueOf("1"));
    }

}
