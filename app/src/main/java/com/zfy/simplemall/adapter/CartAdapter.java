package com.zfy.simplemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.widget.CartItemCombinationLayout;

import java.util.Iterator;
import java.util.List;


/**
 * Created by ZFY on 2017/04/21.
 *
 * @function:CartFragment 的列表 Adapter
 */

public class CartAdapter extends BaseAdapter<ShoppingCart> implements BaseAdapter.onItemClickListener {
    private CheckBox mCbAll; //Fragment中的全选
    private TextView mTvTotal;
    private List<ShoppingCart> mDatas;
    private CartProvider mCartProvider;

    public CartAdapter(List<ShoppingCart> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
        this.mDatas = datas;
        mCartProvider = CartProvider.getInstance(context);
        setOnItemClickListener(this);

    }

    @Override
    protected void bindData(BaseViewHolder holder, final ShoppingCart shoppingCart) {
        CheckBox checkBox = holder.getView(R.id.id_checkbox);
        checkBox.setChecked(shoppingCart.isChecked());
        TextView tvTitle = holder.getView(R.id.id_title_tv);
        tvTitle.setText(shoppingCart.getName());
        TextView tvPrice = holder.getView(R.id.id_price_tv);
        tvPrice.setText("$" + shoppingCart.getPrice());
        SimpleDraweeView draweeView = holder.getView(R.id.id_drawee_view);
        draweeView.setImageURI(Uri.parse(shoppingCart.getImgUrl()));
        CartItemCombinationLayout clControl = holder.getView(R.id.id_num_control);
        clControl.setCurrentValue(shoppingCart.getCount());

        //对购物车中商品的件数进行监听
        clControl.setOnButtonClickListener(new CartItemCombinationLayout.onButtonClickListener() {
            @Override
            public void onButtonAddClick(View view, int value) {
                shoppingCart.setCount(value);
                mCartProvider.update(shoppingCart);
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value) {
                shoppingCart.setCount(value);
                mCartProvider.update(shoppingCart);
                showTotalPrice();
            }
        });
        //对全选按钮进行监听
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mCbAll.isChecked();
                for (ShoppingCart cart : mDatas) {
                    cart.setChecked(isChecked);
                    mCartProvider.update(cart);
                }
                notifyItemRangeChanged(0, mDatas.size());
                showTotalPrice();
            }
        });

        showTotalPrice();
    }


    public void setCbAll(CheckBox cbAll) {
        mCbAll = cbAll;
    }


    public void setTvTotal(TextView tvTotal) {
        mTvTotal = tvTotal;
    }

    public void showTotalPrice() {
        float sum = 0;
        if (mDatas != null && mDatas.size() > 0) {
            for (ShoppingCart cart : mDatas) {
                if (cart.isChecked()) {
                    sum += cart.getPrice() * cart.getCount();
                }
            }
        }
        mTvTotal.setText("合计:" + sum + "元");
    }

    public boolean isAllChecked() {
        for (ShoppingCart cart : mDatas) {
            if (!cart.isChecked()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view, int position) {
        ShoppingCart cart = mDatas.get(position);
        cart.setChecked(!cart.isChecked());
        mCartProvider.update(cart);
        notifyItemChanged(position);
        mCbAll.setChecked(isAllChecked());
    }

    public void setAllCheck(boolean isCheck) {
        for (ShoppingCart data : mDatas) {
            data.setChecked(isCheck);
        }
        mCbAll.setChecked(isAllChecked());
        notifyItemRangeChanged(0, mDatas.size());

    }

    public void deleteItemData() {
        Iterator<ShoppingCart> iterator = mDatas.iterator();
        while (iterator.hasNext()) {
            ShoppingCart cart = iterator.next();
            if (cart.isChecked()) {
                int position = mDatas.indexOf(cart);
                mCartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);

            }
        }

      /* 这种删除方法会报错
      for (ShoppingCart cart : mDatas) {
            if (cart.isChecked()) {
                int position = mDatas.indexOf(cart);
                mCartProvider.delete(cart);
                mDatas.remove(position);
                notifyItemRemoved(position);
            }
        }*/
    }
}
