package common.java.concurrent.ratelimit;

/**
 * @author vv
 * @since 2018/1/18.
 */
public class RateLimiterDemo {


    public static void main(String[] args) throws Exception {
        ConcurrentLimit limit = new ConcurrentLimit();
        limit.handle("com.bow.HelloService","say",null);
    }


}
