package com.weifupro.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.weifupro.R;
import com.weifupro.bean.AnnImageResult;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.Constant;
import com.weifupro.utils.GetJsonDatas;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

/**
 * Created by HuangJ on 2017/7/16.17:44
 */

public class HomeFragment extends BaseFragment {
    private ViewPager mHomeFragmentviewPager;
    private LinearLayout mLinearLayout;
    private int count = 0;//轮播图当前下标
    private List<View> views;
    public final int GetImags = 1014;//获取广告图返回码
    private final int AnnFaild = 1011;//获取广告图失败返回码
    private Timer mTimer;//计时器
    private HMViewpage hmViewpageAdapter;

    @Override
    public int getContentId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void init(View view) {
        super.init(view);
        mHomeFragmentviewPager = (ViewPager) view.findViewById(R.id.fragment_img_viewpager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_point_subscript);
        if (views == null){
            views = new ArrayList<View>();
        }
        hmViewpageAdapter = new HMViewpage();
        mHomeFragmentviewPager.setAdapter(hmViewpageAdapter);//轮播图的适配器;
        //添加界面滚动监听
        mHomeFragmentviewPager.addOnPageChangeListener(hmViewpageAdapter);
    }

    /**
     *下载轮播图的数据
     */
    @Override
    protected void loadDatas() {
        super.loadDatas();
        OkHttpManager.getInstance().getNet(Constant.Announcement, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {
                // 下载失败; 则去数据库里面获取.再更新
                List<AnnImageResult.BodyBean> img_List = DataSupport.findAll(AnnImageResult.BodyBean.class);
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
                List<AnnImageResult.BodyBean> img_List = annInfoData.getBody();
                if (img_List == null){
                    img_List = new ArrayList<AnnImageResult.BodyBean>();
                    Log.d("print", "onSuccess: 图片的URL"+img_List.get(0).getImgUrl());
                }
                // 更新轮播图
                updateBanner(img_List);
                // 保存到数据库;更新缓存
                if (img_List.size() > 0){
                    // 删除数据库缓存
//                    DataSupport.deleteAll(AnnImageResult.BodyBean.class);
                    // 更新新的数据
//                    DataSupport.saveAll(annInfoData.getBody());
                }
            }
        });
    }
    /**
     * 根据公告图片地址动态更新界面
     */
    public void updateBanner(List<AnnImageResult.BodyBean> img_List) {
        Log.d("print", "updateBanner: 更新界面");
        views.clear();//清除List中的view的缓存
        for (AnnImageResult.BodyBean images : img_List) {
            ImageView img = new ImageView(getActivity());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getActivity())
                    .load(Constant.BaseUrl + images.getImgUrl())
                    .into(img);
            views.add(img);
            Log.d("print", "updateBanner: views的数量"+views.size());
        }
        //更新界面显示
        hmViewpageAdapter.notifyDataSetChanged();
        //显示图片的下标
        initLayout();
        //开启计时器
        if (mTimer == null){
            mTimer = new Timer();
            mTimer.schedule(task,0,3000);
        }
    }

    /**
     *
     */
    private void initLayout() {
        mLinearLayout.removeAllViews();//移除所有指示下标布局;
        for ( int i = 0; i < views.size(); i++) {
            ImageView img = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,0,5,0);
            img.setLayoutParams(layoutParams);
            img.setImageResource(R.drawable.sns_v2_page_point);
            final int l = i;
            //下面的图片监听;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHomeFragmentviewPager.setCurrentItem(l);
                }
            });
            if (i == 0){
                img.setSelected(true);
            }
            mLinearLayout.addView(img);
        }
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.Scroll:
                    mHomeFragmentviewPager.setCurrentItem(count);
                    break;
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (count == views.size()){
                count = 0;
            } else{
              count = count + 1;
            }
            mHandler.sendEmptyMessage(Constant.Scroll);
        }
    };

    private class HMViewpage extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));//移除图片
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        /**
         * 滑动监听  动态更改指示下标的选中状态
         */
        @Override
        public void onPageSelected(final int position) {
            Log.d("print", "onPageSelected: 滑动了图片" + position);
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                ImageView childAtImg = (ImageView) mLinearLayout.getChildAt(i);
                if (i == position){
                    childAtImg.setSelected(true);
                }else {
                    childAtImg.setSelected(false);
                }

            }
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
