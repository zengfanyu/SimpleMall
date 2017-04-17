package com.zfy.simplemall.config;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ZFY on 2017/4/13.
 *
 * @function:
 */

public class MallApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }


}
