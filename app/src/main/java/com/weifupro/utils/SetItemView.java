package com.weifupro.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weifupro.R;

/**
 * Created by "huangsays"  on 2017/8/9.16:03"huangays@gmail.com"
 * 个人中心界面 Item;
 */

public class SetItemView extends RelativeLayout {
    // 页面对象
    private View mView;
    // 页面根布局
    private RelativeLayout mRelativeLayout;
    // 左侧文字
    private TextView mTextView;
    // 左侧图标
    private ImageView mLeftImage;
    // 右侧图标
    private ImageView mRightImage;
    // 左侧图标-需要传进来的
    private Drawable mLeftIcon;
    // 右侧图标-需要传进来的
    private Drawable mRightIcon;
    // 需显示的文字
    private String mText;
    // 文字大小
    private float mTextSize;
    // 文字颜色
    private int mTextColor;
    // 下划线
    private View mUnderLine;


    private onSetItemOnClick mOnSetItemOnClick;

    public void setmOnSetItemOnClick(onSetItemOnClick mOnSetItemOnClick) {
        this.mOnSetItemOnClick = mOnSetItemOnClick;
    }

    public SetItemView(Context context) {
        this(context, null);
    }

    public SetItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);// 初始化View
        getCustomStyle(context, attrs);
        mRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSetItemOnClick != null) {
                    mOnSetItemOnClick.onClick();
                }
            }
        });
    }

    /**
     * 初始化View
     */
    private void initView(Context context) {
        mView = View.inflate(context, R.layout.settingitem, this);
        mRelativeLayout = (RelativeLayout) mView.findViewById(R.id.rootLayout);
        mLeftImage = (ImageView) mView.findViewById(R.id.iv_lefticon);
        mTextView = (TextView) mView.findViewById(R.id.tv_lefttext);
        mRightImage = (ImageView) mView.findViewById(R.id.iv_righticon);
        mUnderLine = mView.findViewById(R.id.underline);
    }

    /**
     * 自定义属性;
     * @param context
     * @param attrs
     */
    private void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.SetItemView);
        int num = typedArray.getIndexCount();
        for (int i = 0; i < num; i++) {
            int arr = typedArray.getIndex(i);
            if (arr == R.styleable.SetItemView_leftText){
                mText = typedArray.getString(arr);
                mTextView.setText(mText);
            }else if (arr == R.styleable.SetItemView_leftIcon){
                mLeftIcon = typedArray.getDrawable(arr);
                mLeftImage.setImageDrawable(mLeftIcon);
            }else if (arr == R.styleable.SetItemView_rightIcon){
                mRightIcon = typedArray.getDrawable(arr);
                mRightImage.setImageDrawable(mRightIcon);
            }else if (arr ==R.styleable.SetItemView_textColor){
                //后面的参数设置文字默认灰色
                mTextColor = typedArray.getColor(arr, Color.GRAY);
                mTextView.setTextColor(mTextColor);
            }else if(arr == R.styleable.SetItemView_textSize){
                // 文字大小默认为16sp
                mTextSize = typedArray.getFloat(arr,16);
                mTextView.setTextSize(mTextSize);
            }else if(arr == R.styleable.SetItemView_isShowUnderLine){
                boolean flag = typedArray.getBoolean(arr,true);
                if (!flag){
                    mUnderLine.setVisibility(GONE);
                }
            }
        }
    }

    /**
     * Item的点击
     */
    public interface onSetItemOnClick {
        void onClick();
    }
}
