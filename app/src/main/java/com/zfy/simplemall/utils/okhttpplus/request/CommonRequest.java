package com.zfy.simplemall.utils.okhttpplus.request;

import android.text.TextUtils;

import com.zfy.simplemall.config.MallApplication;

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
        //此处加上token验证
        String token = MallApplication.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            urlBuilder.append("token").append("=").append(token).append("&");
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
    public static Request createPostRequest(String baseUrl, RequestParams params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        //此处加上token验证
        String token = MallApplication.getInstance().getToken();
        if (!TextUtils.isEmpty(token)) {
            formBodyBuilder.add("token", token);
        }
        FormBody body = formBodyBuilder.build();
        return new Request.Builder().post(body).url(baseUrl).build();
    }
}
