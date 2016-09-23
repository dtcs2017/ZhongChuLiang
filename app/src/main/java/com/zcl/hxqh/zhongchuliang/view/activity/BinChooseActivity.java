package com.zcl.hxqh.zhongchuliang.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.BinAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by think on 2015/12/12.
 * 仓库库位号选择页面
 */
public class BinChooseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "BinChooseActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回

    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;

    BinAdapter binAdapter;

    private int page = 1;

    private String location;

    private String itemnum;

    private int mark;//1.原库位 2.目标库位

    public int requestCode;

    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bin_choose);

        initData();
        findViewById();
        initView();
    }

    /**
     * 获取上个界面的数据*
     */
    private void initData() {
        location = getIntent().getStringExtra("location");
        itemnum = getIntent().getStringExtra("itemnum");
        mark = getIntent().getIntExtra("mark",0);
        requestCode = getIntent().getIntExtra("requestCode", 0);
    }

    private void findViewById(){
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }

    private void initView(){
        titleTextView.setText(itemnum);

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLayoutManager = new LinearLayoutManager(BinChooseActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        binAdapter = new BinAdapter(this);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(false);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        getItemList();
    }

    /**
     * 获取库存原库位号信息*
     */

    private void getItemList() {
        ImManager.getDataPagingInfo(BinChooseActivity.this, ImManager.serFrombinUrl(location,itemnum, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Invbalances> items = null;
                try {
                    items = Ig_Json_Model.parseInvbalancesFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (binAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(BinChooseActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }


                    } else {
                        Log.i(TAG, "page=" + page + ",totalPages=" + totalPages + ",currentPage=" + currentPage);
                        if (page == 1) {
                            binAdapter = new BinAdapter(BinChooseActivity.this);
                            mRecyclerView.setAdapter(binAdapter);
                        }
                        if (totalPages == page) {
                            binAdapter.adddate(items);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                mSwipeLayout.setLoading(false);
                if (binAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(BinChooseActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        page++;
        getItemList();
    }

    @Override
    public void onRefresh() {
        mSwipeLayout.setRefreshing(false);
    }
}
