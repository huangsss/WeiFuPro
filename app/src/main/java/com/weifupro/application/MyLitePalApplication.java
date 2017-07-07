package com.weifupro.application;

import org.litepal.LitePalApplication;

/**
 * Created by "huangsays"  on 2017/7/7.19:14"huangays@gmail.com"
 */

public class MyLitePalApplication extends LitePalApplication {

    private MyLitePalApplication myLitePalApplication;

    public MyLitePalApplication initMyLiteApplication() {
        myLitePalApplication = new MyLitePalApplication();
        return myLitePalApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
