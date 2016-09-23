package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.model.Matrectrans;


/**
 * Created by think on 2015/12/12.
 * 仓库转移项详情
 */
public class MatrectransActivity extends BaseActivity {
    private static final String TAG = "MatrectransActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    public TextView itemnum;//项目
    public TextView description;//描述
    public TextView type;//型号
    public EditText receiptquantity;//数量
    public TextView curbaltotal;//仓库当前余量
    public TextView frombin;//原库位号
    public TextView fromlot;//原批次
    public RelativeLayout frombinlayout;
    private TextView tostoreloc_title;
    public TextView tostoreloc;//目标仓库/原仓库
    public RelativeLayout tostoreloclayout;
    public TextView tobin;//目标库位号
    public RelativeLayout tobinlayout;

    public TextView tolotText; //目标批次

    private ImageView tobinImageView;

    private ImageView img;

    public Button confirm;//确定

    private Matrectrans matrectrans;
    private String location;
    private int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrectrans_details);

        geiIntentData();
        findViewById();
        initView();
    }

    /**
     *获取界面数据
     */
    private void geiIntentData() {
        matrectrans = (Matrectrans) getIntent().getSerializableExtra("matrectrans");
        location = getIntent().getStringExtra("location");
        mark = getIntent().getIntExtra("mark",0);
    }

    /**
     * 初始化界面控件
     */
    private void findViewById(){
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);

        itemnum = (TextView) findViewById(R.id.matrectrans_itemnum);
        description = (TextView) findViewById(R.id.matrectrans_description);
        type = (TextView) findViewById(R.id.matrectrans_type);
        receiptquantity = (EditText) findViewById(R.id.matrectrans_receiptquantity);
        curbaltotal = (TextView) findViewById(R.id.matrectrans_curbaltotal);
        frombin = (TextView) findViewById(R.id.matrectrans_frombin);
        fromlot = (TextView) findViewById(R.id.matrectrans_fromlot);
        frombinlayout = (RelativeLayout) findViewById(R.id.frombin_linearlayout_id);
        tostoreloc_title = (TextView) findViewById(R.id.matrectrans_tostoreloc_title);
        tostoreloc = (TextView) findViewById(R.id.matrectrans_tostoreloc);
        tostoreloclayout = (RelativeLayout) findViewById(R.id.tostoreloc_linearlayout_id);
        tobin = (TextView) findViewById(R.id.matrectrans_tobin);
        tobinImageView = (ImageView) findViewById(R.id.matrectrans_tobin_choose);
        tobinlayout = (RelativeLayout) findViewById(R.id.tobin_linearlayout_id);

        tolotText=(TextView)findViewById(R.id.matrectrans_tolot);
        confirm = (Button) findViewById(R.id.confirm_button_id);

        img = (ImageView) findViewById(R.id.matrectrans_tostoreloc_img);
    }

    private void initView() {
        titleTextView.setText(R.string.matrectrans_details);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        backImage.setVisibility(View.VISIBLE);

        if(mark == 1000){
            tostoreloc.setText(matrectrans.fromstoreloc);
            matrectrans.tostoreloc = location;
        }else if(mark == 1001){
            tostoreloc_title.setText(R.string.matrectrans_fromstoreloc);
            tostoreloc.setText(matrectrans.tostoreloc);
            matrectrans.fromstoreloc = location;
            img.setVisibility(View.GONE);
        }
        itemnum.setText(matrectrans.itemnum);
        description.setText(matrectrans.description);
        type.setText(matrectrans.type);
        receiptquantity.setText(matrectrans.receiptquantity);
        curbaltotal.setText(matrectrans.curbaltotal);
        frombin.setText(matrectrans.frombin);
        fromlot.setText(matrectrans.fromlot);
        tobin.setText(matrectrans.tobin);
        confirm.setOnClickListener(confirmClicklistener);

        frombinlayout.setOnClickListener(frombinClicklistener);
        tostoreloclayout.setOnClickListener(tostorelocClicklistener);
//        tobinlayout.setOnClickListener(tobinClicklisener);
        tobinImageView.setOnClickListener(tobinClicklisener);
    }

    private View.OnClickListener frombinClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatrectransActivity.this,BinChooseActivity.class);
            intent.putExtra("location",location);
            intent.putExtra("itemnum",matrectrans.getItemnum());
            intent.putExtra("requestCode", 1);
            intent.putExtra("mark",mark);
            startActivityForResult(intent,1);
        }
    };

    private View.OnClickListener tostorelocClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mark == 1000) {
                Intent intent = new Intent(MatrectransActivity.this, StorelocChooseActivity.class);
                startActivityForResult(intent, 2);
            }
        }
    };

    private View.OnClickListener tobinClicklisener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MatrectransActivity.this,BinChooseActivity.class);
            intent.putExtra("location",tostoreloc.getText());
            intent.putExtra("itemnum",matrectrans.getItemnum());
            intent.putExtra("requestCode", 3);
            intent.putExtra("mark",mark);
            startActivityForResult(intent,3);
        }
    };

    private View.OnClickListener confirmClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            matrectrans.receiptquantity = receiptquantity.getText().toString();
            if(mark == 1000){
                matrectrans.fromstoreloc = location;
            }else if(mark == 1001){
                matrectrans.tostoreloc = location;
            }
            if(receiptquantity.getText().toString().equals("")||receiptquantity.getText().toString()==null){
                Toast.makeText(MatrectransActivity.this,"请输入数量",Toast.LENGTH_SHORT).show();
                return;
            }else if(Integer.parseInt(receiptquantity.getText().toString())>Integer.parseInt(curbaltotal.getText().toString())){
                Toast.makeText(MatrectransActivity.this,"数量必须小于等于当前余量",Toast.LENGTH_SHORT).show();
                return;
            }
            if (frombin.getText().toString().equals("")||tostoreloc.getText().toString().equals("")
                    ||tobin.getText().toString().equals("")){
                Toast.makeText(MatrectransActivity.this,"请填写完整信息",Toast.LENGTH_SHORT).show();
                return;
            }else {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("matrectrans",matrectrans);
                intent.putExtras(bundle);
                setResult(2000,intent);
                finish();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 1:
                String s = data.getStringExtra("binnum");
                frombin.setText(s);
                matrectrans.frombin = s;
                break;
            case 2:
                String s2 = data.getStringExtra("locations");
                tostoreloc.setText(s2);
                matrectrans.tostoreloc = s2;
                break;
            case 3:
                String s3 = data.getStringExtra("binnum");
                String tolot = data.getStringExtra("tolot");
                tobin.setText(s3);
                tolotText.setText(tolot);
                matrectrans.tobin = s3;
                matrectrans.tolot = tolot;
                break;
        }
    }
}
