package common.java.demo;

import org.junit.Test;

/**
 * String 常量池
 *
 * @author acer
 * @version C10 2016年4月24日
 * @since SDP V300R003C10
 */
public class StringCount
{
    @Test
    public void count1()
    {
        String s1 = "xyz";
        String s2 = new String("xyz");
        System.out.println(s1 == s2);// false
    }
    
    @Test
    public void count2()
    {
        String s1 = "a" + "b";
        String s2 = "ab";
        System.out.println(s1 == s2);// true
    }
    
    @Test
    public void count3()
    {
        String s1 = "aa";
        String s2 = s1.toString(); // 实际就是将对象的引用返回了
        System.out.println(s1 == s2); // true
    }
    
    @Test
    public void count4()
    {
        StringBuffer sb = new StringBuffer("aa");
        String s1 = sb.toString();
        String s2 = "aa";
        System.out.println(s1 == s2); // false
    }

}
