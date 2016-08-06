/**  
 * @FileName: LoadOrder.java 
 * @Package environment 
 * all rights reserved by Hill team
 * @version v1.3  
 */ 
package common.java.demo;

/**
 * 结果：先加载 再初始化； 先父类再子类；  先在普通代码块中变量初始化，再构造函数中进行赋值
 * 
 * 父类静态代码块
 * 子类静态代码块
 * 
 * 父类普通代码块
 * 父类构造函数赋值
 * 子类普通代码块
 * 子类构造函数赋值
 */

/** 
 * @ClassName: LoadOrder 
 * @Description: TODO(describe in one sentence) 
 * @author ViVi 
 * @date 2015年5月24日 上午8:14:28  
 */

public class LoadOrder {
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        SubClass sub = new SubClass();
    }
}

class SuperClass {
    public String content;

    public static String staticContent;

    public static String initedStaticContent = "已初始化了的父类静态变量";

    {
        System.out.println("----------父类普通代码块--START-----------");
        System.out.println("content:" + this.content);
        System.out.println("staticContent:" + staticContent);
        System.out.println("initedStaticContent:" + initedStaticContent);
        System.out.println("----------父类普通代码块--END-------------");
    }
    static {
        System.out.println("----------父类静态代码块--START-----------");
        System.out.println("staticContent:" + staticContent);
        System.out.println("initedStaticContent:" + initedStaticContent);
        System.out.println("----------父类静态代码块--END-----------");
    }

    public SuperClass() {
        this("父类");
        System.out.println("[CONSTRUCTOR]父类默认构造函数--END");
    }

    public SuperClass(String content) {
        System.out.println("[CONSTRUCTOR]父类自定义构造函数--START");
        this.content = content;
        staticContent = "父类静态变量";
        System.out.println("staticContent:" + staticContent);
        System.out.println("[CONSTRUCTOR]父类自定义构造函数--END");
    }
}

class SubClass extends SuperClass {
    public String content;

    public static String staticContent;

    public static String initedStaticContent = "已初始化了的子类静态变量";

    {
        System.out.println("----------子类普通代码块--START-----------");
        System.out.println("content:" + this.content);
        System.out.println("staticContent:" + staticContent);
        System.out.println("initedStaticContent:" + initedStaticContent);
        System.out.println("----------子类普通代码块--END-------------");

    }
    static {
        System.out.println("----------子类静态代码块--START------------");
        System.out.println("staticContent:" + staticContent);
        System.out.println("initedStaticContent:" + initedStaticContent);
        System.out.println("----------子类静态代码块--END------------");
    }

    public SubClass() {
        this("子类");
        System.out.println("[CONSTRUCTOR]子类默认构造函数--END");
    }

    public SubClass(String content) {
        super();
        System.out.println("[CONSTRUCTOR]子类自定义构造函数--START");
        this.content = content;
        staticContent = "子类静态变量";
        System.out.println("[CONSTRUCTOR]子类自定义构造函数--END");
    }
}