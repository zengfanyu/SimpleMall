package com.zfy.simplemall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.HWAdapter;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.listener.onPageListener;
import com.zfy.simplemall.listener.onToolbarLeftButtonClickListener;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.utils.PageUtils;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.widget.SearchToolBar;

import java.util.List;

/**
 * Created by ZFY on 2017/05/04.
 *
 * @function:
 */

public class WaresListActivity extends AppCompatActivity implements onPageListener, TabLayout.OnTabSelectedListener {

    private TabLayout mTabLayout;
    private MaterialRefreshLayout mRefreshLayout;
    private String mCampaignId;
    private String mOrderBy = "0";
    private TextView mTvSummary;
    private String mTitleName;
    private RecyclerView mRecyclerView;
    private List<Wares> mWaresList;
    private HWAdapter mAdapter;
    private PageUtils.Builder mBuilder;
    private PageUtils mPageUtils;
    private SearchToolBar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wares_list);
        getIntentDate();
        initViews();
        initTab();
        getDate();
    }

    public void getIntentDate() {
        mCampaignId = getIntent().getIntExtra(Constant.EXTRA_CAMPAIGN_ID, 0) + "";
        mTitleName = getIntent().getStringExtra(Constant.EXTRA_CAMPAIGN_NAME);
    }

    private void initViews() {
        initToolBar();
        mTabLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mTvSummary = (TextView) findViewById(R.id.id_summary_tv);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler_view);
        mRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.id_refresh_layout);
    }

    private void initToolBar() {
        mToolBar = (SearchToolBar) findViewById(R.id.id_search_tool_bar);
        mToolBar.setTitle(mTitleName);
        //左边按钮返回上一个Activity
        mToolBar.setLeftButtonOnClickListener(new onToolbarLeftButtonClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        //右边按钮监听当前的布局类型
        mToolBar.setRightButtonOnClickListener(new onToolbarRightButtonClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                if (tag == Constant.TYPE_LIST) {
                    mToolBar.setRightButtonIcon(R.drawable.icon_grid_32);
                    mRecyclerView.setLayoutManager(new GridLayoutManager(WaresListActivity.this, 2));
                    mAdapter.resetLayout(R.layout.gird_wares_item);
                    view.setTag(Constant.TYPE_GIRD);
                } else if (tag == Constant.TYPE_GIRD) {
                    mToolBar.setRightButtonIcon(R.drawable.icon_list_32);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(WaresListActivity.this));
                    mAdapter.resetLayout(R.layout.hot_wares_item);
                    view.setTag(Constant.TYPE_LIST);
                }

            }
        });
        mToolBar.getRightButton().setTag(Constant.TYPE_LIST);
    }

    private void initTab() {
        TabLayout.Tab tab1 = mTabLayout.newTab();
        tab1.setText("默认");
        tab1.setTag(Constant.TAB_TYPE_DEFAULT);
        mTabLayout.addTab(tab1);

        TabLayout.Tab tab2 = mTabLayout.newTab();
        tab2.setText("销量");
        tab2.setTag(Constant.TAB_TYPE_SALES);
        mTabLayout.addTab(tab2);

        TabLayout.Tab tab3 = mTabLayout.newTab();
        tab3.setText("价格");
        tab3.setTag(Constant.TAB_TYPE_PRICE);
        mTabLayout.addTab(tab3);

        mTabLayout.setOnTabSelectedListener(this);
    }

    private void getDate() {
        mBuilder = PageUtils.Builder()
                .setUrl(Constant.URL_WARES_CAMPAIGN_LIST)
                .setLoadMore(true)
                .setRefreshLayout(mRefreshLayout)
                .addParam("campaignId", mCampaignId)
                .addParam("orderBy", mOrderBy)
                .setListener(this);

        mPageUtils = mBuilder.build(getApplicationContext());

    }

    @Override
    public void load(List data, int pageSize, int curPage) {
        mWaresList = data;
        mAdapter = new HWAdapter(data, getApplicationContext(), R.layout.hot_wares_item);
        mAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToastUtils.showToast(getApplicationContext(), mWaresList.get(position).getName());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void refresh(List data, int pageSize, int curPage) {
        mWaresList = data;
        mAdapter.clearData();
        mAdapter.addData(mWaresList);
    }

    @Override
    public void loadMore(List data, int pageSize, int curPage) {
        mWaresList = data;
        mAdapter.addData(mAdapter.getDatas().size(), mWaresList);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int tag = (int) tab.getTag();
        mBuilder.addParam("orderBy", tag + "");
        mPageUtils.updateParamsAndRequest();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
