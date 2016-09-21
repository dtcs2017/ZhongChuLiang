package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zcl.hxqh.zhongchuliang.R;

public class LoginActivity extends BaseActivity {


    private Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();
    }



    private void findViewById() {
        loginBtn=(Button)findViewById(R.id.btn_login);
    }

    private void initView() {
        loginBtn.setOnClickListener(loginBtnOnClickListener);
    }
    private View.OnClickListener loginBtnOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            jumpMainActicity();
        }
    };


    /**
     * 跳转至主界面*
     */
    private void jumpMainActicity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


}
