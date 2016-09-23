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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zcl.hxqh.zhongchuliang.R;
import com.zcl.hxqh.zhongchuliang.adapter.InvbalancesAdapter;
import com.zcl.hxqh.zhongchuliang.api.HttpRequestHandler;
import com.zcl.hxqh.zhongchuliang.api.ImManager;
import com.zcl.hxqh.zhongchuliang.api.ig_json.Ig_Json_Model;
import com.zcl.hxqh.zhongchuliang.bean.Results;
import com.zcl.hxqh.zhongchuliang.model.Invbalances;
import com.zcl.hxqh.zhongchuliang.model.Matrectrans;
import com.zcl.hxqh.zhongchuliang.until.MessageUtils;
import com.zcl.hxqh.zhongchuliang.view.widght.SwipeRefreshLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 库存项目*
 */
public class InvbalancesListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnLoadListener {
    private static final String TAG = "InvbalancesActivity";

    private TextView titleTextView; // 标题

    private RelativeLayout titlelayout;//标题布局
    private RelativeLayout title_searchlayout;//标题搜索布局

    private ImageView backImage; //返回

    private ImageView searchimg; //搜索

    private EditText editText; // 搜索

    /**
     * 搜索值*
     */
    private String search="";

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

    InvbalancesAdapter invbalancesAdapter;

    private int page = 1;

    private String location;

    private int mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invbalances);

        initData();

        findViewById();

        initView();
    }


    /**
     * 获取上个界面的数据*
     */
    private void initData() {
        location = getIntent().getStringExtra("location");
        mark = getIntent().getIntExtra("mark", 0);
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

        chooseBtn = (Button) findViewById(R.id.invbalances_btn_id);


        mRecyclerView = (RecyclerView) findViewById(R.id.list_topics);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        notLinearLayout = (LinearLayout) findViewById(R.id.have_not_data_id);
    }


    /**
     * 设置事件监听*
     */
    private void initView() {

        titleTextView.setText(getResources().getString(R.string.title_activity_invbalances_list));
        backImage.setOnClickListener(backImageOnClickListener);
        searchimg.setVisibility(View.VISIBLE);
        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_searchlayout.setVisibility(View.VISIBLE);
            }
        });
        editText.setOnEditorActionListener(editTextOnEditorActionListener);


        chooseBtn.setOnClickListener(chooseBtnOnClickListener);

        mLayoutManager = new LinearLayoutManager(InvbalancesListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        invbalancesAdapter = new InvbalancesAdapter(InvbalancesListActivity.this);
        mRecyclerView.setAdapter(invbalancesAdapter);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeLayout.setColor(R.color.holo_blue_bright,
                R.color.holo_green_light,
                R.color.holo_orange_light,
                R.color.holo_red_light);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setLoading(true);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setOnLoadListener(this);


        getItemList(location, "");
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
                                InvbalancesListActivity.this.getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                search = editText.getText().toString();
                mSwipeLayout.setRefreshing(true);
                mSwipeLayout.setLoading(true);
                invbalancesAdapter.removeAllData();
                notLinearLayout.setVisibility(View.GONE);
                getItemList(location, search);
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


    private View.OnClickListener chooseBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            HashMap<Integer, Invbalances> list = invbalancesAdapter.checkedlist;


            ArrayList<Matrectrans> mList = new ArrayList<Matrectrans>();
            Matrectrans matrectrans = null;
            Iterator iter = list.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Invbalances invbalances = (Invbalances) entry.getValue();
                matrectrans = new Matrectrans();
                matrectrans.setItemnum(invbalances.itemnum); //项目
                matrectrans.setDescription(invbalances.itemdesc); //描述
                matrectrans.setType(invbalances.itemin20); //型号
                matrectrans.setCurbaltotal(invbalances.curbal); //余量
                matrectrans.setFrombin(invbalances.binnum); //货柜
                matrectrans.setLinecost(invbalances.unitcost); //行成本
                matrectrans.setFromlot(invbalances.lotnum); //原批次
                if (mark == 1000) {
                    matrectrans.setFromstoreloc(location);
                } else if (mark == 1001) {
                    matrectrans.setTostoreloc(location);
                }
                mList.add(matrectrans);
            }
            Intent intent = getIntent();
            intent.putExtra("matrectrans", mList);
            setResult(1000, intent);
            finish();
        }
    };


    /**
     * 获取库存项目信息*
     */

    private void getItemList(String location, String seach) {
        ImManager.getDataPagingInfo(InvbalancesListActivity.this, ImManager.serInvbalancesUrl(location, seach, page, 20), new HttpRequestHandler<Results>() {
            @Override
            public void onSuccess(Results results) {
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
                        invbalancesAdapter.adddate(items);
                        if (invbalancesAdapter.getItemCount() != 0) {
                            MessageUtils.showMiddleToast(InvbalancesListActivity.this, getString(R.string.loading_data_fail));
                        } else {
                            notLinearLayout.setVisibility(View.VISIBLE);
                        }


                    } else {
                            invbalancesAdapter.adddate(items);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(String error) {
                mSwipeLayout.setRefreshing(false);
                mSwipeLayout.setLoading(false);
                if (invbalancesAdapter.getItemCount() != 0) {
                    MessageUtils.showMiddleToast(InvbalancesListActivity.this, getString(R.string.loading_data_fail));
                } else {
                    notLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onLoad() {
        page++;
        Log.i(TAG,"location="+location+",search="+search);

        getItemList(location, search);
    }

    @Override
    public void onRefresh() {
        page = 1;
        getItemList(location, search);
        mSwipeLayout.setRefreshing(false);
    }
}
