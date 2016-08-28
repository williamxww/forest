package com.bow.forest.frontend.common.servlet.snsserver;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 门户引擎消息跟踪助手 <功能详细描述>
 * 
 * @author j65521
 * @version [版本号, 2010-11-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class MessageTraceHelper
{
    public static final String HEADER_NEEDTRACE = "x-messagetrace";

    public static final String HEADER_TRACERESULT = "x-traceresult";

    public static final String TRACERESULT_SPILT = ";";

    // 单例
    private static MessageTraceHelper instance = new MessageTraceHelper();

    private ThreadLocal<List<String>> tl = new ThreadLocal<List<String>>();

    /**
     * 私有化构造方法
     */
    MessageTraceHelper()
    {
    }

    /**
     * 获取单例对象
     */
    public static MessageTraceHelper getInstance()
    {
        return instance;
    }

    public static boolean needTrace(HttpServletRequest request)
    {
        if (null != request)
        {
            String trace = request.getHeader(HEADER_NEEDTRACE);
            if (StringUtils.isNotEmpty(trace))
            {
                return true;
            }
        }
        return false;
    }

    public static String buildTraceInfo(String namingSQL, long begin, long end)
    {
        StringBuilder sb = new StringBuilder(20);
        if (null != namingSQL)
        {
            sb.append(namingSQL);
            sb.append("(");
            sb.append(begin);
            sb.append("-");
            sb.append(end);
            sb.append(")");
        }

        return sb.toString();
    }

    public static void sendTraceResultResponse(HttpServletResponse response, String traceResult)
    {
        if (null != response && StringUtils.isNotEmpty(traceResult))
        {
            response.addHeader(HEADER_TRACERESULT, traceResult);
        }
    }

    public void startTrace()
    {
        tl.set(new ArrayList<String>());
    }

    public void stopTrace()
    {
        List<String> list = tl.get();
        if (null != list)
        {
            list.clear();
        }

        tl.remove();
    }

    public boolean isTraced()
    {
        return (null != tl.get());
    }

    public List<String> getTraceInfoList()
    {
        return tl.get();
    }

    public String getTraceStrings()
    {
        StringBuilder sb = new StringBuilder(128);
        List<String> list = getTraceInfoList();
        if (null != list)
        {
            for (int i = 0; i < list.size(); i++)
            {
                sb.append(list.get(i));
                sb.append(TRACERESULT_SPILT);
            }
        }

        return sb.toString();
    }

    public void addTraceInfo(String info)
    {
        List<String> list = tl.get();
        if (null != list)
        {
            list.add(info);
        }
    }
}
