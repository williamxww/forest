package com.bow.forest.frontend.common.ratelimit.web;

/**
 * @author vv
 * @since 2018/1/20.
 */
public class WebLimitInfo {

    private String url;

    private String[] params;

    private int tps;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public int getTps() {
        return tps;
    }

    public void setTps(int tps) {
        this.tps = tps;
    }
}
