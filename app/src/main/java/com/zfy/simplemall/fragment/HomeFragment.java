package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeContentView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return mHomeContentView;
    }

    @Override
    public void initView() {
        initData();
        initSlider();
    }

    //初始化数据
    private void initData() {
        mImageUrls = new ArrayList<>();
        mDescriptions = new ArrayList<>();
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
        mImageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");
        mDescriptions.add("新品推荐");
        mDescriptions.add("时尚男装");
        mDescriptions.add("家电秒杀");
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
