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
import com.weifupro.Fragment_2.HomeFragment;
import com.weifupro.Fragment_2.MeFragment;
import com.weifupro.Fragment_2.ShopFragment;
import com.weifupro.Fragment_2.TrainFragment;
import com.weifupro.Fragment_2.VisitFragment;

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

        Log.d("print", "onCreate: 主页");
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

        mMain_rg.getChildAt(0).performClick();
    }

    @Override
    protected void onResume() {
        //判断是否已经登录;

        super.onResume();
    }

    private void bindViews() {

        mMain_rg = (RadioGroup) findViewById(R.id.main_rg);
        mMain_viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTitle_bar_back = (TextView) findViewById(R.id.title_bar_back);
        mTitle_bar_name = (TextView) findViewById(R.id.title_bar_name);
        mTitle_bar_more = (ImageView) findViewById(title_bar_more);
        mTitle_bar_save = (ImageView) findViewById(R.id.title_bar_save);
        mTitle_bar_change = (ImageView) findViewById(title_bar_change);


    }

    private void jumpMe() {
        mTitle_bar_more.setVisibility(View.GONE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_ME, false);
        setTitleName("个人中心");
    }

    private void jumpVisit() {
        mTitle_bar_more.setVisibility(View.VISIBLE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_VISIT, false);
        setTitleName("拜访");
    }

    private void jumpTrain() {
        mTitle_bar_more.setVisibility(View.GONE);
        mTitle_bar_change.setVisibility(View.GONE);
        mMain_viewPager.setCurrentItem(TAB_TRAIN, false);
        setTitleName("培训");
    }

    private void jumpShop() {
        mTitle_bar_more.setVisibility(View.VISIBLE);
        mTitle_bar_change.setVisibility(View.VISIBLE);
        mMain_viewPager.setCurrentItem(TAB_SHOP, false);
        setTitleName("寻店");
    }

    private void jumpHome() {
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
                Toast.makeText(mActivity, "首页", Toast.LENGTH_SHORT).show();
                return;
            case R.id.main_rb2:
                Toast.makeText(mActivity, "巡店", Toast.LENGTH_SHORT).show();
                return;
            case R.id.main_rb3:
                Toast.makeText(mActivity, "拜访", Toast.LENGTH_SHORT).show();
                return;
            case R.id.main_rb4:
                Toast.makeText(mActivity, "培训", Toast.LENGTH_SHORT).show();
                return;
            case R.id.main_rb5:
                Toast.makeText(mActivity, "我的", Toast.LENGTH_SHORT).show();
                return;
            case R.id.title_bar_more:
               /* if (isLoad) {
                    if (IsTab == 2) {//新建巡店
                        Toast.makeText(mActivity, "新建巡店敬请期待", Toast.LENGTH_SHORT).show();
                    } else if (IsTab == 3) {//新建拜访
                        Toast.makeText(mActivity, "新建拜访暂未开放", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, R.string.please_login, Toast.LENGTH_SHORT).show();
                    }
                }*/
        }
    }

    private class MainFragmentAdapter extends FragmentPagerAdapter {
        private final int TAB_COUNT = 5;
        public MainFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Log.d("print", "getItem: Fragment适配器里面position---"+position);
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
