package com.zfy.simplemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;
import com.zfy.simplemall.utils.CartProvider;
import com.zfy.simplemall.utils.TypeCastUtils;
import com.zfy.simplemall.utils.ToastUtils;

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
        mCartProvider = CartProvider.getInstance(context);
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
        if (btnBuy != null) {
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在此处点击立即购买按钮，就添加到购物车中
                    mCartProvider.put(TypeCastUtils.WaresToShoppingCart(wares));
                    ToastUtils.showToast(mContext, "已添加至购物车");
                }
            });
        }

    }


    public void resetLayout(int layoutId) {
        this.mLayoutId = layoutId;
        notifyDataSetChanged();
    }
}
