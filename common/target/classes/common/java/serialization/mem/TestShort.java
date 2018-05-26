package common.java.serialization.mem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 1 Integer(int) in memory take 16 Byte
 * @author wwxiang
 * @since 2017/5/18.
 */
public class TestShort {

    public static void main(String[] args) throws IOException {
        List<Short> list = new ArrayList();
        Random random = new Random(2);

        while (true) {
            System.out.println("list.size " + list.size());
            System.in.read();

            for (int i = 0; i < 100000; i++) {
                Short a = (short)random.nextInt();
                list.add(a);
            }
        }
    }
}
