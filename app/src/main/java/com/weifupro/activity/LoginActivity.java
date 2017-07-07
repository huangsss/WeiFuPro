package com.weifupro.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weifupro.MainActivity;
import com.weifupro.R;
import com.weifupro.utils.StatusbarUtils;

import org.litepal.tablemanager.Connector;

/**
 * 一、登陆界面
 * Created by "huangsays"  on 2017/7/6.19:29"huangays@gmail.com"
 */

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout mEt_name_design;
    private EditText mEt_name;
    private TextInputLayout mEt_password_design;
    private EditText mEt_password;
    private Button mBtn_login;
    private Context mContext;
    private String mUserName;
    private String mPassWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        SQLiteDatabase db = Connector.getDatabase();

        if (checkLogin()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            StatusbarUtils.enableTranslucentStatusbar(this);//状态栏透明化
//            initListener();//事件监听
            bindViews();
        }
    }

    private boolean checkLogin() {
        //获取数据库用户;

        return false;
    }


    private void bindViews() {

        mEt_name_design = (TextInputLayout) findViewById(R.id.et_name_design);
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_password_design = (TextInputLayout) findViewById(R.id.et_password_design);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mBtn_login = (Button) findViewById(R.id.btn_login);
        mEt_name_design.setCounterEnabled(true);//设置是否显示限制提示
        mEt_name_design.setCounterMaxLength(6);//设置显示限制长度
        mEt_password_design.setCounterMaxLength(12);
        mEt_password_design.setCounterEnabled(true);
        mEt_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEt_name_design.setErrorEnabled(true);//设置错误文字是否可用
                if (mEt_name.getText().toString().trim().length() > 6){
                    mEt_name_design.setErrorEnabled(true);
                    mEt_name_design.setError("用户名不能超过6位");
                }else{
                    mEt_name_design.setErrorEnabled(false);
                }
            }
        });

        mEt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mEt_password_design.setErrorEnabled(true);
            }
        });
    }

    /**
     * 监听
     */
    private void initListener() {

        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击登录", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 登录
     */
    private void login() {
        if (checkData()) {
            //可以进行登录

        }
    }

    /**
     * 检验登录数据是否正确
     */
    private boolean checkData() {
        mUserName = mEt_name.getText().toString().trim();
        mPassWord = mEt_password.getText().toString().trim();
      /*  String user_regStr = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,6}$";//账户的正则  昵称格式：限16个字符，支持中英文、数字、减号或下划线
        //账户的正则6-20 位，字母、数字、字符
        String password_regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){6,20}$";
        if (!mUserName.matches(user_regStr)) {
            mEt_name_design.setError("格式不正确");
            return false;
        }
        if (!mPassWord.matches(password_regStr)) {
            mEt_name_design.setError("密码只能以字母、数字、字符构成！");
        }*/
        if (TextUtils.isEmpty(mUserName.trim())) {
            mEt_name_design.setError("用户名不能为空");
            return false;
        }
        if (mUserName.trim().length() < 0 || mUserName.trim().length() > 6) {
            mEt_password_design.setError("请输入6位数以内的用户名");
            return false;
        }
        if (TextUtils.isEmpty(mPassWord)) {
            mEt_password_design.setError("密码不能为空");
            return false;
        }
        return true;
    }
}
