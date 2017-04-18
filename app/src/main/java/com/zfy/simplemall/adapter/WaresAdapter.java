package com.zfy.simplemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;

import java.util.List;

/**
 * Created by ZFY on 2017/04/18.
 *
 * @function:CategoryFragment中分类商品展示列表的Adapter
 */

public class WaresAdapter extends BaseAdapter<Wares> {
    public WaresAdapter(List<Wares> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }


    @Override
    protected void bindData(BaseViewHolder holder, Wares wares) {
        SimpleDraweeView draweeView = holder.getView(R.id.id_drawee_view);
        TextView tvTitle = holder.getView(R.id.id_title_tv);
        TextView tvPrice = holder.getView(R.id.id_price_tv);

        tvTitle.setText(wares.getName());
        tvPrice.setText("$" + wares.getPrice());
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

    }
}
