package com.weifupro;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.weifupro.activity.BaseFragmentActivity;
import com.weifupro.fragment.HomeFragment;
import com.weifupro.fragment.MeFragment;
import com.weifupro.fragment.ShopFragment;
import com.weifupro.fragment.TrainFragment;
import com.weifupro.fragment.VisitFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by "huangsays"  on 2017/7/18.16:10"huangays@gmail.com"
 * Base的沉浸式还有问题需要处理；
 */

public class MainActivity_test extends BaseFragmentActivity {

    private ViewPager mViewPager;
    private RadioGroup mMain_rg;
    private RadioButton mMain_rb1;
    private RadioButton mMain_rb2;
    private RadioButton mMain_rb3;
    private RadioButton mMain_rb4;
    private RadioButton mMain_rb5;
    private TextView mTitle_bar_back;
    private TextView mTitle_bar_name;
    private ImageView mTitle_bar_more;
    private ImageView mTitle_bar_save;
    private ImageView mTitle_bar_change;

    private ViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        bindViews();
        initView();
        setListener();
    }


    private void bindViews() {
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mMain_rg = (RadioGroup) findViewById(R.id.main_rg);
        mMain_rb1 = (RadioButton) findViewById(R.id.main_rb1);
        mMain_rb2 = (RadioButton) findViewById(R.id.main_rb2);
        mMain_rb3 = (RadioButton) findViewById(R.id.main_rb3);
        mMain_rb4 = (RadioButton) findViewById(R.id.main_rb4);
        mMain_rb5 = (RadioButton) findViewById(R.id.main_rb5);

        mTitle_bar_back = (TextView) findViewById(R.id.title_bar_back);
        mTitle_bar_name = (TextView) findViewById(R.id.title_bar_name);
        mTitle_bar_more = (ImageView) findViewById(R.id.title_bar_more);
        mTitle_bar_save = (ImageView) findViewById(R.id.title_bar_save);
        mTitle_bar_change = (ImageView) findViewById(R.id.title_bar_change);
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
                        mViewPager.setCurrentItem(0);
                        Log.d("print", "onCheckedChanged: RB的点击");

                        break;
                    case R.id.main_rb2:
                        mViewPager.setCurrentItem(1);

                        break;
                    case R.id.main_rb3:
                        mViewPager.setCurrentItem(2);

                        break;
                    case R.id.main_rb4:
                        mViewPager.setCurrentItem(3);

                        break;
                    case R.id.main_rb5:
                        mViewPager.setCurrentItem(4);

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
                Log.d("print", "onPageSelected: 滑动的时候滑动的下标为"+position);
                switch (position){
                    case 0:
                        mMain_rb1.setChecked(true);
                        Log.d("print", "onPageSelected: ViewPager的滑动");
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
