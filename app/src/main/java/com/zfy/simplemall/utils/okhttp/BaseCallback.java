package com.zfy.simplemall.utils.okhttp;

import com.google.gson.internal.$Gson$Types;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/11/011.
 */

public abstract class BaseCallback<T> {

    Type mType;

    private static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType pt = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(pt.getActualTypeArguments()[0]);
    }

    public BaseCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    public abstract void onRequestBefore(Request request);

    public abstract void onFailure(Call call, IOException e);

    public abstract void onSuccess(Call call, Response response,T t);

    public abstract void onError(Call call, Response response, int errorCode, Exception e);
}
