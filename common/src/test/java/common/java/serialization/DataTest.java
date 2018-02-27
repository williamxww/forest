package common.java.serialization;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * @author vv
 * @since 2017/4/8.
 */
public class DataTest {

    /**
     * 参考{@link Data1}分析序列化后的字节码<br/>
     *
     * 将对象实例相关的类元数据输出。<br/>
     * 递归地输出类的超类描述直到不再有超类。<br/>
     * 类元数据完了以后，开始从最顶层的超类开始输出对象实例的实际数据值。<br/>
     * 
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        File file = new File("temp.txt");
        FileOutputStream fos = new FileOutputStream(file);
        Data1 data1 = new Data1();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(data1);
        oos.flush();
        fos.close();

        // System.out.println(toHexString(bos.toByteArray()));
    }

}
