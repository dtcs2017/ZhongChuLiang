package com.zcl.hxqh.zhongchuliang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.WorkOrderAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.WorkOrder;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 出库管理
 */
public class OutFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener{
    private static final String TAG = "OutFragment";
    private static final int RESULT_ADD_TOPIC = 100;
    /**
     * RecyclerView*
     */
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;

    SwipeRefreshLayout mSwipeLayout;

    private int page = 1;

    /**
     * 暂无数据*
     */
    LinearLayout notLinearLayout;

    WorkOrderAdapter workOrderAdapter;

    private static final int mark =1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container,
                false);

        findByIdView(view);


        return view;
    }

    /**
     * 初始化界面组件*
     */
    private void findByIdView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        workOrderAdapter = new WorkOrderAdapter(getActivity());
        mRecyclerView.setAdapter(workOrderAdapter);
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);

        notLinearLayout = (LinearLayout) view.findViewById(R.id.have_not_data_id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        mSwipeLayout.setRefreshing(true);
        getItemList("");
    }



    /**
     * 获取出库管理信息*
     */

    private void getItemList(String search) {
        ImManager.getDataPagingInfo(getActivity(), ImManager.serWorkorderUrl(search,page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<WorkOrder> items = null;
                try {
                    items = Ig_Json_Model.parseWorkOrderFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            workOrderAdapter = new WorkOrderAdapter(getActivity());
                            mRecyclerView.setAdapter(workOrderAdapter);
                        }
                        if (totalPages == page) {
                            workOrderAdapter.adddate(items);
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

    //下拉刷新触发事件
    @Override
    public void onRefresh() {
        page = 1;
        getItemList("");
    }

    //上拉加载
    @Override
    public void onLoad() {
        page++;
        getItemList("");
    }
}
