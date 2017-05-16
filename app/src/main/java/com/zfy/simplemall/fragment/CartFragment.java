package com.zfy.simplemall.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.CreateOrderActivity;
import com.zfy.simplemall.activity.MainActivity;
import com.zfy.simplemall.adapter.CartAdapter;
import com.zfy.simplemall.adapter.Decoration.DividerItemDecoration;
import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.widget.SearchToolBar;

import java.util.List;

/**
 * 购物车 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class CartFragment extends BaseFragment implements onToolbarRightButtonClickListener, View.OnClickListener {

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

    @Override
    public void initViews() {
        mCartProvider = CartProvider.getInstance(getContext());
        initView();
        showData();
    }

    @Override
    public int convertLayoutResId() {
        return R.layout.fragment_cart;
    }

    private void initView() {
        mRvCart = (RecyclerView) mContentView.findViewById(R.id.id_recyclerView);
        mCbAll = (CheckBox) mContentView.findViewById(R.id.id_all_cb);
        mTvTotal = (TextView) mContentView.findViewById(R.id.id_total_tv);
        mBtnOrder = (Button) mContentView.findViewById(R.id.id_order_btn);
        mBtnDel = (Button) mContentView.findViewById(R.id.id_del_btn);
        mBtnDel.setOnClickListener(this);
        mBtnOrder.setOnClickListener(this);
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
        mContentView = null;
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
            case R.id.id_order_btn:
                Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                startActivity(intent, true);
                break;
            default:
                break;
        }
    }
}
