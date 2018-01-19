package common.java.concurrent.ratelimit;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author vv
 * @since 2018/1/19.
 */
public class ConcurrentLimit {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentLimit.class);

    private Map<String, RateLimiter> limiterMap = new ConcurrentHashMap();

    private XmlParser parser = null;

    public ConcurrentLimit() {
        this.parser = new XmlParser();
    }

    /**
     * 当调用此接口的tps超过设定值时，此方法立即返回false
     * 
     * @param interfaceName 接口名
     * @param methodName 方法名
     * @param paramValue 入参值
     * @return {@code false} 表示被限流
     */
    public boolean handle(String interfaceName, String methodName, List<?> paramValue) {
        RateLimiter limiter = getOrAddLimiter(interfaceName, methodName, paramValue);
        if (limiter == null) {
            return true;
        }
        return limiter.tryAcquire();
    }

    private RateLimiter getOrAddLimiter(String interfaceName, String methodName, List<?> paramValue) {
        // 找出满足当前方法的一个限流策略
        Limit limit = parser.getLimit(interfaceName, methodName);
        if (limit == null) {
            return null;
        }
        // 找出此次调用的限流器
        String key = Limit.getKey(interfaceName, methodName);

        List<Integer> indexList = limit.getParamIndexList();
        if (indexList.size() != 0) {
            // 限流策略中指定了根据参数限流
            for (Integer index : indexList) {
                if (paramValue == null || paramValue.get(index) == null) {
                    // 出现此情况说明限流策略不满足当前请求，不限流
                    return null;
                }
                key += "-" + paramValue.get(index);
            }
        }

        RateLimiter limiter = limiterMap.get(key);
        if (limiter == null) {
            synchronized (this) {
                limiter = limiterMap.get(key);
                if (limiter == null) {
                    limiter = RateLimiter.create(limit.getValue());
                    limiterMap.put(key, limiter);
                }
            }
        }
        return limiter;
    }
}
