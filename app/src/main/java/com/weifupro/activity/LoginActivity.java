package com.weifupro.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.weifupro.MainActivity;
import com.weifupro.R;
import com.weifupro.bean.LoginBeanResult;
import com.weifupro.bean.User;
import com.weifupro.net.OkHttpManager;
import com.weifupro.utils.GetJsonDatas;
import com.weifupro.utils.SharePreUtil;
import com.weifupro.utils.StatusbarUtils;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

import static com.weifupro.utils.Constant.Login;

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
    private RelativeLayout mReLoading;

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
            bindViews();//
            initListener();//事件监听
        }
    }

    private boolean checkLogin() {
        //获取数据库用户;
        List<User> userList = DataSupport.findAll(User.class);
        if(userList != null && userList.size() > 0){
            return true;
        }
        return false;
    }

    private void bindViews() {
        mReLoading = (RelativeLayout) findViewById(R.id.loading);
        mEt_name_design = (TextInputLayout) findViewById(R.id.et_name_design);
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_password_design = (TextInputLayout) findViewById(R.id.et_password_design);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mBtn_login = (Button) findViewById(R.id.btn_login);
        mEt_name_design.setCounterEnabled(true);//设置是否显示限制提示
        mEt_name_design.setCounterMaxLength(6);//设置显示限制长度
        mEt_password_design.setCounterMaxLength(20);
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
                userNameisOk();
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
                userPasswordisOk();
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
                login();
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        if (checkData()) {
            // 数据正确、可以进行登录
            mReLoading.setVisibility(View.VISIBLE);//显示登陆加载界面;
            //Post发送登陆请求;
            OkHttpManager.getInstance().postNet(Login, new OkHttpManager.ResultCallback() {
                @Override
                public void onFaild(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mReLoading.setVisibility(View.GONE);//登录失败.去除Loading条
                            Toast.makeText(LoginActivity.this,"连接服务器失败，登录失败",Toast.LENGTH_LONG).show();
                        }
                    });

                }

                @Override
                public void onSuccess(String response) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mReLoading.setVisibility(View.GONE);//登录成功.去除Loading条
                        }
                    });
                    LoginBeanResult loginBeanResult = GetJsonDatas.getLoginInfoData(response);
                    if (loginBeanResult.getCode() == 0){
                        //用户存在，密码正确，登录成功,先保存登录信息，然后跳转至主界面
                        SharePreUtil.SetShareString(mContext,"userid",loginBeanResult.getBody().getUserid());
                        //先清除数据库
                        DataSupport.deleteAll(User.class);
                        User user = new User();
                        user.setUserId(loginBeanResult.getBody().getUserid());
                        user.setNickName(loginBeanResult.getBody().getNickname());
                        user.setSex(loginBeanResult.getBody().getSex());
                        user.setJob(loginBeanResult.getBody().getJob());
                        user.setArea(loginBeanResult.getBody().getArea());
                        user.setPhoneNum(loginBeanResult.getBody().getPhonenum());
                        user.setImg(loginBeanResult.getBody().getImg());
                        //保存到数据库之中
                        user.save();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                    }else{
                        //登录失败
                        Toast.makeText(LoginActivity.this, "登录失败！请检查账号或者密码", Toast.LENGTH_SHORT).show();
                    }
                }
            },new OkHttpManager.Param("userid",mUserName),new OkHttpManager.Param("password",mPassWord));

        }
    }

    /**
     * 检验登录数据是否正确
     */
    private boolean checkData() {
        if (!userNameisOk()) {
            return false;
        }else if (!userPasswordisOk()) {
            return false;
        }
        return true;
    }

    /**
     * 检查用户名是否正确
     */
    private boolean userNameisOk() {
        String user_regStr = "^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,6}$";//账户的正则  昵称格式：限16个字符，支持中英文、数字、减号或下划线
        mUserName = mEt_name.getText().toString().trim();//获得输入的用户名
        if (!mUserName.matches(user_regStr)) {
            mEt_name_design.setErrorEnabled(true);//是否显示错误信息
            if (mUserName.length() > 6) {
                mEt_name_design.setError("用户名不能超过6位");
                Log.d("print", "userNameisOk: ssss");
            } else {
                mEt_name_design.setError("用户名格式错误！");
            }
            return false;
        } else {
            mEt_name_design.setErrorEnabled(false);
            return true;
        }
    }

    private boolean userPasswordisOk() {
        //密码的正则6-20 位，字母、数字、字符
        String password_regStr = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]){5,20}$";
        mPassWord = mEt_password.getText().toString().trim();
        if (!mPassWord.matches(password_regStr)) {
            mEt_password_design.setErrorEnabled(true);
            if (mPassWord.length() < 5 || mPassWord.length() > 20){
                mEt_password_design.setError("密码长度位于5-20位");
            }else{
                mEt_password_design.setError("密码格式错误");
            }
            return false;
        } else {
            mEt_password_design.setErrorEnabled(false);
            return true;
        }
    }
}
