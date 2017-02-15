package com.bow.forest.demoice;

import Ice.Current;
import com.bow.forest.backend.serviceice._HelloWorldDisp;

/**
 * 业务实现类<br/>
 * {@link _HelloWorldDisp} 实现了
 * {@link com.bow.forest.backend.serviceice.HelloWorld}
 *
 *
 * @author vv
 * @since 2017/1/24.
 */
public class HelloWorldImpl extends _HelloWorldDisp {

    private static final long serialVersionUID = 1L;

    @Override
    public void say(String s, Current __current) {
        System.out.println("Hello World!" + " the string s is " + s);
    }
}
