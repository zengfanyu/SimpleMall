package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.zfy.simplemall.Decoration.DividerItemDecoration;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.HWAdapter;
import com.zfy.simplemall.bean.Page;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 热卖 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class HotFragment extends BaseFragment {

    private View mHotContentView;
    private int curPage = 1; //此处两个字段是要写进url中的，所以不能使用m
    private int pageSize = 10;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private List<Wares> mWaresList;
    private int mCurrentState = Constant.STATE_NORMAL;
    private HWAdapter mAdapter;
    private int mTotalPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHotContentView = inflater.inflate(R.layout.fragment_hot, container, false);
        initViews();
        return mHotContentView;
    }

    @Override
    public void initViews() {
        initView();
        initRefreshLayout();
        initData();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mHotContentView.findViewById(R.id.id_recyclerView);
        mRefreshLayout = (MaterialRefreshLayout) mHotContentView.findViewById(R.id.id_refresh);
    }

    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if (curPage <= mTotalPage) {
                    loadMoreData();
                } else {
                    ToastUtils.showToast(getContext(), "No more,sorry!");
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    /**
     * 刷新数据的方法
     */
    private void refreshData() {
        curPage = 1;
        mCurrentState = Constant.STATE_REFRESH;
        initData();
    }

    /**
     * 加载更多数据的方法
     */
    private void loadMoreData() {
        curPage = ++curPage;
        mCurrentState = Constant.STATE_MORE;
        initData();
    }

    private void initData() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("curPage", curPage + "");
        paramsMap.put("pageSize", pageSize + "");

        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_HOT_WARES,
                new RequestParams(paramsMap)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Page page = (Page) responseObj;
                mWaresList = page.getList();
                curPage = page.getCurrentPage();
                pageSize = page.getPageSize();
                mTotalPage = page.getTotalPage();
                //拿到数据后，就用这个数据去初始化RecyclerView
                initRecyclerView();
            }

            @Override
            public void onFailure(Object reasonObj) {
                OkHttpException e = (OkHttpException) reasonObj;
                ToastUtils.showToast(getContext(), e.getMessage());
            }
        }, Page.class));


    }

    /**
     * 根据当前状态去设置RecyclerView的方法
     */
    private void initRecyclerView() {
        switch (mCurrentState) {
            case Constant.STATE_NORMAL: //一般状态，也就是第一次展示数据的时候，需要new一个adapter
                mAdapter = new HWAdapter(mWaresList, getContext(), R.layout.hot_wares_item);
                mAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        ToastUtils.showToast(getContext(), mWaresList.get(position).getName());
                    }
                });
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                break;
            case Constant.STATE_REFRESH://刷新数据的时候就不需要重新new一个adapter了，只需要刷新数据即可
                mAdapter.clearData();
                mAdapter.addData(mWaresList);
                mRefreshLayout.finishRefresh();
            case Constant.STATE_MORE://添加更多的数据
                mAdapter.addData(mAdapter.getDatas().size(), mWaresList);
                mRefreshLayout.finishRefreshLoadMore();
                break;
            default:
                break;

        }

    }
}
