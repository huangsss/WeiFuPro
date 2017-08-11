package com.weifupro.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.weifupro.R;


/**
 * Created by "huangsays"  on 2017/8/10.11:22"huangays@gmail.com"
 */

public class UpdateDialog {


    public static void showPopWindow(final Context context, final Activity activity, String updateInfo, final String downloadUrl){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);//获falter对象;
        View popView = inflater.inflate(R.layout.fragment_popwindow,null);
        // 自定义popupWindow true 设置弹出窗体可点击;
        PopupWindow window = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,true);
        window.setOutsideTouchable(true);

    }
}
