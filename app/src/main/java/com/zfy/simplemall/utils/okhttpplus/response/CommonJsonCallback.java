package com.zfy.simplemall.utils.okhttpplus.response;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 专门针对JSON数据的回调处理
 * Created by Administrator on 2017/4/11/011.
 */

public class CommonJsonCallback implements Callback {

    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Handler mDeliverHandler;
    private Gson mGson;


    public CommonJsonCallback(DisposeDataHandle handle) {
        mListener = handle.mListener;
        mClass = handle.mClass;
        mDeliverHandler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliverHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(e);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        //此处是在子线程中，所以不能直接毁掉listener中的任何方法
        if (response.isSuccessful()) {
            final String result = response.body().string();
            Logger.json(result);
            mDeliverHandler.post(new Runnable() {
                @Override
                public void run() {
                    handleResponse(result);
                }


            });
        } else if (isTokenWrong(response)) {
            mListener.onFailure(new OkHttpException(response.code(), Constant.TOKEN_MSG));
        } else {
            mListener.onFailure(new OkHttpException(Constant.NETWORK_ERROR, Constant.NETWORK_ERROR));
        }
    }

    //此方法内可以根据服务器返回的不同状态码来做判别，此处只做基本的判断
    private void handleResponse(String result) {
        if (TextUtils.isEmpty(result)) {
            mListener.onFailure(new OkHttpException(Constant.NETWORK_ERROR, Constant.EMPTY_MSG));
            return;
        }
        if (mClass == null) {
            mListener.onSuccess(result);
        } else {
            Object obj = mGson.fromJson(result, mClass);
            if (obj != null) {
                mListener.onSuccess(obj);
            } else {
                mListener.onFailure(new OkHttpException(Constant.JSON_ERROR, Constant.EMPTY_MSG));
            }
        }
    }

    public boolean isTokenWrong(Response response) {

        return response.code() == Constant.TOKEN_EXPIRED ||
                response.code() == Constant.TOKEN_MISS ||
                response.code() == Constant.TOKEN_ERROR;
    }
}
