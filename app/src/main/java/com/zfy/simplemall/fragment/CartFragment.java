package com.zfy.simplemall.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.MainActivity;
import com.zfy.simplemall.adapter.CartAdapter;
import com.zfy.simplemall.adapter.Decoration.DividerItemDecoration;
import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.toastutils.ToastUtils;
import com.zfy.simplemall.widget.SearchToolBar;

import java.util.List;

/**
 * 购物车 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class CartFragment extends BaseFragment implements onToolbarRightButtonClickListener, View.OnClickListener {

    private View mCartContentView;
    private CartProvider mCartProvider;
    private RecyclerView mRvCart;
    private SearchToolBar mToolBar;
    private CheckBox mCbAll;
    private TextView mTvTotal;
    private CartAdapter mAdapter;
    //Toolbar右边按钮的状态值
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_COMPLETE = 2;
    private Button mToolBarRightButton;
    private Button mBtnOrder;
    private Button mBtnDel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mCartContentView == null) {
            mCartContentView = inflater.inflate(R.layout.fragment_cart, container, false);
        }
        ViewGroup parent = (ViewGroup) mCartContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mCartContentView);
        }
        initViews();
        return mCartContentView;
    }

    @Override
    public void initViews() {
        mCartProvider = CartProvider.getInstance(getContext());
        initView();
        showData();
    }

    private void initView() {
        mRvCart = (RecyclerView) mCartContentView.findViewById(R.id.id_recyclerView);
        mCbAll = (CheckBox) mCartContentView.findViewById(R.id.id_all_cb);
        mTvTotal = (TextView) mCartContentView.findViewById(R.id.id_total_tv);
        mBtnOrder = (Button) mCartContentView.findViewById(R.id.id_order_btn);
        mBtnDel = (Button) mCartContentView.findViewById(R.id.id_del_btn);
        mBtnDel.setOnClickListener(this);
    }

    private void showData() {
        final List<ShoppingCart> carts = mCartProvider.getAll();
        mAdapter = new CartAdapter(carts, getContext(), R.layout.cart_item);
        mAdapter.setCbAll(mCbAll);
        mAdapter.setTvTotal(mTvTotal);
        mRvCart.setAdapter(mAdapter);
        mRvCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvCart.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            mToolBar = (SearchToolBar) mainActivity.findViewById(R.id.search_tool_bar);
            mToolBar.setTitle("购物车");
            mToolBarRightButton = mToolBar.getRightButton();
            mToolBarRightButton.setText("编辑");
            mToolBarRightButton.setTag(ACTION_EDIT);
            mToolBar.setRightButtonOnClickListener(this);

        }
    }

    public void refreshData() {
        mAdapter.clearData();
        List<ShoppingCart> carts = mCartProvider.getAll();
        mAdapter.addData(carts);
        mAdapter.showTotalPrice();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCartContentView = null;
    }


    private void hideDeleteBtn() {
        mToolBarRightButton.setText("编辑");
        mTvTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
        mBtnDel.setVisibility(View.GONE);
        mToolBarRightButton.setTag(ACTION_EDIT);
        mAdapter.setAllCheck(true);
    }

    private void showDeleteBtn() {
        mToolBarRightButton.setText("完成");
        mTvTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolBarRightButton.setTag(ACTION_COMPLETE);
        mAdapter.setAllCheck(false);

    }

    @Override
    public void onClick(View view) {
        ToastUtils.showToast(getContext(), "ToolbarRightButton");
        switch (view.getId()) {
            case R.id.toolbar_rightButton:
                int tag = (int) mToolBarRightButton.getTag();
                if (tag == ACTION_EDIT) {
                    showDeleteBtn();
                } else if (tag == ACTION_COMPLETE) {
                    hideDeleteBtn();
                }
                break;
            case R.id.id_del_btn:
                mAdapter.deleteItemData();
                hideDeleteBtn();
                break;
        }
    }
}
