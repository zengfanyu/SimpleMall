package com.zfy.simplemall.utils.okhttpplus.listener;

/**
 * Created by Administrator on 2017/4/11/011.
 */

public interface DisposeDataListener {
    public void onSuccess(Object responseObj);

    public void onFailure(Object reasonObj);
}
