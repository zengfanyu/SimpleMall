package com.zfy.simplemall.utils;

import android.content.Context;
import android.text.TextUtils;

import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.config.Constant;

/**
 * Created by ZFY on 2017/05/15.
 *
 * @function:保存提取用户数据的帮助类
 */

public class UserDataLocalUtils {

    public static void putUser(Context context, User user) {
        String jsonUser = JsonUtils.toJSON(user);
        SPUtils.put(context, Constant.SP_JSON_USER_KEY, jsonUser);

    }

    public static User getUser(Context context) {
        String jsonUser = (String) SPUtils.get(context, Constant.SP_JSON_USER_KEY, null);
        if (!TextUtils.isEmpty(jsonUser)) {
            return JsonUtils.fromJson(jsonUser, User.class);
        }
        return null;
    }

    public static void putToken(Context context, String token) {
        SPUtils.put(context, Constant.SP_TOKEN_KEY, token);

    }

    public static String getToken(Context context) {
        String token = (String) SPUtils.get(context, Constant.SP_TOKEN_KEY, "");
        if (!TextUtils.isEmpty(token)) {
            return token;
        }

        return null;
    }

    public static void clearUser(Context context) {
        SPUtils.put(context, Constant.SP_JSON_USER_KEY, null);
    }

    public static void clearToken(Context context) {
        SPUtils.put(context, Constant.SP_TOKEN_KEY, null);
    }
}
