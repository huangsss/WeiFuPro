package com.weifupro.fragment;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import com.weifupro.R;
import com.weifupro.bean.AppUpdate;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.Constant;
import com.weifupro.utils.GetJsonDatas;
import com.weifupro.utils.SetItemView;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by HuangJ on 2017/7/16.17:44
 */

public class MeFragment extends BaseFragment {

    private SetItemView mSetItemView;
    private int mCurrentVersionCode;//当前客户端版本号

    @Override
    public int getContentId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        //获取当前版本号;
        mCurrentVersionCode = getPackageInfo(getContext()).versionCode;
        mSetItemView = (SetItemView) view.findViewById(R.id.fragment_me_new);
        mSetItemView.setmOnSetItemOnClick(new SetItemView.onSetItemOnClick() {
            @Override
            public void onClick() {
                // 检测版本;
                checkVersion();
            }
        });

    }

    /**
     * 新版本检测;
     */
    private void checkVersion() {
        //通过url获得服务器版本的Version对比当前版本的Version;
        OkHttpManager.getInstance().getNet(Constant.AppUpdate, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {
                Toast.makeText(getActivity(), "网络错误,请重试...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                AppUpdate appInfo = GetJsonDatas.getAppInfo(response);
                if (appInfo.getCode() == 0) {
                    if (mCurrentVersionCode < Integer.parseInt(appInfo.getBody().getVersion())) {
                        // 则需要更新版本;

                    }else{
                        Toast.makeText(getActivity(), "已经是最新版本,无需更新...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    /**
     * @return 返回包信息;
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }

}
