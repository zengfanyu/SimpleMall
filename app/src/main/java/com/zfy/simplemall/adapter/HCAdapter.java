package com.zfy.simplemall.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.HomeCampaignBean;

import java.util.List;

import static com.zfy.simplemall.R.id.tv_title;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:
 */

public class HCAdapter extends BaseAdapter<HomeCampaignBean> {
    public static int VIEW_TYPE_LEFT = 0;
    public static int VIEW_TYPE_RIGHT = 1;

    public HCAdapter(List<HomeCampaignBean> datas, Context context) {
        super(datas, context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RIGHT) {
            return BaseViewHolder.get(mContext, parent, R.layout.home_cardview_item2, mListener);
        }
        return BaseViewHolder.get(mContext, parent, R.layout.home_cardview_item, mListener);
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_RIGHT;
        } else {
            return VIEW_TYPE_LEFT;
        }
    }

    @Override
    protected void bindData(BaseViewHolder holder, HomeCampaignBean homeCampaignBean) {
        TextView textTitle = holder.getView(tv_title);
        ImageView imageViewBig = holder.getView(R.id.img_big);
        ImageView imageViewSmallBottom = holder.getView(R.id.img_small_bottom);
        ImageView imageViewSmallTop = holder.getView(R.id.img_small_top);


        textTitle.setText(homeCampaignBean.getTitle());
        Picasso.with(mContext).load(homeCampaignBean.getCpOne().getImgUrl()).into(imageViewBig);
        Picasso.with(mContext).load(homeCampaignBean.getCpTwo().getImgUrl()).into(imageViewSmallTop);
        Picasso.with(mContext).load(homeCampaignBean.getCpThree().getImgUrl()).into(imageViewSmallBottom);
    }


}
