package com.bow.forest.frontend.common.ratelimit.web;

import javax.servlet.http.HttpServletRequest;

import com.bow.forest.frontend.common.ratelimit.LimitMetaData;
import com.bow.forest.frontend.common.ratelimit.LimitStrategy;

/**
 * @author vv
 * @since 2018/1/20.
 */
public class WebLimitStrategy implements LimitStrategy {

    private WebXmlParser parser = new WebXmlParser();

    @Override
    public LimitMetaData getLimitMeta(HttpServletRequest request) {
        String url = request.getRequestURI();

        // 找出满足当前方法的一个限流策略
        WebLimitInfo limitInfo = parser.getLimit(url);
        if (limitInfo == null) {
            return null;
        }
        StringBuilder limitName = new StringBuilder(limitInfo.getUrl());
        String[] params = limitInfo.getParams();
        if (params != null && params.length > 0) {
            // 限流策略中指定了根据参数限流
            for (String param : params) {
                if (request == null || request.getParameter(param) == null) {
                    // 出现此情况说明限流策略不满足当前请求，不限流
                    return null;
                }
                limitName.append("-");
                limitName.append(request.getParameter(param));
            }
        }

        // 构造限流器的元信息
        LimitMetaData meta = new LimitMetaData();
        meta.setLimitName(limitName.toString());
        meta.setTps(limitInfo.getTps());
        return meta;
    }

}
