package com.zfy.simplemall.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.widget.CartItemCombinationLayout;

import java.util.List;


/**
 * Created by ZFY on 2017/04/21.
 *
 * @function:CartFragment 的列表 Adapter
 */

public class CartAdapter extends BaseAdapter<ShoppingCart> {
    public CartAdapter(List<ShoppingCart> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, ShoppingCart shoppingCart) {
        CheckBox checkBox = holder.getView(R.id.id_checkbox);
        TextView tvTitle = holder.getView(R.id.id_title_tv);
        TextView tvPrice = holder.getView(R.id.id_price_tv);
        SimpleDraweeView draweeView = holder.getView(R.id.id_drawee_view);
        CartItemCombinationLayout clControl = holder.getView(R.id.id_num_control);


    }
}
