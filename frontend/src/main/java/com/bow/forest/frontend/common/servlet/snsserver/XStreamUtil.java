/*
 * 文 件 名:  XStreamUtil.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  吴飞00106856
 * 修改时间:  2009-10-27
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.bow.forest.frontend.common.servlet.snsserver;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author 吴飞00106856
 * @version [1.0, 2009-10-27]
 * @see [相关类/方法]
 * @since [iRead/Server1.0]
 */
public class XStreamUtil
{
    /**
     * XStream的一种生产方式，忽略消息中多余的字段，增加门户与server版本间的兼容性
     */
    public static XStream baseParseXStream()
    {
        return new XStream()
        {
            protected MapperWrapper wrapMapper(MapperWrapper next)
            {
                return new MapperWrapper(next)
                {
                    public boolean shouldSerializeMember(Class definedIn, String fieldName)
                    {
                        return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
                    }

                };
            }

        };
    }
}
