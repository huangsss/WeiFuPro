package com.weifupro.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by HuangJ on 2017/7/23.23:44
 */

public class AnnImages extends DataSupport{
    /**
     * id : 4
     * imgUrl : /visitshop//img/ann/ann1.jpg
     */

    private int id;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
