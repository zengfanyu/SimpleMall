package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.LoginActivity;
import com.zfy.simplemall.utils.toastutils.ToastUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的 Tab Fragment
 * Created by zfy on 2017/4/8.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View mMineContentView;
    private TextView mTvUserName;
    private CircleImageView mIvImage;
    private FrameLayout mFlLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mMineContentView == null) {
            mMineContentView = inflater.inflate(R.layout.fragment_mine, container, false);
        }
        ViewGroup parent = (ViewGroup) mMineContentView.getParent();
        //缓存的View需要判断是否已经被加载过parent，如有，需要从parent移除，不然会报错
        if (parent != null) {
            parent.removeView(mMineContentView);
        }
        initViews();
        return mMineContentView;
    }

    @Override
    public void initViews() {
        findViews();
    }

    private void findViews() {
        mIvImage = (CircleImageView) mMineContentView.findViewById(R.id.id_img_head);
        mTvUserName = (TextView) mMineContentView.findViewById(R.id.id_tv_user_name);
        mFlLogin = (FrameLayout) mMineContentView.findViewById(R.id.id_fl_login);
        mFlLogin.setClickable(true);
        mFlLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        ToastUtils.showToast(getContext(), "click!");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMineContentView = null;
    }
}
