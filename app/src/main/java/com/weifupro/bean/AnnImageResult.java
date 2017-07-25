package com.weifupro.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by HuangJ on 2017/7/23.22:27
 */

public class AnnImageResult extends DataSupport {


    /**
     * code : 0
     * msg : 获取公告成功
     * body : [{"id":4,"imgUrl":"/visitshop//img/ann/ann1.jpg"},{"id":3,"imgUrl":"/visitshop//img/ann/ann1.jpg"},{"id":2,"imgUrl":"/visitshop//img/ann/ann1.jpg"},{"id":1,"imgUrl":"/visitshop/img/ann/ann1.jpg"}]
     */

    private int code;
    private String msg;
    private List<BodyBean> body;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean extends DataSupport {
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
}
