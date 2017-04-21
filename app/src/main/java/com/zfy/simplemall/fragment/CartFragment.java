package com.zfy.simplemall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zfy.simplemall.R;
import com.zfy.simplemall.utils.CartProvider;

/**
 * 购物车 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class CartFragment extends BaseFragment {

    private View mCartContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCartContentView = inflater.inflate(R.layout.fragment_cart, container, false);
        initViews();
        return mCartContentView;
    }

    @Override
    public void initViews() {
        CartProvider cartProvider=new CartProvider(getContext());
    }
}
