package com.zcl.hxqh.zhongchuliang.view.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 库存余量
 */
public class CInvbalancesDetailActivity extends BaseActivity {

    private static final String TAG = "CInvbalancesDetailActivity";
    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    private Button confirmBtn; //确认

    /**
     * 界面说明*
     */

    private TextView itemnumText; //物资编号
    private TextView binnumText; //货柜
    private TextView lotnumText; //批次
    private EditText curbalText; //当前余量
    private TextView invtypeText; //库存类别

    /**
     * Invbalances*
     */
    private Invbalances invbalances;

    /**
     * 库房*
     */
    private String location;


    /**
     * 进度条*
     */
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinvbalances_detail);

        initData();
        findViewById();
        initView();
    }

    private void initData() {
        invbalances = (Invbalances) getIntent().getSerializableExtra("invbalances");
        location = getIntent().getStringExtra("location");
    }


    /**
     * 初始化界面控件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);

        itemnumText = (TextView) findViewById(R.id.inbalance_itemnum_text);
        binnumText = (TextView) findViewById(R.id.invbalances_binnum_text);
        lotnumText = (TextView) findViewById(R.id.invbalances_lotnum_text);
        curbalText = (EditText) findViewById(R.id.invbalances_curbal_text);
        invtypeText = (TextView) findViewById(R.id.invbalances_invtype_text);


        confirmBtn = (Button) findViewById(R.id.invbalances_btn_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        titleTextView.setText(getString(R.string.cinvbalances_detail_title));
        backImage.setOnClickListener(backOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        if (invbalances != null) {
            itemnumText.setText(invbalances.getItemnum() == null ? "" : invbalances.getItemnum());
            binnumText.setText(invbalances.getBinnum() == null ? "" : invbalances.getBinnum());
            lotnumText.setText(invbalances.getLotnum() == null ? "" : invbalances.getLotnum());
            curbalText.setText(invbalances.getCurbal() == null ? "" : invbalances.getCurbal());
            invtypeText.setText(invbalances.getInvtype() == null ? "" : invbalances.getInvtype());
        }
        confirmBtn.setOnClickListener(confirmBtnOnClickListener);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener confirmBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            mProgressDialog = ProgressDialog.show(CInvbalancesDetailActivity.this, null,
                    "正在提交中...", true, true);
            confirmData();
        }
    };


    /**
     * 提交数据方法*
     */
    private void confirmData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String result = null;
                String data = getBaseApplication().getWsService().INV04Invadj(getBaseApplication().getUsername(), location,
                        invbalances.itemnum, invbalances.binnum, invbalances.lotnum, curbalText.getText().toString());
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    result = jsonObject.getString("msg");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressDialog.cancel();

                MessageUtils.showMiddleToast(CInvbalancesDetailActivity.this, s);
                finish();
            }
        }.execute();
    }
}
