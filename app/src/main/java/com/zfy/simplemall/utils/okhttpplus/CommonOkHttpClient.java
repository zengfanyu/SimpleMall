package com.zfy.simplemall.utils.okhttpplus;

import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.response.CommonJsonCallback;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 用来发送get，post请求的工具类
 * Created by Administrator on 2017/4/11/011.
 */

public class CommonOkHttpClient {
    private static OkHttpClient sClient;

    //static代码块，只会调用一次，所以这里类似于单例类
    static {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(Constant.TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(Constant.TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(Constant.TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.followRedirects(true);//允许重定向
        sClient = okHttpBuilder.build();
    }

    public static void post(Request request, DisposeDataHandle handle) {
        Call call = sClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }

    public static void get(Request request, DisposeDataHandle handle) {
        Call call = sClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
    }
}
