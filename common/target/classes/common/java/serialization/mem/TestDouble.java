package common.java.serialization.mem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 1 Double in memory take 24 Byte
 * @author wwxiang
 * @since 2017/5/18.
 */
public class TestDouble {

    public static void main(String[] args) throws IOException {
        List<Double> list = new ArrayList();
        Random random = new Random(2);

        while (true) {
            System.out.println("list.size " + list.size());
            System.in.read();

            for (int i = 0; i < 100000; i++) {
                Double a = random.nextDouble();
                list.add(a);
            }
        }
    }
}
