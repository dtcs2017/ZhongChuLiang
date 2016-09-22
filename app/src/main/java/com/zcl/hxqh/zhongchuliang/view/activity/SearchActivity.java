package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.InvAdapter;
import com.zcl.hxqh.zhongchuliang.adapter.LocationsAdapter;
import com.zcl.hxqh.zhongchuliang.adapter.PoAdapter;
import com.zcl.hxqh.zhongchuliang.adapter.WorkOrderAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.Inventory;
import com.zcl.hxqh.zhongchuliang.model.Locations;
import com.zcl.hxqh.zhongchuliang.model.Po;
import com.zcl.hxqh.zhongchuliang.model.WorkOrder;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "SearchActivity";
    private static final int PO_MARK = 0; //入库管理
    private static final int WORKORDER_MARK = 1; //出库管理
    private static final int CHECK_MARK = 2; //库存盘点
    private static final int LOCATION_MARK = 3; //库存转移

    private EditText editText; // 搜索

    private ImageView backImage; //返回按钮

    private ImageView codeImage; //二维码扫描


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



    /**
     * 入库管理
     */
    PoAdapter poAdapter;

    /**
     * 出库管理
     */
    WorkOrderAdapter workOrderAdapter;

    /**
     * 盘点
     */

    InvAdapter invAdapter;


    /**
     *转移
     */


    LocationsAdapter locationsAdapter;




    /**
     * 搜索标识*
     */
    private int search_mark;
    /**
     * 搜索值*
     */
    private String search;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getData();
        finViewById();
        initView();
    }


    /**
     * 初始化界面控件*
     */
    private void finViewById() {
        editText = (EditText) findViewById(R.id.search_edittext_id);
        backImage = (ImageView) findViewById(R.id.back_image_id);
        codeImage = (ImageView) findViewById(R.id.menu_imageview_id);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mLayoutManager = new LinearLayoutManager(SearchActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (search_mark == PO_MARK) {//入库管理
            poAdapter = new PoAdapter(SearchActivity.this);
            mRecyclerView.setAdapter(poAdapter);
            codeImage.setVisibility(View.VISIBLE);
        } else if (search_mark == WORKORDER_MARK) {//出库管理
            workOrderAdapter = new WorkOrderAdapter(SearchActivity.this);
            mRecyclerView.setAdapter(workOrderAdapter);
            codeImage.setVisibility(View.VISIBLE);
        }  else if (search_mark == CHECK_MARK) {//库存盘点
            invAdapter = new InvAdapter(SearchActivity.this, 1);
            mRecyclerView.setAdapter(invAdapter);
            codeImage.setVisibility(View.VISIBLE);
        }  else if (search_mark == LOCATION_MARK) {//库存转移
            locationsAdapter = new LocationsAdapter(SearchActivity.this, 0);
            mRecyclerView.setAdapter(locationsAdapter);
            codeImage.setVisibility(View.VISIBLE);
        }
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(false);
        mSwipeLayout.setLoading(false);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);

    }

    /**
     * 设置事件监听*
     */
    private void initView() {
        backImage.setOnClickListener(backOnClickListener);
        editText.setOnEditorActionListener(editTextOnEditorActionListener);
        codeImage.setOnClickListener(codeImageOnClickListener);


    }


    /**
     * 二维码扫描*
     */
    private View.OnClickListener codeImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SearchActivity.this, MipcaActivityCapture.class);
            intent.putExtra("mark",search_mark);
            startActivityForResult(intent, 0);
        }
    };


    /**
     * 获取初始化数据*
     */
    private void getData() {
        search_mark = getIntent().getExtras().getInt("search_mark");
        Log.i(TAG, "search_mark=" + search_mark);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


    /**
     * 软键盘*
     */
    private TextView.OnEditorActionListener editTextOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                // 先隐藏键盘
                ((InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(
                                SearchActivity.this.getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                search = editText.getText().toString();
                mSwipeLayout.setRefreshing(true);
                mSwipeLayout.setLoading(true);
                notLinearLayout.setVisibility(View.GONE);
                 if (search_mark == PO_MARK) {//入库管理
                    getPoList(search);
                }else if (search_mark == WORKORDER_MARK) {//出库管理
                     getWorkorderList(search);
                 }else if (search_mark == CHECK_MARK) { //库存盘点
                    getInvList(search,0);
                } else if (search_mark == LOCATION_MARK) { //库存转移
                    getLocationsList(search);
                }
                return true;

            }
            return false;
        }
    };



    /**
     * 获取入库管理*
     */

    private void getPoList(String search) {
        ImManager.getDataPagingInfo(SearchActivity.this, ImManager.setPoUrl(search, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Po> items = null;
                try {
                    items = Ig_Json_Model.parsePoFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            poAdapter = new PoAdapter(SearchActivity.this);
                            mRecyclerView.setAdapter(poAdapter);
                        }
                        if (page == totalPages) {
                            poAdapter.adddate(items);
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

    /**
     * 获取出库管理信息*
     */

    private void getWorkorderList(String search) {
        ImManager.getDataPagingInfo(SearchActivity.this, ImManager.serWorkorderUrl(search, page, 20), new HttpRequestHandler<Results>() {
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
                            workOrderAdapter = new WorkOrderAdapter(SearchActivity.this);
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


    /**
     * 库存使用情况*
     */
    private void getInvList(String search, final int mark) {
        ImManager.getDataPagingInfo(SearchActivity.this, ImManager.serInventoryUrl(search,page,20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {

                Log.i(TAG,"results="+results.getResultlist()+"totalPages="+totalPages);
                ArrayList<Inventory> items = null;
                try {
                    items = Ig_Json_Model.parseInventoryFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (invAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(SearchActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }
                    } else {

                        if (page == 1) {
                            invAdapter = new InvAdapter(SearchActivity.this, mark);
                            mRecyclerView.setAdapter(invAdapter);
                        }
                        invAdapter.adddate(items);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                if (invAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(SearchActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }






    /**
     * 获取库存转移位置信息*
     */

    private void getLocationsList(String search) {
        ImManager.getDataPagingInfo(SearchActivity.this, ImManager.serLocationsUrl(search, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {
                ArrayList<Locations> items = null;
                try {
                    items = Ig_Json_Model.parseLocationsFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        notLinearLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (page == 1) {
                            locationsAdapter = new LocationsAdapter(SearchActivity.this, 0);
                            mRecyclerView.setAdapter(locationsAdapter);
                        }
                        if (totalPages == page) {
                            locationsAdapter.adddate(items);
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
        page++;
         if (search_mark == PO_MARK) {//入库管理
            getPoList(search);
        } else if (search_mark == WORKORDER_MARK) {//出库管理
            getWorkorderList(search);
        } else if (search_mark == CHECK_MARK) { //库存盘点
            getInvList(search, 1);
        } else if (search_mark == LOCATION_MARK) { //库存转移
            getLocationsList(search);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
         if (search_mark == PO_MARK) {//入库管理
            getPoList(search);
        } else if (search_mark == WORKORDER_MARK) {//出库管理
            getWorkorderList(search);
        } else if (search_mark == CHECK_MARK) { //库存盘点
            getInvList(search, 1);
        }  else if (search_mark == LOCATION_MARK) { //库存转移
            getLocationsList(search);
        }
    }
}
