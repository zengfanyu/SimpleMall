package com.zfy.simplemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:Adapter的基类
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mDatas;
    protected Context mContext;
    protected int mLayoutId;
    protected onItemClickListener mListener;
    protected int position=-1;

    public BaseAdapter(List<T> datas, Context context, int layoutId) {
        this.mDatas = datas;
        this.mContext = context;
        this.mLayoutId = layoutId;
    }
    public BaseAdapter(List<T> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseViewHolder.get(mContext, parent, mLayoutId,mListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mDatas.get(position);
        setPosition(position);
        bindData(holder, t);
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
    public void addData(List<T> datas) {
        addData(0, datas);
    }

    /**
     * 从指定position位置处开始刷新
     */
    public void addData(int position, List<T> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);//先添加数据
            notifyItemRangeChanged(position, mDatas.size());//再通知刷新
        }
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 返回当前的数据集
     */
    public List<T> getDatas() {
        return mDatas;
    }

    public interface onItemClickListener {
        void onClick(View view, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        mListener = listener;
    }

    protected abstract void bindData(BaseViewHolder holder, T t);
}
