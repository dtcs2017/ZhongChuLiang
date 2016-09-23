package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.zhongchuliang.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Results_Activity extends BaseActivity {
    private static final String TAG="Results_Activity";
    /**标题**/
    private TextView titleText;
    /**返回按钮**/
    private ImageView backImageView;

    private TextView num;//编号
    private TextView desc;//描述
    private Button search;//查询
    private String result;
    private int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_);

        getData();
        findViewById();
        initView();
    }

    /**
     * 获取数据*
     */
    private void getData() {
        result = getIntent().getExtras().getString("result");
        mark = getIntent().getIntExtra("mark", 0);
        String s = result;
    }

    private void findViewById(){
        titleText=(TextView)findViewById(R.id.txt_title);
        backImageView=(ImageView)findViewById(R.id.img_back);
        num = (TextView) findViewById(R.id.item_num_title);
        desc = (TextView) findViewById(R.id.item_desc_title);
        search = (Button) findViewById(R.id.search_button);
    }

    private void initView(){
        titleText.setText(getString(R.string.search_result));
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(isJson(result)){
            try {
                JSONObject object = new JSONObject(result);
                if(!object.has("num")||!object.has("desc")){
                    Toast.makeText(Results_Activity.this,"查询结果无效",Toast.LENGTH_SHORT).show();
                }else {
                    num.setText(object.getString("num"));
                    desc.setText(object.getString("desc"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(Results_Activity.this,"查询结果无效",Toast.LENGTH_SHORT).show();
        }

        search.setOnClickListener(searchOnClickListener);
    }

    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    private View.OnClickListener searchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Results_Activity.this,Results_searchActivity.class);
            intent.putExtra("search_mark",mark);
            intent.putExtra("num",num.getText().toString());
            startActivity(intent);
        }
    };
}
