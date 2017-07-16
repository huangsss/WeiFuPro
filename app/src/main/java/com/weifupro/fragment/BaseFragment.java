package com.weifupro.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weifupro.R;

/**
 * Created by HuangJ on 2017/7/16.16:24
 */

public abstract class BaseFragment extends Fragment{
    private Activity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentId(),container,false);
        return view;
    }

    /**
     * 该方法紧跟onCreateView执行
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        bindListener();
        loadDatas();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.activity_in,0);
    }
    //需要显示的布局；
    public abstract int getContentId();
    //初始化
    protected  void init(View view){

    }
    //监听的绑定；
    protected  void bindListener(){

    }
    //下载数据；
    protected void loadDatas() {

    }
}
