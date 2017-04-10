package com.zfy.simplemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.HomeCategoryBean;

import java.util.List;

/**
 * HomeFragment的RecyclerView 的Adapter
 * Created by Administrator on 2017/4/10/010.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    public static int VIEW_TYPE_LEFT = 0;
    public static int VIEW_TYPE_RIGHT = 1;
    private LayoutInflater mInflater;
    List<HomeCategoryBean> mDatas;

    public HomeCategoryAdapter(List<HomeCategoryBean> datas, Context context) {
        this.mDatas = datas;
    }

    @Override //返回ViewHolder的方法
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        Logger.d(mInflater==null);
        if (viewType == VIEW_TYPE_RIGHT) {
            return new ViewHolder(mInflater.inflate(R.layout.home_cardview_item2, parent, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.home_cardview_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCategoryBean categoryBean = mDatas.get(position);
        holder.textTitle.setText(categoryBean.getTitle());
        holder.imageViewSmallBottom.setImageResource(categoryBean.getImgSmall1());
        holder.imageViewSmallTop.setImageResource(categoryBean.getImgSmall2());
        holder.imageViewBig.setImageResource(categoryBean.getImgBig());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override //返回到onCreateViewHolder（）方法
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_RIGHT;
        } else {
            return VIEW_TYPE_LEFT;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.tv_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.img_big);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.img_small_bottom);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.img_small_top);
        }
    }
}
