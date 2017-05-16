package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zfy.simplemall.activity.LoginActivity;
import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.config.MallApplication;

/**
 * Fragment基类
 * Created by zfy on 2017/4/8.
 */

public abstract class BaseFragment extends Fragment {
    protected View mContentView;
    protected int mLayoutResId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutResId = convertLayoutResId();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(mLayoutResId, container, false);
        }
        ViewGroup parent = (ViewGroup) mContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mContentView);
        }
        initViews();
        return mContentView;
    }

    /**
     * 初始化布局的控件的方法
     *
     * @author zfy
     * @created at 2017/5/16/016 9:08
     */
    public abstract void initViews();

    /**
     * 传递布局文件资源id的方法
     *
     * @author zfy
     * @created at 2017/5/16/016 9:08
     */
    public abstract int convertLayoutResId();

    /**
    *跳转Activity并且判断是否需要进行登陆的方法
    *@author zfy
    *@param intent
    *@param  isNeedLogin
    *@created at 2017/5/16/016 10:33
    */
    protected void startActivity(Intent intent, boolean isNeedLogin) {
        if (isNeedLogin) {
            User user = MallApplication.getInstance().getUser();
            if (user != null) {
                super.startActivity(intent);
            } else {
                //此处首先保存目标Intent，然后跳转到登陆界面，登陆成功后才跳转到目标Intent
                MallApplication.getInstance().setTargetIntent(intent);
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                super.startActivity(loginIntent);
            }
        } else {
            super.startActivity(intent);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContentView = null;
    }
}
