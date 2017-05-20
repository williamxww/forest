package common.java.serialization;

import java.io.Serializable;

/**
 * 字节码:<br/>
 * AC ED 00 05 73 72 00 13 73 65 72 69 61 6C 69 7A <br/>
 * 61 74 69 6F 6E 2E 44 61 74 61 31 FF FF FF FF FF <br/>
 * FF FF FF 02 00 01 42 00 02 66 31 78 70 08 <br/>
 *
 * 
 * AC ED: 序列化协议.<br/>
 * 00 05:序列化协议版本.<br/>
 * 73:声明这是一个新的对象.<br/>
 * 72:声明这里开始一个新Class<br/>
 *
 * 00 13: Class名字的长度共19字节.<br/>
 * 73 65 72 69 61 6C 69 7A 61 74 69 6F 6E 2E 44 61 74 61 31-->
 * serialization.Data1<br/>
 * FF FF FF FF FF FF FF FF -->8字节的serialVersionUID<br/>
 * 02: Various flags. 标记号.声明该对象支持序列化<br/>
 * 00 01 --> 此类的域个数<br/>
 * 42 --> 字段类型为byte<br/>
 * 00 02 -->字段名字长度2<br/>
 * 66 31 --> 名称f1<br/>
 * 78 TC_ENDBLOCKDATA 对象块结束的标志<br/>
 * 70 TC_NULL，没有超类了<br/>
 * 08 字段f1的值 0x08<br/>
 * 
 * @author vv
 * @since 2017/4/8.
 */
public class Data1 implements Serializable {
    private static final long serialVersionUID = -1L;

    public byte f1 = 0x8;
}
