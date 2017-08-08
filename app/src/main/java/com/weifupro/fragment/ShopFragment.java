package com.weifupro.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.weifupro.R;
import com.weifupro.adapters.ShopListAdapter;
import com.weifupro.bean.DateList;
import com.weifupro.bean.HistoryShopResult;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.Constant;
import com.weifupro.utils.GetJsonDatas;
import com.weifupro.utils.SharePreUtil;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by HuangJ on 2017/7/16.17:44
 */

public class ShopFragment extends BaseFragment implements TextView.OnEditorActionListener, View.OnClickListener, XRecyclerView.LoadingListener {

    private Activity mActivity;
    private Context mContext;
    private ShopListAdapter mAdapter;
    private RelativeLayout mRelativeLayout_shop, mRelativeLayout_progressBar;
    private EditText mEt_search_shop;
    private TextView mSearch_none_text;
    private XRecyclerView mFragment_shop_list;
    private RelativeLayout mFragment_shop_refrensh;
    private ImageView mShop_http_failed;

    private static List<DateList> mList;

    private int pagenum;//当前页数
    private String shop_name;
    private boolean IsSearch;//是否进入搜索模式
    private String userid;//用户id
    private boolean isLoad;//是否登录
    public static boolean isFirst;//是否是首次登录

    @Override
    public int getContentId() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFirst = true;
    }

    @Override
    protected void init(View view) {
        super.init(view);
        mContext = getContext();
        mActivity = getActivity();
        mRelativeLayout_shop = (RelativeLayout) view.findViewById(R.id.shop_rl_search);
        mEt_search_shop = (EditText) view.findViewById(R.id.et_search_shop);

        mFragment_shop_list = (XRecyclerView) view.findViewById(R.id.fragment_shop_list);
        mFragment_shop_refrensh = (RelativeLayout) view.findViewById(R.id.fragment_shop_refrensh);
        mShop_http_failed = (ImageView) view.findViewById(R.id.shop_http_failed);
        mShop_http_failed.setOnClickListener(this);//联网失败监听
        mRelativeLayout_progressBar = (RelativeLayout) view.findViewById(R.id.rl_progressbar);
        mEt_search_shop.setOnEditorActionListener(this);//搜索框监听

        mFragment_shop_list.setLoadingListener(this);//给XRLView设置下拉刷新以及上拉加载更多...
        //设置线性列表展示
        mFragment_shop_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        View emptyView = view.findViewById(R.id.search_none_text);
        emptyView.setOnClickListener(this);
        //设置空布局;
        mFragment_shop_list.setEmptyView(emptyView);
        //设置加载风格
        mFragment_shop_list.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mFragment_shop_list.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        if (mList == null){
            mList = new ArrayList<>();
        }
        mAdapter = new ShopListAdapter(mContext,mList);
        //设置适配器;
        mFragment_shop_list.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        userid = SharePreUtil.GetShareString(mContext, "userid");
        if (TextUtils.isEmpty(userid)) {
            isLoad = false;
            Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
        } else {
            isLoad = true;
            if (isFirst) {
                isFirst = false;
                pagenum = 1;
                initData();//加载数据;
                mRelativeLayout_progressBar.setVisibility(View.VISIBLE);//显示Loding...
            }
        }
    }

    // 下载数据;
    private void initData() {
        Log.d("print", "initData: 下载数据--页数为:" + pagenum);
        //店面查询请求
        String urlString = "";
        if (IsSearch) {
            urlString = Constant.HistroyShop + "?userid=" + userid + "&pagenum=" + pagenum + "&keyword=" + shop_name;
        } else {
            urlString = Constant.HistroyShop + "?userid=" + userid + "&pagenum=" + pagenum;
        }
        OkHttpManager.getInstance().getNet(urlString, new OkHttpManager.ResultCallback() {
            @Override
            public void onFaild(Request request, IOException e) {

                onStopLoad();
                mRelativeLayout_progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, R.string.http_failed, Toast.LENGTH_LONG).show();//告知用户网络连接失败..
                if (mList == null || mList.size() == 0) {
                    mList = DataSupport.findAll(DateList.class);//先去数据库查询
                    if (mList.size() > 0) {
                        Log.d("print", "onFaild: 数据库有数据");
                        mShop_http_failed.setVisibility(View.GONE);//隐藏网络请求失败;
                        //设置数据进适配器;
                        mAdapter.setmList(mList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Log.d("print", "onFaild: 数据库无数据则显示联网失败");
                        mShop_http_failed.setVisibility(View.VISIBLE);//网络请求失败;
                    }
                }

            }

            @Override
            public void onSuccess(String response) {
                HistoryShopResult shopData = GetJsonDatas.getShopData(response);
                onStopLoad();
                mRelativeLayout_progressBar.setVisibility(View.GONE);
                mShop_http_failed.setVisibility(View.GONE);
                if (shopData != null && shopData.getCode() == 0 && shopData.getDatelist() != null) {

                    if (mList == null) {
                        mList = new ArrayList<DateList>();
                    }
                    // 如果是第一页的数据则清空;
                    if (pagenum == 1) {
                        mList.clear();
                    }
                    if (shopData.getDatelist().size() == 0) {
                        Toast.makeText(mContext, "没有更多数据", Toast.LENGTH_SHORT).show();

                    } else {
                        mList.addAll(shopData.getDatelist());
                    }
                    pagenum++;
                    Log.d("print", "shopFragment----:现在的页数为:" + pagenum);
                    // 适配器添加数据;
                    Log.d("print", "shopFragment-----数据为:" + mList.get(0).getName());
                    mAdapter.setmList(mList);
                    mAdapter.notifyDataSetChanged();
                    //添加至数据库
                    DataSupport.deleteAll(DateList.class);
                    DataSupport.saveAll(shopData.getDatelist());

                }
            }
        });
    }

    /**
     * 监听软键盘的活动监听;
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //点击搜索;
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            Log.d("print", "onEditorAction: ---点击软键盘的搜索---");

        }

        return false;
    }

    /**
     * 网络请求失败点击事件;
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_http_failed:
                mShop_http_failed.setVisibility(View.GONE);
                initData();//重新下拉数据;
                break;
            case R.id.search_none_text:
                initData();
                break;
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        //下拉刷新
        pagenum = 1;
        initData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        //加载更多
        initData();
    }

    /**
     * 结束上下拉刷新
     */
    private void onStopLoad() {
        mFragment_shop_list.loadMoreComplete();
        mFragment_shop_list.refreshComplete();
    }

    public void hideKeyboard() {//隐藏软键盘
        InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(mActivity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 清空变量
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mList = null;
    }
}
