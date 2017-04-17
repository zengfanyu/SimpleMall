package com.zfy.simplemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:ViewHolder类的基类
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    protected SparseArray<View> mViews;
    protected Context mContext;
    protected View mItemView;
    protected BaseAdapter.onItemClickListener mListener;


    public BaseViewHolder(Context context, View itemView, BaseAdapter.onItemClickListener listener) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mViews = new SparseArray<>();
        this.mListener = listener;
        mItemView.setOnClickListener(this);
    }

    public static BaseViewHolder get(Context context, ViewGroup parent, int layoutId, BaseAdapter.onItemClickListener listener) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView, listener);
    }

    /**
     * 通过viewId获取控件的方法
     */
    public <T extends View> T getView(int viewId) {
        View v = mViews.get(viewId);
        if (v == null) {
            v = mItemView.findViewById(viewId);
            mViews.put(viewId, v);
        }
        return (T) v;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onClick(v, getLayoutPosition());
        }
    }
}
