package common.java.serviceloader;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Created by vv on 2016/8/6.
 * 新建文件META-INF/services/common.java.serviceloader.IHelloService
 * 文件内容：common.java.serviceloader.EnglishHelloServiceImpl 该接口的实现
 * 以下代码就可以找到该接口的实现类进行调用
 */
public class HelloTest {
    public static void main(String[] args) {
        ServiceLoader<IHelloService> sl = ServiceLoader.load(IHelloService.class);
        Iterator<IHelloService> it = sl.iterator();
        while(it.hasNext()){
            IHelloService service = it.next();
            service.sayHello();
        }
    }
}
