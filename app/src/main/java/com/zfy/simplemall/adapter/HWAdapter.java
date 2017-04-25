package com.zfy.simplemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import java.util.List;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:
 */

public class HWAdapter extends BaseAdapter<Wares> {
    private CartProvider mCartProvider;
    private Context mContext;

    public HWAdapter(List<Wares> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
        mCartProvider = new CartProvider(context);
        mContext = context;
    }

    @Override
    protected void bindData(BaseViewHolder holder, final Wares wares) {
        SimpleDraweeView draweeView = holder.getView(R.id.id_drawee_view);
        TextView tvTitle = holder.getView(R.id.id_title_tv);
        TextView tvPrice = holder.getView(R.id.id_price_tv);
        Button btnBuy = holder.getView(R.id.id_buy_btn);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        tvPrice.setText(wares.getPrice() + "");
        tvTitle.setText(wares.getName());
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在此处点击立即购买按钮，就添加到购物车中
                mCartProvider.put(convertData(wares));
                ToastUtils.showToast(mContext, "已添加至购物车");
            }
        });

    }

    /**
     * 将Wares转换成ShoppingCart的方法。
     * 强制向下转型会报错
     */
    public ShoppingCart convertData(Wares item) {
        ShoppingCart cart = new ShoppingCart();
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());
        cart.setId(item.getId());
        return cart;

    }
}
