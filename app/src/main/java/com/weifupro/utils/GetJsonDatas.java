package com.weifupro.utils;

import com.google.gson.Gson;
import com.weifupro.bean.LoginBeanResult;

/**
 * Created by "huangsays"  on 2017/7/13.17:05"huangays@gmail.com"
 * 作用；解析Json
 */

public class GetJsonDatas {

    /**
     *
     * @param json  登录信息
     * @return 解析登录信息
     */
    public static LoginBeanResult getLoginInfoData(String json){
    Gson gson = new Gson();
    return gson.fromJson(json,LoginBeanResult.class);
}

}
