package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.MatrectransAdapter;
import com.zcl.hxqh.zhongchuliang.model.Matrectrans;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by think on 2015/12/11.
 */
public class InvbalancesActivity extends BaseActivity {
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题


    private ImageView backImage; //返回


    private Button chooseBtn; //选择
    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    MatrectransAdapter matrectransAdapter;

    private Button confirm;//确定

    public String location;//源仓库

    public int mark; //移出，移入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances_list);

        geiIntentData();
        findViewById();
        initView();
    }

    /**
     * 获取界面数据
     */
    private void geiIntentData() {
        location = getIntent().getStringExtra("location");
        mark = getIntent().getIntExtra("mark", 0);
    }

    /**
     * 初始化界面控件
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);
        chooseBtn = (Button) findViewById(R.id.btn_transfer_btn);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        confirm = (Button) findViewById(R.id.confirm);
    }

    private void initView() {
//        if (mark == 1000) {
//            titleTextView.setText(R.string.invbalances_remove);
//        } else if (mark == 1001) {
//            titleTextView.setText(R.string.invbalances_move);
//        }
        titleTextView.setText(R.string.transfer_text);
        backImage.setVisibility(View.VISIBLE);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chooseBtn.setVisibility(View.VISIBLE);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        matrectransAdapter = new MatrectransAdapter(this, location);
        mRecyclerView.setAdapter(matrectransAdapter);

        chooseBtn.setOnClickListener(chooseBtnOnClickListener);
        confirm.setOnClickListener(confirmOnClickListener);
    }

    /**
     * 选择按钮*
     */
    private View.OnClickListener chooseBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();

            intent.setClass(InvbalancesActivity.this, InvbalancesListActivity.class);
            intent.putExtra("location", location);
            intent.putExtra("mark", mark);
            startActivityForResult(intent, 0);
        }
    };

    /**
     * 确定按钮
     */
    private View.OnClickListener confirmOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final ArrayList<Matrectrans> matrectranses = matrectransAdapter.mItems;
            if (isOK(matrectranses)) {
                showProgressBar(R.string.submit_process_ing);
                for (int i = 0; i < matrectranses.size(); i++) {
                    final int finalI = i;
                    new AsyncTask<String, String, String>() {
                        @Override
                        protected String doInBackground(String... strings) {
                            String result = null;
                            String data = getBaseApplication().getWsService().INV05Invtrans1(getBaseApplication().getUsername(),
                                    matrectranses.get(finalI).itemnum, matrectranses.get(finalI).receiptquantity,
                                    matrectranses.get(finalI).fromstoreloc, matrectranses.get(finalI).frombin, matrectranses.get(finalI).fromlot, matrectranses.get(finalI).tostoreloc,
                                    matrectranses.get(finalI).tobin, matrectranses.get(finalI).tolot);

                            Log.i(TAG,"data="+data);
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                result = jsonObject.getString("msg");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return result;
                        }

                        @Override
                        protected void onPostExecute(String o) {
                            super.onPostExecute(o);
                            if (o.equals("操作成功！")) {
                                Toast.makeText(InvbalancesActivity.this, o, Toast.LENGTH_SHORT).show();
                                matrectransAdapter.remove(matrectranses.get(finalI).itemnum);
                            } else {
                                Toast.makeText(InvbalancesActivity.this, matrectranses.get(finalI).itemnum +
                                        "提交失败", Toast.LENGTH_SHORT).show();

                            }
//                            if (finalI == matrectranses.size()) {
                            colseProgressBar();
//                            }
                        }
                    }.execute();
                }
            }
        }
    };

    private boolean isOK(ArrayList<Matrectrans> list) {
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).receiptquantity == null || list.get(i).receiptquantity.equals("")
                        || list.get(i).fromstoreloc == null || list.get(i).fromstoreloc.equals("")
                        || list.get(i).frombin == null || list.get(i).frombin.equals("")
                        || list.get(i).tostoreloc == null || list.get(i).tostoreloc.equals("")
                        || list.get(i).tobin == null || list.get(i).tobin.equals("")) {
                    Toast.makeText(InvbalancesActivity.this, list.get(i).itemnum + "信息未完善", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        int i = resultCode;
        switch (resultCode) {
            case 1000:
                ArrayList<Matrectrans> list = (ArrayList<Matrectrans>) data.getSerializableExtra("matrectrans");
                matrectransAdapter.adddate(list);
                break;
            case 2000:
                Matrectrans matrectrans = (Matrectrans) data.getSerializableExtra("matrectrans");
                matrectransAdapter.update(matrectrans);
                break;
        }
    }

}
