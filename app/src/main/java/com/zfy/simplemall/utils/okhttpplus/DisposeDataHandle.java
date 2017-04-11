package com.zfy.simplemall.utils.okhttpplus;

import com.zfy.simplemall.utils.okhttpplus.listener.DisposeDataListener;

/**
 * Created by Administrator on 2017/4/11/011.
 */

public class DisposeDataHandle {
    public DisposeDataListener mListener;
    public Class<?> mClass;

    public DisposeDataHandle(DisposeDataListener listener) {
        mListener = listener;
    }

    public DisposeDataHandle(DisposeDataListener listener, Class<?> aClass) {
        mListener = listener;
        mClass = aClass;
    }
}
