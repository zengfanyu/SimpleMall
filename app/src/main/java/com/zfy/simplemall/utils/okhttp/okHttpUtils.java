package com.zfy.simplemall.utils.okhttp;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zfy.simplemall.config.Constant;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp的帮助类
 * Created by Administrator on 2017/4/11/011.
 */

public class okHttpUtils {
    private volatile static OkHttpClient sClient = null;
    private Gson mGson;
    private Handler mUIHandler;

    private okHttpUtils() {
        mGson = new Gson();
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    public static okHttpUtils getInstance() {
        if (sClient == null) {
            synchronized (okHttpUtils.class) {
                if (sClient == null) {
                    sClient = new OkHttpClient();
                }
            }
        }

        return new okHttpUtils();
    }

    public void get(String url, BaseCallback callback) {
        Request request = buildRequest(url, null, Constant.HTTP_METHOD_GET);
        doRequest(request, callback);
    }

    public void post(String url, Map<String, String> params, BaseCallback callback) {
        Request request = buildRequest(url, params, Constant.HTTP_METHOD_POST);
        doRequest(request, callback);
    }

    public void doRequest(Request request, final BaseCallback callback) {

        callback.onRequestBefore(request);

        sClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String resultStr = response.body().string();
                if (response.isSuccessful()) {
                    if (callback.mType == String.class) {
                        mUIHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onSuccess(call, response, resultStr);
                            }
                        });

                    } else {
                        try {
                            final Object object = mGson.fromJson(resultStr, callback.mType);
                            mUIHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onSuccess(call, response, object);

                                }
                            });
                        } catch (final JsonSyntaxException e) {
                            mUIHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError(call, response, response.code(), e);

                                }
                            });
                        }
                    }


                } else {
                    callback.onError(call, response, response.code(), null);
                }
            }
        });
    }

    private Request buildRequest(String url, Map<String, String> params, int httpMethodType) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (httpMethodType == Constant.HTTP_METHOD_GET) {
            builder.get();
        } else if (httpMethodType == Constant.HTTP_METHOD_POST) {
            FormBody body = buildFormBody(params);
            builder.post(body);
        }
        return builder.build();
    }

    private FormBody buildFormBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }


}
