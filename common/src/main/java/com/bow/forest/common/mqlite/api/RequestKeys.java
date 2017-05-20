package com.bow.forest.common.mqlite.api;

/**
 * @author vv
 * @since 2017/5/20.
 */
public enum RequestKeys {
    PRODUCE, // 0
    FETCH, // 1
    MULTIFETCH, // 2
    MULTIPRODUCE, // 3
    OFFSETS, // 4
    CREATE, // 5
    DELETE;// 6

    public int value = ordinal();

    final static int size = values().length;

    public static RequestKeys valueOf(int ordinal) {
        if (ordinal < 0 || ordinal >= size) {
            return null;
        }
        return values()[ordinal];
    }
}
