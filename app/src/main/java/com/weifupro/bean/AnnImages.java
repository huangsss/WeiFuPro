package com.weifupro.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HuangJ on 2017/7/23.23:44
 */

public class AnnImages extends DataSupport{
    private String id;
    private String ImgUrl;

    @Override
    public String toString() {
        return "AnnImages{" +
                "id='" + id + '\'' +
                ", ImgUrl='" + ImgUrl + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
