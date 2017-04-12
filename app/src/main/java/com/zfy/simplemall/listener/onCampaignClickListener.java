package com.zfy.simplemall.listener;

import android.view.View;

import com.zfy.simplemall.bean.CampaignBean;

/**
 * Created by ZFY on 9:59.
 *
 * @function:HomeFragment中的广告图片的监听器
 */

public interface onCampaignClickListener {
    public void onClick(View view, CampaignBean campaignBean);
}
