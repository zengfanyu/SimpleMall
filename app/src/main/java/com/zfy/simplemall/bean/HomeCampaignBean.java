package com.zfy.simplemall.bean;

/**
 * Created by ZFY on 9:36.
 *
 * @function:HomeFragment中的广告图片
 */

public class HomeCampaignBean {

    /**
     * cpOne : {"id":17,"title":"手机专享","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg"}
     * cpTwo : {"id":15,"title":"闪购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560a26d2N78974496.jpg"}
     * cpThree : {"id":11,"title":"团购","imgUrl":"http://7mno4h.com2.z0.glb.qiniucdn.com/560be0c3N9e77a22a.jpg"}
     * id : 1
     * title : 超值购
     * campaignOne : 17
     * campaignTwo : 15
     * campaignThree : 11
     */

    private CampaignBean cpOne;
    private CampaignBean cpTwo;
    private CampaignBean cpThree;
    private int id;
    private String title;

    public CampaignBean getCpOne() {
        return cpOne;
    }

    public void setCpOne(CampaignBean cpOne) {
        this.cpOne = cpOne;
    }

    public CampaignBean getCpTwo() {
        return cpTwo;
    }

    public void setCpTwo(CampaignBean cpTwo) {
        this.cpTwo = cpTwo;
    }

    public CampaignBean getCpThree() {
        return cpThree;
    }

    public void setCpThree(CampaignBean cpThree) {
        this.cpThree = cpThree;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
