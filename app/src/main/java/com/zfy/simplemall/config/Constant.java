package com.zfy.simplemall.config;

/**
 * 静态常亮
 * Created by Administrator on 2017/4/11/011.
 */

public class Constant {
    //"http://112.124.22.238:8081/course_api/banner/query?type=1"
    public static final String URL_HOME_BANNER_BASE = "http://112.124.22.238:8081/course_api/banner/query";
    //http://112.124.22.238:8081/course_api/campaign/recommend
    public static final String URL_HOME_CAMPAIGN="http://112.124.22.238:8081/course_api/campaign/recommend";
    public static final int HTTP_METHOD_GET = 1;
    public static final int HTTP_METHOD_POST = 2;

    /**
     * the logic layer exception, may alter in different app
     */
    public static final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    public static final int RESULT_CODE_VALUE = 0;
    public static final String ERROR_MSG = "emsg";
    public static final String EMPTY_MSG = "";
    public static final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * the java layer exception, do not same to the logic error
     */
    public static final int NETWORK_ERROR = -1; // the network relative error
    public static final int JSON_ERROR = -2; // the JSON relative error
    public static final int OTHER_ERROR = -3; // the unknow error

    public static final int TIME_OUT = 30;
}
