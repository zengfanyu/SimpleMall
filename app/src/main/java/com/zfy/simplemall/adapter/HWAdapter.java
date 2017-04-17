package com.zfy.simplemall.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;

import java.util.List;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:
 */

public class HWAdapter extends BaseAdapter<Wares> {
    public HWAdapter(List<Wares> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    protected void bindData(BaseViewHolder holder, Wares wares) {
        SimpleDraweeView draweeView = holder.getView(R.id.id_drawee_view);
        TextView tvTitle = holder.getView(R.id.id_title_tv);
        TextView tvPrice = holder.getView(R.id.id_price_tv);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));
        tvPrice.setText(wares.getPrice() + "");
        tvTitle.setText(wares.getName());

    }
}
