package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zfy.simplemall.R;
import com.zfy.simplemall.activity.LoginActivity;
import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.config.MallApplication;

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
    private Button mBtnLogout;

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
        showUser(MallApplication.getInstance().getUser());
    }

    private void findViews() {
        mIvImage = (CircleImageView) mMineContentView.findViewById(R.id.id_img_head);
        mTvUserName = (TextView) mMineContentView.findViewById(R.id.id_tv_user_name);
        mFlLogin = (FrameLayout) mMineContentView.findViewById(R.id.id_fl_login);
        mBtnLogout = (Button) mMineContentView.findViewById(R.id.id_btn_logout);
        mFlLogin.setClickable(true);
        mFlLogin.setOnClickListener(this);
        mBtnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.id_btn_logout) {
            // TODO: 2017/5/15/015 清理User数据，恢复按钮为不可见，恢复默认布局
            MallApplication.getInstance().clearUser();
            showUser(null);

        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivityForResult(intent, Constant.START_LOGIN_ACTIVITY_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user = MallApplication.getInstance().getUser();
        showUser(user);
    }

    private void showUser(User user) {
        if (user != null) {
            mTvUserName.setText(user.getUsername());
            Picasso.with(getActivity()).load(user.getLogo_url()).into(mIvImage);
            mBtnLogout.setVisibility(View.VISIBLE);
        } else {
            mTvUserName.setText(R.string.to_login);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMineContentView = null;
    }
}
