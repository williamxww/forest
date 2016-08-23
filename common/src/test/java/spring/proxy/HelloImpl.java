package spring.proxy;

/**
 * Created by vv on 2016/8/14.
 */
public class HelloImpl implements IHello {
    @Override
    public void sayHello() {
        System.out.println("hello world");
    }
}
