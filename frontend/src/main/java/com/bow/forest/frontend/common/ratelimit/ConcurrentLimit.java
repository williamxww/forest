package com.bow.forest.frontend.common.ratelimit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.bow.forest.frontend.common.ratelimit.web.WebLimitStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vv
 * @since 2018/1/19.
 */
public class ConcurrentLimit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentLimit.class);

    private Map<String, RateLimiter> limiterMap = new ConcurrentHashMap<>();

    private LimitStrategy strategy = null;

    public ConcurrentLimit() {
        this.strategy = new WebLimitStrategy();
    }

    /**
     * 当调用此接口的tps超过设定值时，此方法立即返回false
     * 
     * @param request 业务请求
     * @return {@code false} 表示被限流
     */
    public boolean handle(HttpServletRequest request) {
        RateLimiter limiter = getOrAddLimiter(request);
        if (limiter == null) {
            return true;
        }
        return limiter.tryAcquire();
    }

    private RateLimiter getOrAddLimiter(HttpServletRequest request) {
        LimitMetaData meta = strategy.getLimitMeta(request);
        if (meta == null) {
            return null;
        }
        String key = meta.getLimitName();
        RateLimiter limiter = limiterMap.get(key);
        if (limiter == null) {
            synchronized (this) {
                limiter = limiterMap.get(key);
                if (limiter == null) {
                    limiter = RateLimiter.create(meta.getTps());
                    limiterMap.put(key, limiter);
                }
            }
        }
        return limiter;
    }

}
