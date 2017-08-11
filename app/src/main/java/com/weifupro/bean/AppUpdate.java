package com.weifupro.bean;

/**
 * Created by "huangsays"  on 2017/8/10.11:09"huangays@gmail.com"
 */

public class AppUpdate {


    /**
     * code : 0
     * msg : APP信息获取成功
     * body : {"version":"2","address":"/visitshop/img/app.apk","publishdate":"2016-07-28","updateinfo":"1:新增拜访模块;2:新增培训模块;3:性能优化，节省流量"}
     */

    private int code;
    private String msg;
    private BodyBean body;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * version : 2
         * address : /visitshop/img/app.apk
         * publishdate : 2016-07-28
         * updateinfo : 1:新增拜访模块;2:新增培训模块;3:性能优化，节省流量
         */

        private String version;
        private String address;
        private String publishdate;
        private String updateinfo;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPublishdate() {
            return publishdate;
        }

        public void setPublishdate(String publishdate) {
            this.publishdate = publishdate;
        }

        public String getUpdateinfo() {
            return updateinfo;
        }

        public void setUpdateinfo(String updateinfo) {
            this.updateinfo = updateinfo;
        }
    }
}
