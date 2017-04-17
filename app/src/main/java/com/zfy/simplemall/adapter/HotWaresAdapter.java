package com.zfy.simplemall.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.Wares;

import java.util.List;

/**
 * Created by ZFY on 2017/04/13.
 *
 * @function:
 */

public class HotWaresAdapter extends RecyclerView.Adapter<HotWaresAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Wares> mDatas;
    private View mHotContentView;

    public HotWaresAdapter(List<Wares> datas) {
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        mHotContentView = mInflater.inflate(R.layout.hot_wares_item, parent, false);
        return new ViewHolder(mHotContentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares waresBean = mDatas.get(position);
        holder.draweeView.setImageURI(Uri.parse(waresBean.getImgUrl()));
        holder.tvPrice.setText(waresBean.getPrice() + "");
        holder.tvTitle.setText(waresBean.getName());
//        Logger.d(waresBean.getDescription());
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        }
        return 0;
    }

    /**
     * 清除数据集的方法
     */
    public void clearData() {
        mDatas.clear();
        notifyItemRangeChanged(0, mDatas.size());
    }

    /**
     * 从头开始刷新
     */
    public void addData(List<Wares> datas) {
        addData(0, datas);
    }

    /**
     * 从指定position位置处开始刷新
     */
    public void addData(int position, List<Wares> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);//先添加数据
            notifyItemRangeChanged(position, mDatas.size());//再通知刷新
        }
    }
    /**
     * 返回当前的数据集
     */
    public List<Wares> getDatas() {
        return mDatas;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView draweeView;
        TextView tvTitle;
        TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.id_drawee_view);
            tvTitle = (TextView) itemView.findViewById(R.id.id_title_tv);
            tvPrice = (TextView) itemView.findViewById(R.id.id_price_tv);
        }
    }
}
