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
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.Decoration.DividerItemDecoration;
import com.zfy.simplemall.R;
import com.zfy.simplemall.adapter.HomeCategoryAdapter;
import com.zfy.simplemall.bean.HomeCategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class HomeFragment extends BaseFragment {

    private View mHomeContentView;
    private SliderLayout mSliderLayout;
    private List<String> mImageUrls;
    private List<String> mDescriptions;
    private List<HomeCategoryBean> mHomeCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeContentView = inflater.inflate(R.layout.fragment_home, container, false);
        initViews();
        return mHomeContentView;
    }

    @Override
    public void initViews() {
        initData();
        initRecyclerView();
        initSlider();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) mHomeContentView.findViewById(R.id.home_rv);
        HomeCategoryAdapter adapter = new HomeCategoryAdapter(mHomeCategory, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    //初始化数据
    private void initData() {
        //准备轮播条的数据
        mImageUrls = new ArrayList<>();
        mDescriptions = new ArrayList<>();
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");
        mDescriptions.add("新品推荐");
        mDescriptions.add("时尚男装");
        mDescriptions.add("家电秒杀");
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

    }

    //初始化slider
    private void initSlider() {
        mSliderLayout = (SliderLayout) mHomeContentView.findViewById(R.id.slider);
        PagerIndicator indicator = (PagerIndicator) mHomeContentView.findViewById(R.id.custom_indicator);

        for (int i = 0; i < mImageUrls.size(); i++) {
            //新建三个展示View，并且添加到SliderLayout
            TextSliderView sliderView = new TextSliderView(getActivity());
            sliderView.image(mImageUrls.get(i)).description(mDescriptions.get(i));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Toast.makeText(getActivity(), mDescriptions.get(finalI), Toast.LENGTH_SHORT).show();
                }
            });
            mSliderLayout.addSlider(sliderView);
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
