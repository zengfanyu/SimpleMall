package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.WaresListActivity;
import com.zfy.simplemall.adapter.BaseAdapter;
import com.zfy.simplemall.adapter.Decoration.DividerItemDecoration;
import com.zfy.simplemall.adapter.HCAdapter;
import com.zfy.simplemall.bean.BannerBean;
import com.zfy.simplemall.bean.HomeCampaignBean;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 主页 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class HomeFragment extends BaseFragment {

    private View mHomeContentView;
    private SliderLayout mSliderLayout;
    private List<BannerBean> mBanners;
    private List<HomeCampaignBean> mHomeCampaignBeen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mHomeContentView == null) {
            mHomeContentView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        ViewGroup parent = (ViewGroup) mHomeContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mHomeContentView);
        }
        initViews();
        return mHomeContentView;
    }

    @Override
    public void initViews() {
        initBanner();
        initRecyclerView();

    }

    public void initBanner() {

        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_HOME_BANNER,
                new RequestParams("type", "1")), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                BannerBean[] bannerBeans = (BannerBean[]) responseObj;
                mBanners = Arrays.asList(bannerBeans);
                prepareSlider();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }, BannerBean[].class));
    }


    //初始化RecyclerView
    private void initRecyclerView() {

        CommonOkHttpClient.get(CommonRequest.createGetRequest(Constant.URL_HOME_CAMPAIGN, null), new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                HomeCampaignBean[] campaignBeen = (HomeCampaignBean[]) responseObj;
                mHomeCampaignBeen = Arrays.asList(campaignBeen);


                prepareRecyclerView();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }, HomeCampaignBean[].class));
    }

    private void prepareRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mHomeContentView.findViewById(R.id.home_rv);
        HCAdapter adapter = new HCAdapter(mHomeCampaignBeen, getContext());
        adapter.setOnItemClickListener(new BaseAdapter.onItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ToastUtils.showToast(getContext(), mHomeCampaignBeen.get(position).getTitle());
                Intent intent = new Intent(getActivity(), WaresListActivity.class);
                intent.putExtra(Constant.EXTRA_CAMPAIGN_ID, mHomeCampaignBeen.get(position).getId());
                intent.putExtra(Constant.EXTRA_CAMPAIGN_NAME, mHomeCampaignBeen.get(position).getTitle());
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    //初始化slider
    private void prepareSlider() {
        mSliderLayout = (SliderLayout) mHomeContentView.findViewById(R.id.slider);
        PagerIndicator indicator = (PagerIndicator) mHomeContentView.findViewById(R.id.custom_indicator);
        if (mBanners != null) {
            for (final BannerBean bean : mBanners) {
                TextSliderView sliderView = new TextSliderView(getActivity());
                sliderView.image(bean.getImgUrl());
                sliderView.description(bean.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);
                sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Toast.makeText(getActivity(), bean.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }
        //对SliderLayout进行一些自定义的配置
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSliderLayout.setDuration(3000);
        mSliderLayout.setCustomIndicator(indicator);
    }

    @Override
    public void onDestroy() {
        mSliderLayout.stopAutoCycle();
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeContentView = null;
    }
}
