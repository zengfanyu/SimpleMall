package com.zfy.simplemall.bean;

/**
 * Created by ZFY on 9:41.
 *
 * @function:
 */

public class CampaignBean {
    /**
     * id : 17
     * title : 手机专享
     * imgUrl : http://7mno4h.com2.z0.glb.qiniucdn.com/555c6c90Ncb4fe515.jpg
     */
    private int id;
    private String title;
    private String imgUrl;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
