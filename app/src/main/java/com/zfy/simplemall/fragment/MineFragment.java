package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zfy.simplemall.R;

/**
 * 我的 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class MineFragment extends BaseFragment {
    private View mMineContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mMineContentView == null) {
            mMineContentView = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        ViewGroup parent = (ViewGroup) mMineContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mMineContentView);
        }
        return mMineContentView;
    }

    @Override
    public void initViews() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMineContentView = null;
    }
}
