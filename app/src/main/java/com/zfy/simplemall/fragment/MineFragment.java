package com.zfy.simplemall.fragment;

import android.content.Intent;
import android.view.View;
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
    private TextView mTvUserName;
    private CircleImageView mIvImage;
    private FrameLayout mFlLogin;
    private Button mBtnLogout;


    @Override
    public void initViews() {
        findViews();
        showUser(MallApplication.getInstance().getUser());
    }

    @Override
    public int convertLayoutResId() {
        return R.layout.fragment_mine;
    }

    private void findViews() {
        mIvImage = (CircleImageView) mContentView.findViewById(R.id.id_img_head);
        mTvUserName = (TextView) mContentView.findViewById(R.id.id_tv_user_name);
        mFlLogin = (FrameLayout) mContentView.findViewById(R.id.id_fl_login);
        mBtnLogout = (Button) mContentView.findViewById(R.id.id_btn_logout);
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

}
