package com.zfy.simplemall.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ZFY on 2017/04/17.
 *
 * @function:Toast 工具类
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(Context context, String showWhat) {
        if (toast == null) {
            toast = Toast.makeText(context, showWhat, Toast.LENGTH_SHORT);

        } else {
            toast.setText(showWhat);
        }
        toast.show();
    }
}
