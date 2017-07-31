package com.weifupro.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weifupro.R;
import com.weifupro.adapters.HomeTaskListAdapter;
import com.weifupro.adapters.InfomationListAdapter;
import com.weifupro.bean.AnnImageResult;
import com.weifupro.bean.AnnImages;
import com.weifupro.bean.HomeTask;
import com.weifupro.bean.HomeTaskBody;
import com.weifupro.bean.Infomation;
import com.weifupro.bean.InfomationBody;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.Constant;
import com.weifupro.utils.GetJsonDatas;
import com.weifupro.utils.SharePreUtil;

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
    private Timer mTimer;//计时器
    private HMViewpage hmViewpageAdapter;

    private LinearLayout mRl_http_failed;
    private RadioButton mFragment_sort_shop, mFragment_sort_visit, mFragment_sort_train;
    private RelativeLayout mHome_fragment_task, mProgressBar;
    private TextView mFragment_home_task_more, mFragment_home_info_more;
    private RecyclerView mFragment_home_task_list, mFragment_home_info_list;
    private String userId;
    private boolean isLogin;

    private InfomationListAdapter mInfomationListAdapter;
    private HomeTaskListAdapter mHomeTaskListAdapter;
    private int mShowSize = 3;

    @Override
    public int getContentId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void init(View view) {
        super.init(view);
        userId = SharePreUtil.GetShareString(getActivity(), "userid");
        if (TextUtils.isEmpty(userId)) {
            isLogin = false;
            Toast.makeText(getActivity(), R.string.please_login, Toast.LENGTH_SHORT).show();
        } else {
            isLogin = true;
        }
        if (views == null) {
            views = new ArrayList<View>();
        }
        mHomeFragmentviewPager = (ViewPager) view.findViewById(R.id.fragment_img_viewpager);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.fragment_point_subscript);
        hmViewpageAdapter = new HMViewpage();
        mHomeFragmentviewPager.setAdapter(hmViewpageAdapter);//轮播图的适配器;
        //添加界面滚动监听
        mHomeFragmentviewPager.addOnPageChangeListener(hmViewpageAdapter);
        mRl_http_failed = (LinearLayout) view.findViewById(R.id.rl_http_failed);
        mFragment_sort_shop = (RadioButton) view.findViewById(R.id.fragment_sort_shop);
        mFragment_sort_visit = (RadioButton) view.findViewById(R.id.fragment_sort_visit);
        mFragment_sort_train = (RadioButton) view.findViewById(R.id.fragment_sort_train);
        mHome_fragment_task = (RelativeLayout) view.findViewById(R.id.home_fragment_task);
        mFragment_home_task_more = (TextView) view.findViewById(R.id.fragment_home_task_more);
        mFragment_home_task_list = (RecyclerView) view.findViewById(R.id.fragment_home_task_list);
        mFragment_home_info_more = (TextView) view.findViewById(R.id.fragment_home_info_more);
        mFragment_home_info_list = (RecyclerView) view.findViewById(R.id.fragment_home_info_list);
        mProgressBar = (RelativeLayout) view.findViewById(R.id.home_progress);
        mProgressBar.setVisibility(View.VISIBLE);//设置Loding
        if (isLogin) {
            //登陆了则显示任务列表
            mHome_fragment_task.setVisibility(View.VISIBLE);
            mFragment_home_task_list.setVisibility(View.VISIBLE);
        }
        mFragment_home_task_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFragment_home_info_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFragment_home_info_list.setNestedScrollingEnabled(false);
        mFragment_home_task_list.setNestedScrollingEnabled(false);
    }

    /**
     * 下载轮播图的数据
     */
    @Override
    protected void loadDatas() {
        super.loadDatas();
        OkHttpManager.getInstance().getNet(Constant.Announcement, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {
                // 下载失败; 则去数据库里面获取.再更新
                try {
                    Log.d("print", "onFaild: 去数据库里面获取");
                    List<AnnImages> img_List = DataSupport.findAll(AnnImages.class);
                    if (img_List != null) {
                        updateBanner(img_List);
                    }
                } catch (Exception e1) {
                    Log.d("print", "onFaild: 数据库寻找AnnImageResult.BodyBean.class加载失败");
                    Log.d("print", "onFaild: 失败的理由" + e.getMessage());
                    e1.printStackTrace();
                }
            }

            @Override
            public void onSuccess(String response) {
                // 下载成功
                String annDatas = response.toString();
                Log.d("print", "onSuccess: 首页信息" + annDatas);
                AnnImageResult annInfoData = GetJsonDatas.getAnnInfoData(annDatas);
                Log.d("print", "onSuccess:顶部图片的信息 " + annInfoData.getBody().get(0).getImgUrl());
                // 顶部ImgList;
                List<AnnImages> img_List = annInfoData.getBody();
                if (img_List == null) {
                    img_List = new ArrayList<AnnImages>();
                    Log.d("print", "onSuccess: 图片的URL" + img_List.get(0).getImgUrl());
                }
                // 更新轮播图
                updateBanner(img_List);
                // 保存到数据库;更新缓存
                if (img_List.size() > 0) {
                   /* try {
                        // 删除数据库缓存
                        DataSupport.deleteAll(AnnImages.class);
                        // 更新新的数据
                        DataSupport.saveAll(annInfoData.getBody());
                    } catch (Exception e) {
                        Log.d("print", "onFaild: 更新数据库失败的理由"+e.getMessage());
                        e.printStackTrace();
                    }*/
                    // 删除数据库缓存----报错原因 每次更新时litepal版本升级一次;
                    DataSupport.deleteAll(AnnImages.class);
                    // 更新新的数据
                    DataSupport.saveAll(annInfoData.getBody());
                }
            }
        });
        //下载最新咨询
        OkHttpManager.getInstance().getNet(Constant.Info + "?pagenum=" + 1 + "&type=" + 0, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {
                //失败则去数据库获取---
                mProgressBar.setVisibility(View.GONE);
                mRl_http_failed.setVisibility(View.VISIBLE);
                //资讯展示数据
                List<InfomationBody> infomationBody = DataSupport.findAll(InfomationBody.class);
                if (infomationBody != null) {
                    mInfomationListAdapter = new InfomationListAdapter(getContext(), infomationBody, mShowSize);
                    mFragment_home_info_list.setAdapter(mInfomationListAdapter);
                }

            }

            @Override
            public void onSuccess(String response) {
                //得到数据---解析---设置适配器
                mProgressBar.setVisibility(View.GONE);//隐藏Loding条
                Infomation infomation = GetJsonDatas.getInfomationData(response.toString());
                List<InfomationBody> infomationBody = infomation.getBody();
                Log.d("print", "onSuccess: 资讯信息----" + infomation.getBody().get(0).getTitle());
                if (infomationBody != null) {
                    mInfomationListAdapter = new InfomationListAdapter(getContext(), infomationBody, mShowSize);
                    mFragment_home_info_list.setAdapter(mInfomationListAdapter);
                    DataSupport.deleteAll(InfomationBody.class);
                    DataSupport.saveAll(infomationBody);
                }
            }
        });
        //登陆后下载最新任务
        if (isLogin) {
            OkHttpManager.getInstance().getNet(Constant.Task + "?pagenum=" + 1, new OkHttpManager.ResultCallback() {
                @Override
                public void onFaild(Request request, IOException e) {
                    mProgressBar.setVisibility(View.GONE);
                    mRl_http_failed.setVisibility(View.VISIBLE);
                    //----去数据库获取
                    List<HomeTaskBody> homeBodyList = DataSupport.findAll(HomeTaskBody.class);
                    if (homeBodyList != null){
                        mHomeTaskListAdapter = new HomeTaskListAdapter(getContext(),homeBodyList,mShowSize);
                        mFragment_home_task_list.setAdapter(mHomeTaskListAdapter);
                    }
                }

                @Override
                public void onSuccess(String response) {
                    HomeTask taskData = GetJsonDatas.getHomeTaskData(response.toString());
                    List<HomeTaskBody> homeBodyList = taskData.getTaskBody();
                    if (homeBodyList != null){
                        mHomeTaskListAdapter = new HomeTaskListAdapter(getContext(),homeBodyList,mShowSize);
                        mFragment_home_task_list.setAdapter(mHomeTaskListAdapter);
                        DataSupport.deleteAll(HomeTaskBody.class);
                        DataSupport.saveAll(homeBodyList);
                    }
                }
            });
        }
    }

    /**
     * 根据公告图片地址动态更新界面
     */
    public void updateBanner(List<AnnImages> img_List) {
        Log.d("print", "updateBanner: 更新界面");
        views.clear();//清除List中的view的缓存
        for (AnnImages images : img_List) {
            ImageView img = new ImageView(getActivity());
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(getActivity())
                    .load(Constant.BaseUrl + images.getImgUrl())
                    .into(img);
            views.add(img);
            Log.d("print", "updateBanner: views的数量" + views.size());
        }
        //更新界面显示
        hmViewpageAdapter.notifyDataSetChanged();
        //显示图片的下标
        initLayout();
        //开启计时器
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(task, 0, 3000);
        }
    }

    /**
     *
     */
    private void initLayout() {
        mLinearLayout.removeAllViews();//移除所有指示下标布局;
        for (int i = 0; i < views.size(); i++) {
            ImageView img = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5, 0, 5, 0);
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
            if (i == 0) {
                img.setSelected(true);
            }
            mLinearLayout.addView(img);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.Scroll:
                    mHomeFragmentviewPager.setCurrentItem(count);
                    break;
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (count == views.size()) {
                count = 0;
            } else {
                count = count + 1;
            }
            mHandler.sendEmptyMessage(Constant.Scroll);
        }
    };

    /**
     * ViewPager的适配器;
     */
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
                if (i == position) {
                    childAtImg.setSelected(true);
                } else {
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

