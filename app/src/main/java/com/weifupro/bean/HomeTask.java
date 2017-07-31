package com.weifupro.bean;

import java.util.List;

/**
 * Created by HuangJ on 2017/7/31.22:54
 */

public class HomeTask {

    /**
     * code : 0
     * msg : 任务信息获取成功
     * body : [{"title":"新产品Y008调研","detail":"针对公司新产品Y008的市场调研。需要来店里咨询的客户填写问卷，并留联系方式。问卷已发送至各位邮箱，请下载并打印。","publishdate":"2016-08-15","executedate":"2016-09-30","state":0},{"title":"用户反馈统计","detail":"需要来店里咨询的用户统计，统计用户相关信息，信息表需要到公司网站下载。","publishdate":"2016-07-15","executedate":"2016-10-11","state":0},{"title":"关于意见反馈","detail":"现在开通提意见、赢大奖活动，可以提出自己发现目前存在的问题，或者有其他的建议，一经采纳给予奖励。意见通道可以通过APP意见反馈提交","publishdate":"2016-07-11","executedate":"2016-08-11","state":0},{"title":"优秀员工评选","detail":"评选优秀员工，需要个员工登录公司网站进行投票，没有投票的视为弃权。","publishdate":"2016-05-10","executedate":"2016-06-01","state":0},{"title":"回收旧产品，以旧换新","detail":"公司现在退出以旧换新活动，相关说明查看公司网站活动模块。","publishdate":"2016-03-15","executedate":"2016-07-10","state":1},{"title":"新产品XS03型号宣传推广","detail":"新产品XS03型号的宣传，每个店面需要放置相关产品在主要位置，店面门口需要有推介海报。展示产品需要有专人负责讲解，会进行不定期抽查。","publishdate":"2016-03-01","executedate":"2016-06-01","state":1},{"title":"年度总结","detail":"各店面对去年一年工作总结，包括销售业绩、发现的问题、解决方式，经理需要对每个员工做出考核评价","publishdate":"2016-01-05","executedate":"2016-01-25","state":1}]
     */

    private int code;
    private String msg;
    /**
     * title : 新产品Y008调研
     * detail : 针对公司新产品Y008的市场调研。需要来店里咨询的客户填写问卷，并留联系方式。问卷已发送至各位邮箱，请下载并打印。
     * publishdate : 2016-08-15
     * executedate : 2016-09-30
     * state : 0
     */

    private List<HomeTaskBody> body;

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

    public List<HomeTaskBody> getTaskBody() {
        return body;
    }

    public void setTaskBody(List<HomeTaskBody> body) {
        this.body = body;
    }

}
