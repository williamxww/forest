package com.bow.forest.hsqldb;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.map.BitMap;
import org.junit.Test;

import java.util.BitSet;

/**
 * {@link java.util.BitSet} 需要存储[3,5,6,9]最大值为9 <br/>
 * 定义长度为10的bit 数组,用1所在的下标表示数值<br/>
 * [0,0,0,1,0,1,0,0,0,1]
 * 
 * @author vv
 * @since 2017/5/26.
 */
public class BitMapTest {

    /**
     * 定义一个int[] ary,用数组的下标代表值，如对电话号码出现次数进行统计<br/>
     * 当15872509160出现 就让 ary[15872509160]的值+1
     */
    @Test
    public void bitSetTest() {
        BitSet bitSet = new BitSet();
        bitSet.set(8);// 第8个bit 改为true
        System.out.println(bitSet.size());// 64
        System.out.println(bitSet.get(8));// true
    }

    /**
     * BitMap 位图<br/>
     * 通过set指定第几个bit是1<br/>
     * 当指定的位数过大如指定第33位是1，则位图内部会用2个int来展示整个bit
     */
    @Test
    public void bitMapTest() {
        BitMap bitMap = new BitMap(10, true);
        // bitMap.setValue(3, true);// 将第3个bit改为1
        // bitMap.set(3);// 将第3个bit改为1 同上
        // System.out.println(bitMap.get(3));// 返回第三个bit是0还是1
        // bitMap.setRange(0, 4);// 最开始的4个bit改为1

        bitMap.set(33);// [0,40000000] 一个int只有32位，因此此处用了2个Int来表示
        printInts(bitMap.getIntArray());
    }

    /**
     * byte[] 中各个byte 取反
     */
    @Test
    public void not() {
        byte[] map = new byte[] { (byte) 0x0F, 0 };
        byte[] result = BitMap.not(map);
        printBytes(result); // [f0,ff]
    }

    @Test
    public void and() {
        byte[] a = new byte[] { 0x0F };
        byte[] b = new byte[] { 0x70 };
        byte[] result = BitMap.and(a, b);
        printBytes(result); // [00]

        byte[] c = new byte[] { 0x0F };
        result = BitMap.and(a, c);
        printBytes(result); // [0f]
    }

    @Test
    public void or() {
        byte[] a = new byte[] { 0x0F };
        byte[] b = new byte[] { 0x70 };
        byte[] result = BitMap.or(a, b);
        printBytes(result); // [7f]

        // 异或 0 xor 1 -> 1 , 1 xor 1 -> 0
        byte[] c = new byte[] { 0x0F };
        result = BitMap.xor(a, c);
        printBytes(result); // [00]
    }

    /**
     * byte[] 数组中每个byte左移指定位数
     */
    @Test
    public void leftShift() {
        byte[] map = new byte[] { 0x00, 0x01, 0x02 };
        byte[] result = BitMap.leftShift(map, 1);
        printBytes(result); // [00,02,04]
    }

    /**
     * 指定int 中没有被设置为1的 bit 个数
     */
    @Test
    public void countUnsetBitsStart() {
        int map = 0x000000FF;
        int result = BitMap.countUnsetBitsStart(map);
        System.out.println(result);// 24
        result = BitMap.countSetBitsEnd(map);
        System.out.println(result); // 8
    }

    /**
     * 将ary 以16进制打印
     * 
     * @param ary byte array
     */
    public void printBytes(byte[] ary) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (byte a : ary) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            // 0xFF在转为Int时会变为 0xFFFFFFFF
            int unsignedInt = a & 0xFF;
            String hex = Integer.toHexString(unsignedInt);
            // 0xA 转为 0x0A
            if (hex.length() < 2) {
                sb.append("0");
            }
            sb.append(hex);
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    public void printInts(int[] ary) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int a : ary) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            String hex = Integer.toHexString(a);
            if (hex.length() < 8) {
                StringUtils.leftPad(hex, 8, "0");
            }

            sb.append(hex);
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

}
