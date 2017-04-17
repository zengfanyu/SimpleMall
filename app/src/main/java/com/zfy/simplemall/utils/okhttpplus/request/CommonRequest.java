package com.zfy.simplemall.utils.okhttpplus.request;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/4/11/011.
 *
 * @function: 负责创建各种类型的Request对象，包括GET，POST，文件上传类型，文件下载类型
 */

public class CommonRequest {
    /**
     * Create GET Request
     *
     * @param baseUrl
     * @param params
     * @return Request
     * @author zfy
     * @created at 2017/4/11/011 15:32
     */
    public static Request createGetRequest(String baseUrl, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(baseUrl).append("?");
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder()
                .url(urlBuilder.substring(0, urlBuilder.length() - 1)) //去掉最后一个& 注意这个函数是佐凯有弊
                .get()
                .build();
    }

    /**
     * Create POST Request
     *
     * @param baseUrl
     * @param params
     * @return Request
     * @author zfy
     * @created at 2017/4/11/011 15:44
     */
    public static Request getPostRequest(String baseUrl, RequestParams params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody body = formBodyBuilder.build();
        return new Request.Builder().post(body).url(baseUrl).build();
    }
}
