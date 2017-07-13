package com.weifupro.net;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by "huangsays"  on 2017/7/13.14:36"huangays@gmail.com"
 * 网络请求管理，对OkHttp请求进行封装
 */

public class OkHttpManager {
    private static OkHttpManager instance;
    private OkHttpClient mOkHttpClient;
    private Handler okHandler;

    private  OkHttpManager() {
        //声明Handler对象，指定为主线程Looper,确保执行方法在主线程之中。
        okHandler = new Handler(Looper.getMainLooper());
        //指定超时时间
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        mOkHttpClient = builder.build();
    }

    /**
     * 获取当前实例对象
     **/
    public static OkHttpManager getInstance() {
        if (instance == null) {
            Log.d("print", "getInstance: ssssssss");
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    Log.d("print", "getInstance: ssss22222");
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    /**
     * get请求
     */
    public void getNet(String url, ResultCallback resultCallback) {
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)//get可不设置
                .build();
        dealNet(request, resultCallback);
    }

    /**
     *
     * @param url
     * @param resultCallback
     * @param param 参数不定
     */
    public void postNet(String url, ResultCallback resultCallback, Param... param) {
        if (param == null) {
            param = new Param[0];
        }
        FormBody.Builder formBody = new FormBody.Builder();
        for (Param P : param) {
            formBody.add(P.key, P.value);
        }
        RequestBody requestBody = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        dealNet(request,resultCallback);
    }

    /**
     * 发送网络请求
     */
    private void dealNet(final Request request, final ResultCallback resultCallback) {
        if (mOkHttpClient==null){
            Log.d("print", "dealNet: mOkHttpClient == null");
            return;
        }
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                resultCallback.onFaild(request, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "";
                try {
                    str = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final String finalStr = str;
                Log.d("print", "onResponse: " + finalStr);
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //成功请求数据;
                        resultCallback.onSuccess(finalStr);
                    }
                });
            }
        });
    }

    public static abstract class ResultCallback {
        public abstract void onFaild(Request request, IOException e);

        public abstract void onSuccess(String response);
    }

    public static class Param {
        String key;
        String value;

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }
}
