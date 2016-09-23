package com.zcl.hxqh.zhongchuliang.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.CInvbalancesAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.model.Inventory;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 库存余量*
 */
public class CInvbalancesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "CInvbalancesActivity";

    private TextView titleTextView; // 标题

    private RelativeLayout titlelayout;//标题布局
    private RelativeLayout title_searchlayout;//标题搜索布局

    private ImageView backImage; //返回

    private ImageView searchimg; //搜索

    private EditText editText; // 搜索

    /**
     * 搜索值*
     */
    private String search;

    private Button chooseBtn; //选择
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

    CInvbalancesAdapter cInvbalancesAdapter;

    private int page = 1;

    /**
     * Inventory*
     */
    private Inventory inventory;

    private int mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinvbalances);

        initData();

        findViewById();

        initView();
    }


    /**
     * 获取上个界面的数据*
     */
    private void initData() {

        inventory = (Inventory) getIntent().getSerializableExtra("inventory");
    }


    /**
     * 初始化界面组件*
     */
    private void findViewById() {
        titleTextView = (TextView) findViewById(R.id.txt_title);
        titlelayout = (RelativeLayout) findViewById(R.id.title_contains);
        title_searchlayout = (RelativeLayout) findViewById(R.id.title_search_layout);
        editText = (EditText) findViewById(R.id.search_edittext_id);
        backImage = (ImageView) findViewById(R.id.img_back);
        searchimg = (ImageView) findViewById(R.id.img_right);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        titleTextView.setText(inventory.location);
        backImage.setOnClickListener(backImageOnClickListener);
        backImage.setVisibility(View.VISIBLE);
        searchimg.setVisibility(View.VISIBLE);
        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_searchlayout.setVisibility(View.VISIBLE);
            }
        });
        editText.setHint(getString(R.string.invbalances_pici_hint));
        editText.setOnEditorActionListener(editTextOnEditorActionListener);


        mLayoutManager = new LinearLayoutManager(CInvbalancesActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        cInvbalancesAdapter = new CInvbalancesAdapter(CInvbalancesActivity.this,inventory.location);
        mRecyclerView.setAdapter(cInvbalancesAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(false);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        getItemList(inventory.itemnum, "");
    }


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
                                CInvbalancesActivity.this.getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                search = editText.getText().toString();
                mSwipeLayout.setRefreshing(true);
                mSwipeLayout.setLoading(true);
                notLinearLayout.setVisibility(View.GONE);
                getItemList(inventory.itemnum, search);
                return true;

            }
            return false;
        }
    };

    private View.OnClickListener backImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (title_searchlayout.getVisibility() == View.VISIBLE) {
                title_searchlayout.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
    };


    /**
     * 获取库存项目信息*
     */

    private void getItemList(String itemnum, String seach) {
        ImManager.getDataPagingInfo(CInvbalancesActivity.this, ImManager.sercInvbalancesUrl(itemnum, seach, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
                Log.i(TAG, "data=" + results);
            }

            @Override
            public void onSuccess(Results results, int totalPages, int currentPage) {

                Log.i(TAG,"results="+results.getResultlist());
                ArrayList<Invbalances> items = null;
                try {
                    items = Ig_Json_Model.parseInvbalancesFromString(results.getResultlist());
                    mSwipeLayout.setRefreshing(false);
                    mSwipeLayout.setLoading(false);
                    if (items == null || items.isEmpty()) {
                        if (cInvbalancesAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(CInvbalancesActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }


                    } else {
                        if (page == 1) {
                            cInvbalancesAdapter = new CInvbalancesAdapter(CInvbalancesActivity.this,inventory.location);
                            mRecyclerView.setAdapter(cInvbalancesAdapter);
                        }
                        if (totalPages == page) {
                            cInvbalancesAdapter.adddate(items);
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
                if (cInvbalancesAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(CInvbalancesActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        page++;
        getItemList(inventory.itemnum, search);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getItemList(inventory.itemnum, search);
        mSwipeLayout.setRefreshing(false);
    }
}
