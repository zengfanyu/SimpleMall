package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.WareDetailActivity;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.Decoration.DividerItemDecoration;
import com.zfy.simplemall.adapter.HWAdapter;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.listener.onPageListener;
import com.zfy.simplemall.utils.PageUtils;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import java.util.List;

/**
 * 热卖 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class HotFragment extends BaseFragment implements onPageListener {

    protected View mHotContentView;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private List<Wares> mWaresList;
    private HWAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mHotContentView == null) {
            mHotContentView = inflater.inflate(R.layout.fragment_hot, container, false);
        }
        ViewGroup parent = (ViewGroup) mHotContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mHotContentView);
        }
        initViews();
        return mHotContentView;
    }

    @Override
    public void initViews() {
        initView();


        PageUtils.Builder()
                .setUrl(Constant.URL_HOT_WARES)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setListener(this)
                .build(getContext().getApplicationContext());
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mHotContentView.findViewById(R.id.id_recyclerView);
        mRefreshLayout = (MaterialRefreshLayout) mHotContentView.findViewById(R.id.id_refresh);
    }

    @Override
    public void load(List data, int pageSize, int curPage) {
        mWaresList = data;
        mAdapter = new HWAdapter(data, getContext(), R.layout.hot_wares_item);
        mAdapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Wares wares = mWaresList.get(position);
                ToastUtils.showToast(getContext(), wares.getName());
                Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                intent.putExtra(Constant.EXTRA_WARE_NAME, wares);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
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
    public void onDestroyView() {
        super.onDestroyView();
        mHotContentView = null;
    }
}
