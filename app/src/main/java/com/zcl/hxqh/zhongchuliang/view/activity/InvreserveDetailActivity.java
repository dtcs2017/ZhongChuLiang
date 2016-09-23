package com.zcl.hxqh.zhongchuliang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invreserve;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 出库
 */
public class InvreserveDetailActivity extends BaseActivity {

    private static final String TAG = "InvreserveDetailActivity";
    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    private Button issueBtn; //发放

    private Button withdrawingBtn; //退货

    /**
     * 界面说明*
     */

    private TextView itemnumText; //物资编号
    private TextView desctionText; //描述
    private EditText qtyText; //当前余量
    private TextView locationText; //库房
    private TextView binnumText; //货柜
    private TextView lotnumText; //批次
    private ImageView chooseImageView; //选择

    /**
     * Invreserve*
     */
    private Invreserve invreserve;

    /**
     * 工单
     */
    private String wonum;


    /**
     * 进度条*
     */
    private ProgressDialog mProgressDialog;

    private String negative; //退库时的数量

    private int mark=0; //判断是发放还是退库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invreserve_detail);

        initData();
        findViewById();
        initView();
    }

    private void initData() {
        invreserve = (Invreserve) getIntent().getSerializableExtra("invreserve");
        wonum = getIntent().getStringExtra("wonum");


    }


    /**
     * 初始化界面控件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);

        itemnumText = (TextView) findViewById(R.id.invreserve_itemnum_text);
        desctionText = (TextView) findViewById(R.id.inbalance_desction_text);
        qtyText = (EditText) findViewById(R.id.invreserve_qty_text);
        locationText = (TextView) findViewById(R.id.invreserve_location_text);
        binnumText = (EditText) findViewById(R.id.invreserve_binnum_text);
        lotnumText = (TextView) findViewById(R.id.invreserve_lotnum_text);
        chooseImageView = (ImageView) findViewById(R.id.invreserve_binnum_choose);

        issueBtn = (Button) findViewById(R.id.invreserve_issue_btn_id);
        withdrawingBtn = (Button) findViewById(R.id.invreserve_withdrawing_btn_id);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        titleTextView.setText("发放/退库");
        backImage.setOnClickListener(backOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        if (invreserve != null) {
            itemnumText.setText(invreserve.getItemnum() == null ? "" : invreserve.getItemnum());
            desctionText.setText(invreserve.getDescription() == null ? "" : invreserve.getDescription());
            qtyText.setText(invreserve.getReservedqty() == null ? "" : invreserve.getReservedqty());
            locationText.setText(invreserve.getLocation() == null ? "" : invreserve.getLocation());
            binnumText.setText(invreserve.getBinnum() == null ? "" : invreserve.getBinnum());
        }
        chooseImageView.setOnClickListener(chooseImageViewOnClickListener);
        issueBtn.setOnClickListener(confirmBtnOnClickListener);
        withdrawingBtn.setOnClickListener(confirmBtnOnClickListener);
    }


    private View.OnClickListener chooseImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(InvreserveDetailActivity.this, BinChooseActivity.class);
            intent.putExtra("location", invreserve.getLocation());
            intent.putExtra("itemnum", invreserve.getItemnum());
            intent.putExtra("requestCode", 3);
            startActivityForResult(intent, 3);
        }
    };


    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private View.OnClickListener confirmBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.invreserve_issue_btn_id:
                    negative= qtyText.getText().toString();
                    mark=0;
                    break;
                case R.id.invreserve_withdrawing_btn_id:
                    mark=1;
                    negative= qtyText.getText().toString();
                    break;
            }

            if ( Integer.parseInt(negative)>= Integer.parseInt(invreserve.reservedqty)) {
                MessageUtils.showMiddleToast(InvreserveDetailActivity.this, "数量必须小于等于当前余量");
            } else {
                mProgressDialog = ProgressDialog.show(InvreserveDetailActivity.this, null,
                        "正在提交中...", true, true);
                confirmData();
            }
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
                String data=null;
                if(mark==0) {
                     data = getBaseApplication().getWsService().INV03Issue(getBaseApplication().getUsername(), wonum,
                            invreserve.itemnum, qtyText.getText().toString(), invreserve.location, binnumText.getText().toString(), lotnumText.getText().toString());
                }else{
                    data = getBaseApplication().getWsService().INV03Issue(getBaseApplication().getUsername(), wonum,
                            invreserve.itemnum, "-"+qtyText.getText().toString(), invreserve.location, binnumText.getText().toString(), lotnumText.getText().toString());
                }
                Log.i(TAG, "data=" + data);
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

                MessageUtils.showMiddleToast(InvreserveDetailActivity.this, s);
                finish();
            }
        }.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 3:
                String s3 = data.getStringExtra("binnum");
                String lotnum = data.getStringExtra("tolot");
                binnumText.setText(s3);
                lotnumText.setText(lotnum);
                break;
        }
    }
}
