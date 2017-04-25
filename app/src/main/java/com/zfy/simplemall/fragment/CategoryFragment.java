package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.CategoryListAdapter;
import com.zfy.simplemall.adapter.Decoration.DividerGridItemDecoration;
import com.zfy.simplemall.adapter.Decoration.DividerItemDecoration;
import com.zfy.simplemall.adapter.WaresAdapter;
import com.zfy.simplemall.bean.BannerBean;
import com.zfy.simplemall.bean.CategoryBean;
import com.zfy.simplemall.bean.Page;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zfy.simplemall.config.Constant.STATE_MORE;
import static com.zfy.simplemall.config.Constant.STATE_NORMAL;
import static com.zfy.simplemall.config.Constant.STATE_REFRESH;

/**
 * 分类 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class CategoryFragment extends BaseFragment {

    private View mCategoryContentView;
    private List<BannerBean> mBanners;
    private List<CategoryBean> mCategories;
    private SliderLayout mSliderLayout;
    private long categoryId = 1;
    private int curPage = 1;
    private int pageSize = 10;
    private int mTotalPage;
    private List<Wares> mWaresList;


    private int mCurrentState = STATE_NORMAL;
    private WaresAdapter mAdapter;
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mRvWares;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategoryContentView = inflater.inflate(R.layout.fragment_category, container, false);
        initViews();
        return mCategoryContentView;
    }

    @Override
    public void initViews() {
        initRefreshLayout();
        requestBannersData();
        requestCategoryData();
        requestWaresData(categoryId);

    }

    private void initRefreshLayout() {
        mRefreshLayout = (MaterialRefreshLayout) mCategoryContentView.findViewById(R.id.id_refresh);
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

    private void loadMoreData() {
        curPage = ++curPage;
        mCurrentState = STATE_MORE;
        requestWaresData(categoryId);
    }

    private void refreshData() {
        curPage = 1;
        mCurrentState = STATE_REFRESH;
        requestWaresData(categoryId);

    }

    /**
     * 请求某一分类的广告数据
     */
    private void requestWaresData(long categoryId) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("curPage", curPage + "");
        paramMap.put("pageSize", pageSize + "");
        paramMap.put("categoryId", categoryId + "");
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_WARES_LIST,
                new RequestParams(paramMap)), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                Page page = (Page) responseObj;
                mWaresList = page.getList();
                curPage = page.getCurrentPage();
                pageSize = page.getPageSize();
                mTotalPage = page.getTotalCount() / pageSize + 1;
                Logger.d("mTotalPage:" + mTotalPage);
                showWaresList();
            }

            @Override
            public void onFailure(Object reasonObj) {
                ToastUtils.showToast(getContext(), "Wares数据请求错误");
            }
        }, Page.class));
    }

    /**
     * 展示广告数据
     */
    private void showWaresList() {
        switch (mCurrentState) {
            case STATE_NORMAL:
                mRvWares = (RecyclerView) mCategoryContentView.findViewById(R.id.id_wares_rv);
                mAdapter = new WaresAdapter(mWaresList, getContext(), R.layout.gird_wares_item);
                mRvWares.setAdapter(mAdapter);
                mRvWares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                mRvWares.setItemAnimator(new DefaultItemAnimator());
                mRvWares.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;
            case STATE_REFRESH:
                mAdapter.clearData();
                mAdapter.addData(mWaresList);
                mRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getDatas().size(), mWaresList);
                mRefreshLayout.finishRefreshLoadMore();
                break;
            default:
                break;
        }

    }

    /**
     * 请求左侧分类列表的数据
     */
    private void requestCategoryData() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_CATEGORY_LIST, null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CategoryBean[] beans = (CategoryBean[]) responseObj;
                mCategories = Arrays.asList(beans);
                Logger.d("mCategories" + mCategories.size());
                showCategoryList();

                if (mCategories != null && mCategories.size() > 0) {
                    categoryId = mCategories.get(0).getId();
                    requestWaresData(categoryId);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                ToastUtils.showToast(getContext(), "Category数据请求错误");
            }
        }, CategoryBean[].class));
    }

    /**
     * 展示左侧分类列表数据到RecyclerView上
     */
    private void showCategoryList() {
        RecyclerView rvCategory = (RecyclerView) mCategoryContentView.findViewById(R.id.id_category_rv);
        CategoryListAdapter adapter = new CategoryListAdapter(mCategories, getContext(),
                R.layout.category_first_level_menu_item);
        adapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToastUtils.showToast(getContext(), mCategories.get(position).getId() + "");
                categoryId = mCategories.get(position).getId();
                //此处点击了另外一个分类，则应该从另外一个分类的第1页开始加载，
                // 并且此时的加载状态应该是NORMAL
                curPage = 1;
                mCurrentState = STATE_NORMAL;
                requestWaresData(categoryId);
            }
        });
        rvCategory.setAdapter(adapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        rvCategory.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 请求顶部轮播广告的数据
     */
    private void requestBannersData() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_HOME_BANNER,
                new RequestParams("type", "1")),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        BannerBean[] bannerBeen = (BannerBean[]) responseObj;
                        mBanners = Arrays.asList(bannerBeen);
                        showBanner();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        ToastUtils.showToast(getContext(), "Banner数据请求错误");
                    }
                }, BannerBean[].class));
    }

    /**
     * 展示顶部轮播广告
     */
    private void showBanner() {
        mSliderLayout = (SliderLayout) mCategoryContentView.findViewById(R.id.id_slider);
        if (mBanners != null) {
            for (final BannerBean banner : mBanners) {
                TextSliderView sliderView = new TextSliderView(getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.CenterCrop);
                mSliderLayout.addSlider(sliderView);
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        ToastUtils.showToast(getContext(), banner.getName());
                    }
                });
            }
        }
        //对SliderLayout进行一些自定义的配置
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
    }

    @Override
    public void onDestroy() {
        mSliderLayout.stopAutoCycle();
        super.onDestroy();
    }
}
