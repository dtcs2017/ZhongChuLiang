package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.zcl.hxqh.zhongchuliang.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 2000);
    }

    class splashhandler implements Runnable {

        public void run() {
            jumpLoginActivity();
        }

    }




    /**
     * 跳转至登录界面*
     */
    private void jumpLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

}
