package com.weifupro.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HuangJ on 2017/7/30.22:16
 */

public class InfomationBody extends DataSupport {


    private String title;
    private String summary;
    private String imgurl;
    private String detail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
