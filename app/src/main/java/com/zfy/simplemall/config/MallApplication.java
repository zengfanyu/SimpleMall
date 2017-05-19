package com.zfy.simplemall.config;

import android.app.Application;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.utils.UserDataLocalUtils;

import cn.smssdk.SMSSDK;

/**
 * Created by ZFY on 2017/4/13.
 *
 * @function:
 */

public class MallApplication extends Application {
    private User mUser;
    private Intent mTargetIntent;

    private static MallApplication sApplication;

    public static MallApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        SMSSDK.initSDK(this, "1deb5b5a3b81c", "cc09b21c8427a696abc37623fd6098d4");
        initUser();
        Fresco.initialize(this);
    }

    private void initUser() {
        this.mUser = UserDataLocalUtils.getUser(this);
    }

    public User getUser() {
        return mUser;
    }

    public void putUser(User user, String token) {
        this.mUser = user;
        UserDataLocalUtils.putUser(this, mUser);
        UserDataLocalUtils.putToken(this, token);
    }

    public void clearUser() {
        mUser = null;
        UserDataLocalUtils.clearUser(this);
        UserDataLocalUtils.clearToken(this);
    }

    public void clearToken() {
        UserDataLocalUtils.clearToken(this);
    }

    public String getToken() {
        return UserDataLocalUtils.getToken(this);
    }

    public Intent getTargetIntent() {
        return mTargetIntent;
    }

    public void setTargetIntent(Intent targetIntent) {
        this.mTargetIntent = targetIntent;
    }

    public void jumpToTargetActivity() {
        startActivity(mTargetIntent);
        mTargetIntent = null;

    }


}
