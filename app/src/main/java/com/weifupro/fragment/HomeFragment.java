package com.weifupro.fragment;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.weifupro.R;
import com.weifupro.bean.AnnImageResult;
import com.weifupro.bean.AnnImages;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.Constant;
import com.weifupro.utils.GetJsonDatas;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import okhttp3.Request;

/**
 * Created by HuangJ on 2017/7/16.17:44
 */

public class HomeFragment extends BaseFragment {
    private ViewPager mHomeFragmentviewPager;
    private LinearLayout mLinearLayout;
    private int count = 0;//轮播图当前下标
    private View view;//根布局
    public final int GetImags = 1014;//获取广告图返回码
    private final int AnnFaild = 1011;//获取广告图失败返回码
    private Timer mTimer;//计时器
    

    @Override
    public int getContentId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void init(View view) {
        super.init(view);
        mHomeFragmentviewPager = (ViewPager) view.findViewById(R.id.fragment_img_viewpager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_point_subscript);
    }

    /**
     *下载数据
     */
    @Override
    protected void loadDatas() {
        super.loadDatas();
        OkHttpManager.getInstance().getNet(Constant.Announcement, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {
                // 下载失败; 则去数据库里面获取.再更新
                List<AnnImages> img_List = DataSupport.findAll(AnnImages.class);
                if (img_List != null){
                    updateBanner(img_List);
                }
            }

            @Override
            public void onSuccess(String response) {
                // 下载成功
                String annDatas = response.toString();
                Log.d("print", "onSuccess: 首页信息" +annDatas);
                AnnImageResult annInfoData = GetJsonDatas.getAnnInfoData(annDatas);
                Log.d("print", "onSuccess:顶部图片的信息 " +annInfoData.getBody().get(0).getImgUrl());
                // 顶部ImgList;
                List<AnnImages> img_List = annInfoData.getBody();
                if (img_List == null){
                    img_List = new ArrayList<AnnImages>();
                }
                // 更新轮播图
                updateBanner(img_List);
                // 保存到数据库;更新缓存
                if (img_List.size() > 0){
                    // 删除缓存
                    DataSupport.deleteAll(AnnImages.class);
                    // 更新新的数据
                    DataSupport.saveAll(img_List);
                }

            }

            /**
             * 根据公告图片地址动态更新界面
             */
            private void updateBanner(List<AnnImages> img_List) {
                Log.d("print", "updateBanner: 更新界面");
            }
        });
    }
}
