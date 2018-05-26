package common.java.serviceloader;

/**
 * Created by vv on 2016/8/6.
 */
public class ChineseHelloServiceImpl implements IHelloService {
    @Override
    public void sayHello() {
        System.out.println("你好");
    }
}
