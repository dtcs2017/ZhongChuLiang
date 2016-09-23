package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.zhongchuliang.AppManager;
import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.view.fragment.CheckFragment;
import com.zcl.hxqh.zhongchuliang.view.fragment.InFragment;
import com.zcl.hxqh.zhongchuliang.view.fragment.OutFragment;
import com.zcl.hxqh.zhongchuliang.view.fragment.TransferFragment;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity implements OnClickListener {
    private TextView txt_title;
    private ImageView img_right;
    private Fragment[] fragments;
    public InFragment infragment;
    public OutFragment outfragment;
    public CheckFragment checkfragment;
    public TransferFragment transferfragment;
    private ImageView[] imagebuttons;
    private TextView[] textviews;

    /**
     * 搜索按钮*
     */
    private ImageView searchButton;

    private int index;
    private int currentTabIndex;// 当前fragment的index

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initViews();
        initTabView();
    }

    private void initTabView() {
        infragment = new InFragment();
        outfragment = new OutFragment();
        checkfragment = new CheckFragment();
        transferfragment = new TransferFragment();
        fragments = new Fragment[]{infragment, outfragment,
                checkfragment, transferfragment};
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_in);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_out);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_check);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_transfer);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_in);
        textviews[1] = (TextView) findViewById(R.id.tv_out);
        textviews[2] = (TextView) findViewById(R.id.tv_check);
        textviews[3] = (TextView) findViewById(R.id.tv_transfer);
        textviews[0].setTextColor(0xFF45C01A);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, infragment)
                .add(R.id.fragment_container, outfragment)
                .add(R.id.fragment_container, checkfragment)
                .add(R.id.fragment_container, transferfragment)
                .hide(outfragment).hide(checkfragment)
                .hide(transferfragment).show(infragment).commit();
    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_in:
                index = 0;
                if (infragment != null) {
//                    infragment.refresh();
                }
                txt_title.setText(R.string.in_storage_text);

                break;
            case R.id.re_out:
                index = 1;
                txt_title.setText(R.string.out_storage_text);
                break;
            case R.id.re_check:
                index = 2;
                txt_title.setText(R.string.check_text);
                break;
            case R.id.re_transfer:
                index = 3;
                txt_title.setText(R.string.transfer_text);
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void findViewById() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        searchButton = (ImageView) findViewById(R.id.img_right);
        searchButton.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        searchButton.setOnClickListener(searchButtonOnClickListener);
    }

    private View.OnClickListener searchButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setSearchButton(currentTabIndex);
        }
    };

    private int keyBackClickCount = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            switch (keyBackClickCount++) {
                case 0:
                    Toast.makeText(this, "再次按返回键退出", Toast.LENGTH_SHORT).show();
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            keyBackClickCount = 0;
                        }
                    }, 3000);
                    break;
                case 1:
                    AppManager.AppExit(MainActivity.this);
                    finish();
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }


    /**
     * 跳转至搜索界面*
     */
    private void setSearchButton(int mark) {

        if (mark == 0) { //跳转至入库
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        } else if (mark == 1) { //出库
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        } else if (mark == 2) { //盘点
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        } else if (mark == 3) { //转移
            Intent intent = new Intent();
            intent.putExtra("search_mark", mark);
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivityForResult(intent, 0);
        }

    }

}