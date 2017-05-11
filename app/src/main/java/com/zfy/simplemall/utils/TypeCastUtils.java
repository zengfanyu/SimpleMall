package com.zfy.simplemall.utils;

import com.zfy.simplemall.bean.ShoppingCart;
import com.zfy.simplemall.bean.Wares;

/**
 * Created by ZFY on 2017/05/11.
 *
 * @function:JavaBean类型转换工具
 */

public class TypeCastUtils {
    public static ShoppingCart WaresToShoppingCart(Wares wares) {
        ShoppingCart cart = new ShoppingCart();
        cart.setDescription(wares.getDescription());
        cart.setImgUrl(wares.getImgUrl());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());
        cart.setId(wares.getId());
        return cart;
    }
}
