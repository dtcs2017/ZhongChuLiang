package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Locations;


public class LocationsDetailActivity extends BaseActivity {


    private static final int REMOVED_MARK=1000; //移出
    private static final int MOVED_MARK=1001; //移入

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回


    /**界面值ʾ**/
    private TextView locationText; //位置

    private TextView desctionText; //描述

    private TextView siteidText;  //地点


    private Button removedBtn; //转移
//    private Button moveBtn; //移入


    /**Locations**/
    private Locations locations;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_detail);

        initData();

        findViewById();

        initView();
    }


    /**获取上个界面**/
    private void initData() {
        locations= (Locations) getIntent().getSerializableExtra("locations");
    }


    /**初始化界面控件**/
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);


        locationText=(TextView)findViewById(R.id.locations_location_text);
        desctionText=(TextView)findViewById(R.id.locations_description_text);
        siteidText=(TextView)findViewById(R.id.location_siteid_text);


        removedBtn=(Button)findViewById(R.id.locations_removed_btn_id);
//        moveBtn=(Button)findViewById(R.id.locations_move_btn_id);
    }

    /**设置事件监听**/
    private void initView() {
        titleTextView.setText(getString(R.string.locations_title_text));
        backImage.setOnClickListener(backOnClickListener);
        backImage.setVisibility(View.VISIBLE);
        if(locations!=null){
            locationText.setText(locations.getLocation()==null?"":locations.getLocation());
            desctionText.setText(locations.getDescription()==null?"":locations.getDescription());
            siteidText.setText(locations.getSiteid()==null?"":locations.getSiteid());
        }


        removedBtn.setOnClickListener(new IntentClickListener(REMOVED_MARK));
//        moveBtn.setOnClickListener(new IntentClickListener(MOVED_MARK));
    }



    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    private class IntentClickListener implements View.OnClickListener{
        private int mark;
        private IntentClickListener(int mark){
            this.mark = mark;
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(LocationsDetailActivity.this,InvbalancesActivity.class);
            intent.putExtra("location",locations.getLocation());
            intent.putExtra("mark",mark);
            startActivityForResult(intent,0);
        }
    }

}
