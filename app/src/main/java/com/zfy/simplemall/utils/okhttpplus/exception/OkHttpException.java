package com.zfy.simplemall.utils.okhttpplus.exception;

/**
 * Created by Administrator on 2017/4/11/011.
 */

public class OkHttpException extends Exception {


    /**
     * the server return code
     */
    private int ecode;

    /**
     * the server return error message
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
