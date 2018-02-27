package common.java.serialization.hessian;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianOutput;

/**
 *
 * @see Data
 * @author vv
 * @since 2018/2/27.
 */
public class DataTest {

    public static void main(String[] args) throws Exception {
        DataTest demo = new DataTest();
        demo.serialize();
    }

    public void serialize() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        File file = new File("hessian.txt");
//        FileOutputStream os = new FileOutputStream(file);
        HessianOutput ho = new HessianOutput(os);
        Data data = new Data();
        ho.writeObject(data);
        String result = toHexString(os.toByteArray());
        System.out.println(result);
    }

    private String toHexString(byte[] ary) {
        StringBuilder sb = new StringBuilder();
        for (byte a : ary) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            // 如果byte为8个1，则其值为-1，转为int时变为0xffffffff
            // 负数在转换时，高位全补1，此处过滤掉高位
            String sTemp = Integer.toHexString(0xff & a);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
