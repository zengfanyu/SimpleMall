package com.zfy.simplemall.bean;

/**
 * Created by Administrator on 2017/4/10/010.
 */

public class HomeCategoryBean {
    private String title;
    private int imgBig;
    private int imgSmall1;
    private int imgSmall2;

    public HomeCategoryBean(String title, int imgBig, int imgSmall1, int imgSmall2) {
        this.title = title;
        this.imgBig = imgBig;
        this.imgSmall1 = imgSmall1;
        this.imgSmall2 = imgSmall2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgBig() {
        return imgBig;
    }

    public void setImgBig(int imgBig) {
        this.imgBig = imgBig;
    }

    public int getImgSmall1() {
        return imgSmall1;
    }

    public void setImgSmall1(int imgSmall1) {
        this.imgSmall1 = imgSmall1;
    }

    public int getImgSmall2() {
        return imgSmall2;
    }

    public void setImgSmall2(int imgSmall2) {
        this.imgSmall2 = imgSmall2;
    }
}
