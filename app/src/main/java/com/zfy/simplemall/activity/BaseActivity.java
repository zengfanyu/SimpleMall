package com.zfy.simplemall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.config.MallApplication;
import com.zfy.simplemall.widget.SearchToolBar;

import butterknife.ButterKnife;

/**
 * Created by ZFY on 2017/05/16.
 *
 * @function:Activity的基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected SearchToolBar mToolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(convertLayoutResId());
        ButterKnife.bind(this);
        initViews();
    }

    public void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            User user = MallApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {
                //此处首先保存目标Intent，然后跳转到登陆界面，登陆成功后才跳转到目标Intent
                MallApplication.getInstance().setTargetIntent(intent);
                Intent loginIntent = new Intent(this, LoginActivity.class);
                super.startActivity(loginIntent);
            }
        } else {
            super.startActivity(intent);
        }
    }

    protected abstract int convertLayoutResId();

    protected abstract void initViews();
}
