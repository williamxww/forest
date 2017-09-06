package common.java.serialization.mem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author wwxiang
 * @since 2017/5/18.
 */
public class TestString {

	private static final String data = "12345";

	public static void main(String[] args) throws IOException {
		List<String> list = new ArrayList();
		Random random = new Random(2);

		while (true) {
			System.out.println("list.size " + list.size());
			System.in.read();

			for (int i = 0; i < 100000; i++) {
				int a = random.nextInt(9);
				list.add("987654321" + a);
			}
		}
	}
}
