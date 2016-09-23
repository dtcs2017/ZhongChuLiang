package com.zcl.hxqh.zhongchuliang.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;


/**
 * 库存转移详情*
 */
public class InvbalanceDetailActivity extends BaseActivity {
    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    /**
     * 界面说明*
     */

    private TextView itemnumText; //项目
    private TextView descriptionText; //描述
    private TextView in20Text; //规格型号
    private TextView orderunitText; //订购单位
    private TextView curbalText; //当前余量
    private TextView locationText; //库房
    private TextView siteidText; //地点
    private TextView binnumText; //货柜
    private TextView lotnumText; //批次

    /**Invbalances**/
    private Invbalances invbalances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalance_detail);

        initData();
        findViewById();
        initView();
    }

    private void initData() {
        invbalances= (Invbalances) getIntent().getSerializableExtra("invbalances");


    }


    /**
     * 初始化界面控件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);

        itemnumText=(TextView)findViewById(R.id.invbalance_itemnum_text);
        descriptionText=(TextView)findViewById(R.id.invbalance_description_text);
        in20Text=(TextView)findViewById(R.id.invbalance_in20_text);
        orderunitText=(TextView)findViewById(R.id.invbalance_orderunit_text);
        curbalText=(TextView)findViewById(R.id.invbalance_curbal_text);
        locationText=(TextView)findViewById(R.id.invbalance_location_text);
        siteidText=(TextView)findViewById(R.id.invbalance_siteid_text);
        binnumText=(TextView)findViewById(R.id.invbalance_binnum_text);
        lotnumText=(TextView)findViewById(R.id.invbalance_lotnum_text);

    }


    /**
     * 设置事件监听*
     */
    private void initView() {
        titleTextView.setText(getString(R.string.title_activity_invbalance_detail));
        backImage.setOnClickListener(backOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        if(invbalances!=null){
            itemnumText.setText(invbalances.getItemnum()==null?"":invbalances.getItemnum());
            descriptionText.setText(invbalances.getItemdesc()==null?"":invbalances.getItemdesc());
            in20Text.setText(invbalances.getItemin20()==null?"":invbalances.getItemin20());
            orderunitText.setText(invbalances.getItemorderunit()==null?"":invbalances.getItemorderunit());
            curbalText.setText(invbalances.getCurbal()==null?"":invbalances.getCurbal());
            locationText.setText(invbalances.getLocation()==null?"":invbalances.getLocation());
            siteidText.setText(invbalances.getSiteid()==null?"":invbalances.getSiteid());
            binnumText.setText(invbalances.getBinnum()==null?"":invbalances.getBinnum());
            lotnumText.setText(invbalances.getLotnum()==null?"":invbalances.getLotnum());
        }

    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
