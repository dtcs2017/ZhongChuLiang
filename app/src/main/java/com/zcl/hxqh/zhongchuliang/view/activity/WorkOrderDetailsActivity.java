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
import com.zcl.hxqh.zhongchuliang.adapter.InvreserveAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.Invreserve;
import com.zcl.hxqh.zhongchuliang.model.WorkOrder;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 出库管理详情
 */
public class WorkOrderDetailsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {

    private static final String TAG = "WorkOrderDetailsActivity";

    private TextView titleTextView; // 标题

    private ImageView backImage; //返回按钮


    /**
     * --界面显示的textView--**
     */

    private TextView wonumTextView; //工单编号

    private TextView descriptionTextView; //描述

    private TextView onbehalfofTextView; //领用人

    private TextView statusTextView; //状态

    /**
     * WorkOrder*
     */
    private WorkOrder workOrder;


    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;


    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;


    private InvreserveAdapter invreserveAdapter;


//    private String wonum="965361";

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workorder_details_items);
        geiIntentData();
        initView();
        setEvent();
    }

    /**
     * 获取上个界面的数据*
     */
    private void geiIntentData() {
        workOrder = (WorkOrder) getIntent().getSerializableExtra("workOrder");

    }


    /**
     * 初始化界面组件
     */
    private void initView() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        backImage = (ImageView) findViewById(R.id.img_back);

        wonumTextView = (TextView) findViewById(R.id.workorder_wonum_text);
        descriptionTextView = (TextView) findViewById(R.id.workorder_desction_text);
        onbehalfofTextView = (TextView) findViewById(R.id.workorder_onbehalfof_text);
        statusTextView = (TextView) findViewById(R.id.workorder_status_text);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        invreserveAdapter = new InvreserveAdapter(WorkOrderDetailsActivity.this,workOrder.wonum);
        mRecyclerView.setAdapter(invreserveAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);

        getInvreserveList(workOrder.wonum);
//        getInvreserveList(wonum);
    }


    /**
     * 设置事件监听
     */
    private void setEvent() {
        titleTextView.setText(getString(R.string.workorder_detail_title));
        backImage.setOnClickListener(backOnClickListener);
        backImage.setVisibility(View.VISIBLE);

        if (workOrder != null) {
            wonumTextView.setText(workOrder.wonum);
            descriptionTextView.setText(workOrder.description);
            onbehalfofTextView.setText(workOrder.onbehalfof);
            statusTextView.setText(workOrder.status);
        }


    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    /**
     * 获取gInvreserve信息*
     */

    private void getInvreserveList(final String wonum) {
        ImManager.getDataPagingInfo(this, ImManager.serInvreserveUrl(wonum, "", page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Invreserve> items = null;
                try {
                    items = Ig_Json_Model.parseInvreserveFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            invreserveAdapter = new InvreserveAdapter(WorkOrderDetailsActivity.this,wonum);
                            mRecyclerView.setAdapter(invreserveAdapter);
                        }
                        if (totalPages == page) {
                            invreserveAdapter.adddate(items);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                notLinearLayout.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onLoad() {
        page = 1;
        getInvreserveList(workOrder.wonum);
    }

    @Override
    public void onRefresh() {
        page++;
        getInvreserveList(workOrder.wonum);
    }
}
