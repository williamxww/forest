package com.bow.forest.frontend.common.ratelimit;

import org.springframework.mock.web.MockHttpServletRequest;


/**
 * @author vv
 * @since 2018/1/18.
 */
public class RateLimiterDemo {


    public static void main(String[] args) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/user/addUser");
        ConcurrentLimit limit = new ConcurrentLimit();
        limit.handle(request);
    }


}
