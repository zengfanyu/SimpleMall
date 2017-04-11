package com.zfy.simplemall.fragment;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.Decoration.DividerItemDecoration;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.HomeCategoryAdapter;
import com.zfy.simplemall.bean.BannerBean;
import com.zfy.simplemall.bean.HomeCategoryBean;
import com.zfy.simplemall.config.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 主页 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class HomeFragment extends BaseFragment {

    private View mHomeContentView;
    private SliderLayout mSliderLayout;
    private List<HomeCategoryBean> mHomeCategory;
    private Gson mGson = new Gson();
    private List<BannerBean> mBanners;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeContentView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return mHomeContentView;
    }

    @Override
    public void initViews() {
        initBanner();
        initRecyclerView();

    }

    public void initBanner() {
        OkHttpClient client = new OkHttpClient();

        FormBody body = new FormBody.Builder().add("type", "1").build();

        Request request = new Request.Builder()
                .url(Constant.URL_HOME_BANNER_POST)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String strJSON = response.body().string();
                    Logger.json(strJSON);
                    mBanners = mGson.fromJson(strJSON, new TypeToken<List<BannerBean>>() {
                    }.getType());
                    Logger.d(mBanners.size());
                    initSlider();
                }
            }
        });


    }


    //初始化RecyclerView
    private void initRecyclerView() {
        //RecyclerView Item 的数据
        mHomeCategory = new ArrayList<>();
        HomeCategoryBean category = new HomeCategoryBean("热门活动", R.drawable.img_big_1, R.drawable.img_1_small1, R.drawable.img_1_small2);
        mHomeCategory.add(category);

        category = new HomeCategoryBean("有利可图", R.drawable.img_big_4, R.drawable.img_4_small1, R.drawable.img_4_small2);
        mHomeCategory.add(category);
        category = new HomeCategoryBean("品牌街", R.drawable.img_big_2, R.drawable.img_2_small1, R.drawable.img_2_small2);
        mHomeCategory.add(category);

        category = new HomeCategoryBean("金融街 包赚翻", R.drawable.img_big_1, R.drawable.img_3_small1, R.drawable.imag_3_small2);
        mHomeCategory.add(category);

        category = new HomeCategoryBean("超值购", R.drawable.img_big_0, R.drawable.img_0_small1, R.drawable.img_0_small2);
        mHomeCategory.add(category);

        Logger.d(mHomeCategory.size());

        RecyclerView recyclerView = (RecyclerView) mHomeContentView.findViewById(R.id.home_rv);
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(mHomeCategory, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    //初始化slider
    private void initSlider() {
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
}
