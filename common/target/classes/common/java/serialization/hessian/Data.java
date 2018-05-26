package common.java.serialization.hessian;

import java.io.Serializable;

/**
 *
 * 字节码：
 *
 * 4D,74,00,26,<br/>
 *
 * 63,6F,6D,6D,6F,6E,2E,6A,61,76,61,2E,73,65,72,69,61,6C,69,7A,61,74
 * ,69,6F,6E,2E,68,65,73,73,69,61,6E,2E,44,61,74,61, -->
 * common.java.serialization.hessian.Data<br/>
 *
 * 53,00,<br/>
 * 02, --> 属性名称f1的长度<br/>
 * 66,31, --> 字符串f1<br/>
 *
 * 49,--> f1的类型<br/>
 * 00,00,00,08, --> f1的值<br/>
 * 7A
 * 
 * @author vv
 * @since 2017/2/27.
 */
public class Data implements Serializable {
    // private static final long serialVersionUID = -1L;

    public byte f1 = 0x8;
}
