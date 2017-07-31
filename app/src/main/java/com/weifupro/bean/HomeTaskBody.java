package com.weifupro.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HuangJ on 2017/7/31.23:00
 */

public class HomeTaskBody extends DataSupport {
    private String title;
    private String detail;
    private String publishdate;
    private String executedate;
    private int state;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }

    public String getExecutedate() {
        return executedate;
    }

    public void setExecutedate(String executedate) {
        this.executedate = executedate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
