package com.weifupro.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by HuangJ on 2017/7/30.22:14
 */

public class Infomation extends DataSupport {

    /**
     * code : 0
     * msg : 资讯信息获取成功
     * body : [{"title":"华为联想全球化启示：如何在海外构建中国品牌","summary":"腾讯科技","imgurl":"http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png","detail":"http://tech.qq.com/a/20151123/008196.htm"},{"title":"联想取消中高端手机品牌VIBE","summary":"腾讯科技","imgurl":"http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png","detail":"http://tech.qq.com/a/20151123/018308.htm"},{"title":"联想计划明年在印度生产1000万部手机","summary":"腾讯科技","imgurl":"http://img1.gtimg.com/tech/pics/hv1/101/186/1974/128406881.jpg","detail":"http://tech.qq.com/a/20151126/043557.htm"},{"title":"联想签约高通：中国手机产业躲不过专利费","summary":"腾讯科技","imgurl":"http://img1.gtimg.com/tech/pics/hv1/165/235/2024/131670690.jpg","detail":"http://tech.qq.com/a/20160224/030848.htm"},{"title":"联想：非洲是下个最大手机市场 超印度和中国","summary":"腾讯科技","imgurl":"http://img1.gtimg.com/tech/pics/hv1/224/96/2026/131765354.jpg","detail":"http://tech.qq.com/a/20160226/044907.htm"},{"title":"众创空间WeWork融资4.3亿美元 联想控股领投","summary":"腾讯科技","imgurl":"http://img1.gtimg.com/tech/pics/hv1/223/117/2033/132225883.jpg","detail":"http://tech.qq.com/a/20160310/025118.htm"},{"title":"陈旭东公开信：联想将在国内扭转智能手机业务","summary":"腾讯科技","imgurl":"http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png","detail":"http://tech.qq.com/a/20160318/043508.htm"},{"title":"小米华为联想魅族推出的千元机，都不是自己设计的","summary":"网易新闻","imgurl":"http://inews.gtimg.com/newsapp_ls/0/305511207_300240/0","detail":"http://tech.qq.com/a/20160518/076472.htm"},{"title":"联想发布模块化手机Moto Z 投影、摄影、背壳能自选","summary":"腾讯科技","imgurl":"http://inews.gtimg.com/newsapp_ls/0/555495485_300240/0","detail":"http://tech.qq.com/a/20160906/038980.htm"},{"title":"联想的AR手机延期上市，智能手机找点\u201c创新\u201d真不容易","summary":"腾讯科技","imgurl":"http://inews.gtimg.com/newsapp_ls/0/580125438_300240/0","detail":"http://tech.qq.com/a/20160914/009726.htm"}]
     */

    private int code;
    private String msg;
    /**
     * title : 华为联想全球化启示：如何在海外构建中国品牌
     * summary : 腾讯科技
     * imgurl : http://mat1.gtimg.com/tech/00Jamesdu/2014/index/remark/2.png
     * detail : http://tech.qq.com/a/20151123/008196.htm
     */

    private List<InfomationBody> body;

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

    public List<InfomationBody> getBody() {
        return body;
    }

    public void setBody(List<InfomationBody> body) {
        this.body = body;
    }
}
