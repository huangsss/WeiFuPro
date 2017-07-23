package com.weifupro;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.weifupro.activity.BaseFragmentActivity;
import com.weifupro.fragment.HomeFragment;
import com.weifupro.fragment.MeFragment;
import com.weifupro.fragment.ShopFragment;
import com.weifupro.fragment.TrainFragment;
import com.weifupro.fragment.VisitFragment;
import com.weifupro.utils.SharePreUtil;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

/**
 * Created by "huangsays"  on 2017/7/18.16:10"huangays@gmail.com"
 * Base的沉浸式还有问题需要处理；
 */

public class MainActivity extends BaseFragmentActivity {

    private ViewPager mViewPager;
    private RadioGroup mMain_rg;
    private RadioButton mMain_rb1;
    private RadioButton mMain_rb2;
    private RadioButton mMain_rb3;
    private RadioButton mMain_rb4;
    private RadioButton mMain_rb5;

    private ImageView mTitle_bar_more;
    private ImageView mTitle_bar_save;
    private ImageView mTitle_bar_change;
    private TextView mTitle_bar_back;

    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> fragmentList;

    private String userId;
    private boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();//绑定id;
        initView();//初始化;
        setListener();//监听事件;
    }

    @Override
    protected void onResume() {
        super.onResume();
        userId = SharePreUtil.GetShareString(mActivity,"userid");
        if ("".equals(userId)){
            Log.d("print", "onResume: 未登陆");
            Toast.makeText(mContext, "你未登录！", Toast.LENGTH_SHORT).show();
        }else{
            isLogin = true;
        }
    }

    private void bindViews() {
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mMain_rg = (RadioGroup) findViewById(R.id.main_rg);
        mMain_rb1 = (RadioButton) findViewById(R.id.main_rb1);
        mMain_rb2 = (RadioButton) findViewById(R.id.main_rb2);
        mMain_rb3 = (RadioButton) findViewById(R.id.main_rb3);
        mMain_rb4 = (RadioButton) findViewById(R.id.main_rb4);
        mMain_rb5 = (RadioButton) findViewById(R.id.main_rb5);
        mTitle_bar_more = (ImageView) findViewById(R.id.title_bar_more);
        mTitle_bar_save = (ImageView) findViewById(R.id.title_bar_save);
        mTitle_bar_change = (ImageView) findViewById(R.id.title_bar_change);
        mTitle_bar_back = (TextView) findViewById(R.id.title_bar_back);


    }

    private void initView() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new ShopFragment());
        fragmentList.add(new VisitFragment());
        fragmentList.add(new TrainFragment());
        fragmentList.add(new MeFragment());
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);//一次性加载3个Fragment;
        mMain_rg.getChildAt(0).performClick();//默认点击主页;
        setTitleName("首页");
        mTitle_bar_back.setVisibility(GONE);
        mTitle_bar_save.setVisibility(GONE);
        mTitle_bar_change.setVisibility(GONE);
    }

    private void setListener() {
        /**
         *RadioButton的事件监听;
         */
        mMain_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.main_rb1:
                        mTitle_bar_more.setVisibility(GONE);
                        mTitle_bar_change.setVisibility(GONE);
                        mTitle_bar_back.setVisibility(GONE);
                        mTitle_bar_save.setVisibility(GONE);
                        mViewPager.setCurrentItem(0,false);// false 去除ViewPager的滑动效果
                        setTitleName("首页");
                        break;
                    case R.id.main_rb2:
                        mTitle_bar_more.setVisibility(View.GONE);
                        mTitle_bar_change.setVisibility(View.VISIBLE);
                        mTitle_bar_save.setVisibility(View.VISIBLE);
                        mTitle_bar_back.setVisibility(View.VISIBLE);
                        mViewPager.setCurrentItem(1,false);
                        setTitleName("巡店");
                        break;
                    case R.id.main_rb3:
                        mTitle_bar_more.setVisibility(View.VISIBLE);
                        mTitle_bar_change.setVisibility(GONE);
                        mTitle_bar_save.setVisibility(GONE);
                        mTitle_bar_back.setVisibility(View.GONE);
                        mViewPager.setCurrentItem(2,false);
                        setTitleName("拜访");
                        break;
                    case R.id.main_rb4:
                        mTitle_bar_more.setVisibility(GONE);
                        mTitle_bar_change.setVisibility(GONE);
                        mTitle_bar_save.setVisibility(GONE);
                        mTitle_bar_back.setVisibility(View.GONE);
                        mViewPager.setCurrentItem(3,false);
                        setTitleName("培训");
                        break;
                    case R.id.main_rb5:
                        mTitle_bar_more.setVisibility(GONE);
                        mTitle_bar_change.setVisibility(GONE);
                        mTitle_bar_back.setVisibility(GONE);
                        mTitle_bar_save.setVisibility(GONE);
                        mViewPager.setCurrentItem(4,false);
                        setTitleName("个人中心");
                        break;
                }
            }
        });
        /**
         * ViewPager的事件监听;
         */
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mMain_rb1.setChecked(true);
                        break;
                    case 1:
                        mMain_rb2.setChecked(true);
                        break;
                    case 2:
                        mMain_rb3.setChecked(true);
                        break;
                    case 3:
                        mMain_rb4.setChecked(true);
                        break;
                    case 4:
                        mMain_rb5.setChecked(true);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * ViewPager的适配器
     */
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragmentList;
        public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}
