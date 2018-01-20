package com.bow.forest.frontend.common.ratelimit;

/**
 * 限流器的描述信息
 * 
 * @author vv
 * @since 2018/1/20.
 */
public class LimitMetaData {

    /**
     * 限流器的名字
     */
    private String limitName;

    /**
     * tps
     */
    private int tps;

    public String getLimitName() {
        return limitName;
    }

    public void setLimitName(String limitName) {
        this.limitName = limitName;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }
}
