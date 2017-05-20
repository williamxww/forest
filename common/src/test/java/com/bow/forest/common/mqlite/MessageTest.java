package com.bow.forest.common.mqlite;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author vv
 * @since 2017/5/20.
 */
public class MessageTest {

    @Test
    public void demo(){
        Message msg = new Message("vv".getBytes());
        System.out.println(msg);
    }

}