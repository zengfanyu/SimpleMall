package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.Decoration.DividerItemDecoration;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.CategoryListAdapter;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCategoryContentView = inflater.inflate(R.layout.fragment_category, container, false);
        initViews();
        return mCategoryContentView;
    }

    @Override
    public void initViews() {
        requestBannersData();
        requestCategoryData();
        requestWaresData(categoryId);
    }

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
                mTotalPage = page.getTotalPage();
                Logger.d("mWaresList:"+mWaresList.size());
                showWaresList();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }, Page.class));
    }

    private void showWaresList() {
        // TODO: 2017/4/17/017 展示Wares的RecyclerView 以及刷新加载更多功能
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
            }

            @Override
            public void onFailure(Object reasonObj) {

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
                // TODO: 2017/4/17/017 此处拿到的 id 字段作为 categoryId 请求服务器数据
                ToastUtils.showToast(getContext(), mCategories.get(position).getId() + "");
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
