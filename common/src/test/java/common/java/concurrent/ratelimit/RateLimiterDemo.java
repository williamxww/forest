package common.java.concurrent.ratelimit;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @author vv
 * @since 2018/1/18.
 */
public class RateLimiterDemo {

    public static void main(String[] args) {
        testWithRateLimiter();
    }


    public static void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(2); // 每秒不超过10个任务被提交
        for (int i = 0; i < 10; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private void parse(){

    }
}
