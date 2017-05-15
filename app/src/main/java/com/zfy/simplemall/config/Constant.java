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
    //http://112.124.22.238:8081/course_api/wares/campaign/list?campaignId=1&orderBy=0&curPage=1&pageSize=10
    public static final String URL_WARES_CAMPAIGN_LIST = URL_BASE + "wares/campaign/list";
    //http://112.124.22.238:8081/course_api/wares/detail.html
    public static final String URL_WARE_DETAIL = "http://112.124.22.238:8081/course_api/wares/detail.html";
    //http://112.124.22.238:8081/course_api/auth/login post方式 请求参数：phone password
    public static final String URL_LOGIN = URL_BASE + "auth/login";


    public static final String EXTRA_CAMPAIGN_ID = "campaign_id";
    public static final String EXTRA_CAMPAIGN_NAME = "campaign_name";
    public static final String EXTRA_WARE_NAME = "ware_name";
    //tab sort by
    public static final int TAB_TYPE_DEFAULT = 0;
    public static final int TAB_TYPE_SALES = 1;
    public static final int TAB_TYPE_PRICE = 2;

    public static final int TYPE_LIST = 1;
    public static final int TYPE_GIRD = 2;
    //Used for DES encode.same as the key in server
    public static final String DES_KEY = "Cniao5_123456";

    /**
     * the logic layer exception, may alter in different app
     */
    public static final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    public static final int RESULT_CODE_VALUE = 0;
    public static final String ERROR_MSG = "emsg";
    public static final String EMPTY_MSG = "";
    public static final String TOKEN_MSG = "token may be miss,error or expired check error code";
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
    public static final int TOKEN_MISS = 401;
    public static final int TOKEN_ERROR = 40;
    public static final int TOKEN_EXPIRED = 403;

    /**
     * MaterialRefreshLayout status
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_REFRESH = 2;
    public static final int STATE_MORE = 3;

    //sharedPreference key
    public static final String SP_JSON_USER_KEY = "SP_json_user_key";
    public static final String SP_TOKEN_KEY = "sp_token_key";

    //code used for MineFragment startActivityForResult
    public static final int START_LOGIN_ACTIVITY_CODE = 1;
}
