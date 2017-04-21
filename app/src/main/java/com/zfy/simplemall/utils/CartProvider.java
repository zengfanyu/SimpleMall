package com.zfy.simplemall.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.zfy.simplemall.bean.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZFY on 2017/04/21.
 *
 * @function:购物车数据存储帮助类
 */

public class CartProvider {
    private SparseArray<ShoppingCart> mDatas = null;
    private Context mContext;
    private String CART_JSON = "cart_json";

    public CartProvider(Context context) {
        mDatas = new SparseArray<>(10);
        this.mContext = context;
        listToSparse();
    }

    public void put(ShoppingCart shoppingCart) {
        int cartId = shoppingCart.getId().intValue();
        ShoppingCart tmpCart = mDatas.get(cartId);
        if (tmpCart != null) {
            tmpCart.setCount(tmpCart.getCount() + 1);
        } else {
            mDatas.put(cartId, shoppingCart);
        }
        commit();
    }

    public void update(ShoppingCart shoppingCart) {
        mDatas.put(shoppingCart.getId().intValue(), shoppingCart);
        commit();
    }

    public void delete(ShoppingCart shoppingCart) {
        mDatas.delete(shoppingCart.getId().intValue());
        commit();
    }

    public List<ShoppingCart> getAll() {
        return getDataFromLocal();
    }

    /**
     * 将内存中的数据更新到本地SP
     *
     * @param
     * @return
     * @author zfy
     * @created at 2017/4/21/021 12:47
     */
    private void commit() {
        List<ShoppingCart> carts = sparseToList(mDatas);
        SPUtils.put(mContext, CART_JSON, JsonUtils.toJSON(carts));
    }

    /**
     * 从本地SP中获取String类型的Json数据，在将其转换为List的方法
     *
     * @param
     * @return
     * @author zfy
     * @created at 2017/4/21/021 12:43
     */
    private List<ShoppingCart> getDataFromLocal() {
        String json = (String) SPUtils.get(mContext, CART_JSON, "");
        List<ShoppingCart> carts = null;
        if (json != null) {
            carts = JsonUtils.fromJson(json, new TypeToken<List<ShoppingCart>>() {
            }.getType());
        }
        return carts;
    }

    /**
     * 将SparseArray类型的数据转换为List类型
     *
     * @param
     * @return
     * @author zfy
     * @created at 2017/4/21/021 12:46
     */
    private List<ShoppingCart> sparseToList(SparseArray datas) {
        int size = datas.size();
        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((ShoppingCart) datas.valueAt(i));
        }
        return list;
    }

    /**
     * 将本地List类型的数据转换为SparseArray类型
     *
     * @param
     * @return
     * @author zfy
     * @created at 2017/4/21/021 12:44
     */
    private void listToSparse() {
        List<ShoppingCart> dataFromLocal = getDataFromLocal();
        if (dataFromLocal != null && dataFromLocal.size() > 0) {
            for (ShoppingCart cart : dataFromLocal) {
                mDatas.put(cart.getId().intValue(), cart);
            }
        }
    }
}
