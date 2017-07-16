package com.weifupro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import static com.weifupro.R.id.title_bar_change;
import static com.weifupro.R.id.title_bar_more;

public class MainActivity extends BaseFragmentActivity {

    private String userid;//用户id
    private Boolean isLoad;//是否登录

    private RadioGroup mMain_rg;
    private ViewPager mMain_viewPager;

    private TextView mTitle_bar_back;
    private TextView mTitle_bar_name;
    private ImageView mTitle_bar_more;
    private ImageView mTitle_bar_save;
    private ImageView mTitle_bar_change;

    private HomeFragment mHomeFragment;
    private ShopFragment mShopFragment;
    private VisitFragment mVisitFragment;
    private TrainFragment mTrainFragment;
    private MeFragment mMeFragment;
    private MainFragmentAdapter mMainFragmentAdapter;

    private final int TAB_HOME = 0;
    private final int TAB_SHOP = 1;
    private final int TAB_VISIT = 2;
    private final int TAB_TRAIN = 3;
    private final int TAB_ME = 4;
    private int IsTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mHomeFragment = new HomeFragment();
        mShopFragment = new ShopFragment();
        mVisitFragment = new VisitFragment();
        mTrainFragment = new TrainFragment();
        mMeFragment = new MeFragment();
        mMainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        mMain_viewPager.setAdapter(mMainFragmentAdapter);
//        mMain_viewPager.setCurrentItem(TAB_HOME);
        mMain_rg.getChildAt(0).performClick();
    }
    @Override
    protected void onPause() {
        userid = SharePreUtil.GetShareString(mContext, "userid");
        if (userid == null) {
            Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
        } else {
            isLoad = true;//登陆成功
        }
        super.onPause();
    }

    private void bindViews() {

        mMain_rg = (RadioGroup) findViewById(R.id.main_rg);
        mMain_viewPager = (ViewPager) findViewById(R.id.main_viewpager);

        mTitle_bar_back = (TextView) findViewById(R.id.title_bar_back);
        mTitle_bar_name = (TextView) findViewById(R.id.title_bar_name);
        mTitle_bar_more = (ImageView) findViewById(title_bar_more);
        mTitle_bar_save = (ImageView) findViewById(R.id.title_bar_save);
        mTitle_bar_change = (ImageView) findViewById(title_bar_change);
        mMain_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("main_viewpager", "position--" + position);
                switch (position) {
                    case TAB_HOME:
                        //主页
                        jumpHome();
                    case TAB_SHOP:
                        jumpShop();
                    case TAB_TRAIN:
                        jumpTrain();
                    case TAB_VISIT:
                        jumpVisit();
                    case TAB_ME:
                        jumpMe();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void jumpMe() {
        IsTab = 5;
        mTitle_bar_more.setVisibility(View.GONE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_ME, false);
        setTitleName("个人中心");
    }

    private void jumpVisit() {
        IsTab = 4;
        mTitle_bar_more.setVisibility(View.VISIBLE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_VISIT, false);
        setTitleName("拜访");
    }

    private void jumpTrain() {
        IsTab = 3;
        mTitle_bar_more.setVisibility(View.GONE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_TRAIN, false);
        setTitleName("培训");
    }

    private void jumpShop() {
        IsTab = 2;
        mTitle_bar_more.setVisibility(View.VISIBLE);
        mTitle_bar_change.setVisibility(View.VISIBLE);
        mMain_viewPager.setCurrentItem(TAB_SHOP, false);
        setTitleName("寻店");
    }

    private void jumpHome() {
        IsTab = 1;
        mTitle_bar_more.setVisibility(View.GONE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_HOME, false);// false 去除ViewPager的滑动效果
        setTitleName("首页");
    }

    /**
     * 底部按钮监听;
     *
     * @param view
     */
    public void btnOnclick(View view) {
        /*if (!isLoad) {
            Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
        }*/
        switch (view.getId()) {
            case R.id.main_rb1:
                jumpHome();
                return;
            case R.id.main_rb2:
                jumpShop();
                return;
            case R.id.main_rb3:
                jumpVisit();
                return;
            case R.id.main_rb4:
                jumpTrain();
                return;
            case R.id.main_rb5:
                jumpMe();
                return;
            case R.id.title_bar_more:
                if (isLoad) {
                    if (IsTab == 2) {//新建巡店
                        Toast.makeText(mActivity, "新建巡店敬请期待", Toast.LENGTH_SHORT).show();
                    } else if (IsTab == 3) {//新建拜访
                        Toast.makeText(mActivity, "新建拜访暂未开放", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter {
        private final int TAB_COUNT = 5;

        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case TAB_HOME:
                    return mHomeFragment;
                case TAB_SHOP:
                    return mShopFragment;
                case TAB_VISIT:
                    return mVisitFragment;
                case TAB_TRAIN:
                    return mTrainFragment;
                case TAB_ME:
                    return mMeFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}
