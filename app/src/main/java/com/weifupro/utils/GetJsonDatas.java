package com.weifupro.utils;

import com.google.gson.Gson;
import com.weifupro.bean.AnnImageResult;
import com.weifupro.bean.Infomation;
import com.weifupro.bean.LoginBeanResult;

/**
 * Created by "huangsays"  on 2017/7/13.17:05"huangays@gmail.com"
 * 作用；解析Json
 */

public class GetJsonDatas {
    private static Gson gson = new Gson();
    /**
     *
     * @param json  登录信息
     * @return 解析登录信息
     */
    public static LoginBeanResult getLoginInfoData(String json){

    return gson.fromJson(json,LoginBeanResult.class);
}

    /**
     *
     * @param json HomeFragment的顶部Banner信息;
     */
    public static AnnImageResult getAnnInfoData(String json){
        return gson.fromJson(json,AnnImageResult.class);
    }

    /**
     *
     * @param json 主页咨询
     * @return
     */
    public static Infomation getInfomation(String json){
        return  gson.fromJson(json,Infomation.class);
    }
}
