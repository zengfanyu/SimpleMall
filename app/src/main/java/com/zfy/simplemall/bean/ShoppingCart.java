package com.zfy.simplemall.bean;

/**
 * Created by ZFY on 2017/04/21.
 *
 * @function: 购物车中的实体类
 */

public class ShoppingCart extends Wares {
    private int count;
    private boolean isChecked = true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
