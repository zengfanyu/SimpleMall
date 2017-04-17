package com.zfy.simplemall.config;

/**
 * 静态常量
 * Created by zfy on 2017/4/11/011.
 */

public class Constant {
    /**
     * url
     */
    public static final String URL_BASE = "http://112.124.22.238:8081/course_api/";
    //"http://112.124.22.238:8081/course_api/banner/query?type=1"
    public static final String URL_HOME_BANNER = URL_BASE + "banner/query";
    //http://112.124.22.238:8081/course_api/campaign/recommend
    public static final String URL_HOME_CAMPAIGN = URL_BASE + "campaign/recommend";
    //http://112.124.22.238:8081/course_api/wares/hot/?curPage=1&pageSize=10
    public static final String URL_HOT_WARES = URL_BASE + "wares/hot";
    //http://112.124.22.238:8081/course_api/wares/list?curPage=1&pageSize=10&categoryId=5
    public static final String URL_WARES_LIST = URL_BASE + "wares/list";
    //http://112.124.22.238:8081/course_api/category/list
    public static final String URL_CATEGORY_LIST = URL_BASE + "category/list";


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

    /**
     * MaterialRefreshLayout status
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_REFRESH = 2;
    public static final int STATE_MORE = 3;
}
