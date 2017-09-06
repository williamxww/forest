package common.java.serialization.mem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 对象头16 Byte 对象内一个引用4 Byte
 * @author wwxiang
 * @since 2017/5/18.
 */
public class TestObject {

    private String f1;
    private String f2;
    private String f3;
    private String f4;
    private String f5;
    private Integer f6;
    private Integer f7;
    private Integer f8;
    private Integer f9;
    private Integer f0;



	public static void main(String[] args) throws IOException {
		List<Object> list = new ArrayList();
		Random random = new Random(2);

		while (true) {
			System.out.println("list.size " + list.size());
			System.in.read();

			for (int i = 0; i < 100000; i++) {
				list.add(new TestObject());
			}
		}
	}
}
