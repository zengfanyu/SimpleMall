package com.zfy.simplemall.utils.okhttpplus.request;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used for build {@code CommonRequest }
 * Created by Administrator on 2017/4/11/011.
 */

public class RequestParams {
    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Object> fileParams = new ConcurrentHashMap<>();


    public RequestParams(Map<String, String> datas) {
        if (datas != null) {
            for (Map.Entry<String, String> entry : datas.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }


    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {
            {
                put(key, value);
            }
        });
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }

}
