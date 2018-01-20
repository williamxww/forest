package com.bow.forest.frontend.common.ratelimit;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vv
 * @since 2018/1/20.
 */
public interface LimitStrategy {

    LimitMetaData getLimitMeta(HttpServletRequest request);

}
