package com.zfy.simplemall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.HomeCampaignBean;
import com.zfy.simplemall.listener.onCampaignClickListener;

import java.util.List;

import static com.zfy.simplemall.R.id.tv_title;

/**
 * HomeFragment的RecyclerView 的Adapter
 * Created by Administrator on 2017/4/10/010.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {
    public static int VIEW_TYPE_LEFT = 0;
    public static int VIEW_TYPE_RIGHT = 1;
    private LayoutInflater mInflater;
    private List<HomeCampaignBean> mDatas;
    private Context mContext;
    private onCampaignClickListener mListener;

    public HomeCategoryAdapter(List<HomeCampaignBean> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override //返回ViewHolder的方法
    public HomeCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        Logger.d(mInflater == null);
        if (viewType == VIEW_TYPE_RIGHT) {
            return new ViewHolder(mInflater.inflate(R.layout.home_cardview_item2, parent, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.home_cardview_item, parent, false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampaignBean campaignBean = mDatas.get(position);
        holder.textTitle.setText(campaignBean.getTitle());

        Picasso.with(mContext).load(campaignBean.getCpOne().getImgUrl()).into(holder.imageViewBig);
        Picasso.with(mContext).load(campaignBean.getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(campaignBean.getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);
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

    //设置监听的方法
    public void setOnCampaignClickListener(onCampaignClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(tv_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.img_big);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.img_small_bottom);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.img_small_top);

            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            HomeCampaignBean homeCampaignBean = mDatas.get(getLayoutPosition());

            if (mListener != null) {
                switch (v.getId()) {
                    case R.id.img_big:
                        mListener.onClick(v, homeCampaignBean.getCpOne());
                        break;
                    case R.id.img_small_top:
                        mListener.onClick(v, homeCampaignBean.getCpTwo());
                        break;
                    case R.id.img_small_bottom:
                        mListener.onClick(v, homeCampaignBean.getCpThree());
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
