package common.java.serviceloader;

/**
 * Created by vv on 2016/8/6.
 */
public class EnglishHelloServiceImpl implements IHelloService {
    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}
