package com.weifupro.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.weifupro.R;


public class BaseFragmentActivity extends AppCompatActivity {

	protected Context mContext;
	protected Activity mActivity;
	private TextView title_bar_back,title_bar_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mActivity = this;
//		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@SuppressLint("NewApi")
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	/*	if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		//4.0  这个一个boolean值的内部属性，让view可以根据系统窗口(如status bar)来调整自己的布局，
		//    如果值为true,就会调整view的paingding属性来给system windows留出空间....
		if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
			View v = findViewById(R.id.root_layout);
			if (v != null) {
				v.setFitsSystemWindows(true);
			}
		}
		// 5.0
		if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();  
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);  
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  
            window.setStatusBarColor(Color.TRANSPARENT);
        }*/
		title_bar_back = (TextView) findViewById(R.id.title_bar_back);
		title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		if(title_bar_back != null){
			title_bar_back.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}
	
	public void setTitleName(String str){
		if(title_bar_name != null){
			title_bar_name.setText(str);
		}
	}
}
