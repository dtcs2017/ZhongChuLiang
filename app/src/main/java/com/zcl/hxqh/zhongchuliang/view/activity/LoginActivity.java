package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.bugly.Bugly;
import com.zcl.hxqh.zhongchuliang.AppManager;
import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.dialog.FlippingLoadingDialog;
import com.zcl.hxqh.zhongchuliang.until.AccountUtils;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private EditText mUsername;
    private EditText mPassword;

    private Button loginBtn;

    private CheckBox checkBox; //记住密码

    private boolean isRemember; //是否记住密码

    String userName; //用户名
    String userPassWorld; //密码
    String imei; //imei

    private long exitTime = 0;

    protected FlippingLoadingDialog mLoadingDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bugly.init(getApplicationContext(), "a8d607bea9", true);

        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        findViewById();
        initView();
        setEvent();


    }


    private void findViewById() {

        mUsername = (EditText) findViewById(R.id.login_username_edit);
        mPassword = (EditText) findViewById(R.id.login_password_edit);

        loginBtn = (Button) findViewById(R.id.btn_login);

        boolean isChecked = AccountUtils.getIsChecked(LoginActivity.this);
        if (isChecked) {
            mUsername.setText(AccountUtils.getUserName(LoginActivity.this));
            mPassword.setText(AccountUtils.getUserPassword(LoginActivity.this));
        }


        checkBox = (CheckBox) findViewById(R.id.checkBox);
    }

    /**
     * 设置事件监听*
     */
    private void setEvent() {
        checkBox.setOnCheckedChangeListener(cheBoxOnCheckedChangListener);
    }


    private CompoundButton.OnCheckedChangeListener cheBoxOnCheckedChangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isRemember = isChecked;
        }
    };


    private void initView() {
        loginBtn.setOnClickListener(this);
    }


    /**
     * 跳转至主界面*
     */
    private void startIntent() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (mUsername.getText().length() == 0) {
                    mUsername.setError(getString(R.string.login_error_empty_user));
                    mUsername.requestFocus();
                } else if (mPassword.getText().length() == 0) {
                    mPassword.setError(getString(R.string.login_error_empty_passwd));
                    mPassword.requestFocus();
                } else {
                    login();
                }
                break;

        }
    }


    /**
     * 登陆*
     */
    private void login() {
        getLoadingDialog("正在登陆...").show();

        ImManager.loginWithUsername(LoginActivity.this,
                mUsername.getText().toString(),
                mPassword.getText().toString(), imei,
                new HttpRequestHandler<String>() {
                    @Override
                    public void onSuccess(String data) {

                        getLoadingDialog("正在登陆...").dismiss();
                        if (data != null) {
                            getBaseApplication().setUsername(mUsername.getText().toString());
                            if (isRemember) {
                                AccountUtils.setChecked(LoginActivity.this, isRemember);
                                //记住密码
                                AccountUtils.setUserNameAndPassWord(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                            }
                            startIntent();
                        } else {
                            MessageUtils.showMiddleToast(LoginActivity.this, "用户名或密码错误");
                        }
                    }

                    @Override
                    public void onSuccess(String data, int totalPages, int currentPage) {
                        if (data != null) {
                            MessageUtils.showMiddleToast(LoginActivity.this, getString(R.string.login_successful_hint));

                            startIntent();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        MessageUtils.showErrorMessage(LoginActivity.this, error);
                        getLoadingDialog("正在登陆...").dismiss();
                    }
                });
    }


    @Override
    public void onBackPressed() {


        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_LONG).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.AppExit(LoginActivity.this);
        }
    }

    private FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }




}
