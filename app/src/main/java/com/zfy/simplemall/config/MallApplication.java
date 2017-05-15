package com.zfy.simplemall.config;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.utils.UserDataLocalUtils;

/**
 * Created by ZFY on 2017/4/13.
 *
 * @function:
 */

public class MallApplication extends Application {
    private User mUser;

    private static MallApplication sApplication;

    public static MallApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
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
}
