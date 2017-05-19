package com.zfy.simplemall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.LoginRespMsg;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.config.MallApplication;
import com.zfy.simplemall.utils.DESUtil;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZFY on 2017/05/14.
 *
 * @function:登陆界面的Activity
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ClearEditText mEtPhone;
    private ClearEditText mEtPassword;
    private TextView mTvRegister;
    private TextView mTvForget;
    private String mPhone;
    private String mPassword;
    private Button mBtnLogin;

    @Override
    protected int convertLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        initView();
    }


    private void initView() {
        mEtPhone = (ClearEditText) findViewById(R.id.id_et_phone);
        mEtPassword = (ClearEditText) findViewById(R.id.id_et_password);
        mTvRegister = (TextView) findViewById(R.id.id_tv_register);
        mTvForget = (TextView) findViewById(R.id.id_tv_forget);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mTvRegister.setClickable(true);
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_login:
                requestData();
                break;
            case R.id.id_tv_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.id_tv_forget:
                break;
            default:
                break;
        }
    }

    private void requestData() {
        mPhone = mEtPhone.getText().toString().trim();
        mPassword = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mPassword)) {
            ToastUtils.showToast(this, "Please input account or password!");
        } else {
            Map<String, String> params = new HashMap<>(2);
            params.put("phone", mPhone);
            params.put("password", DESUtil.encode(Constant.DES_KEY, mPassword));
            CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.URL_LOGIN, new RequestParams(params)),
                    new DisposeDataHandle(new DisposeDataListener() {
                        @Override
                        public void onSuccess(Object responseObj) {
                            MallApplication application = MallApplication.getInstance();
                            LoginRespMsg userLoginRespMsg = (LoginRespMsg) responseObj;
                            Logger.d("status:" + userLoginRespMsg.getStatus());
                            application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                            if (application.getTargetIntent() == null) {
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                startActivity(application.getTargetIntent());
                                finish();
                            }

                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            OkHttpException exception = (OkHttpException) reasonObj;
                            int ecode = exception.getEcode();
                            String emsg = (String) exception.getEmsg();
                            ToastUtils.showToast(LoginActivity.this, "emsg:" + emsg + ",ecode:" + ecode);
                        }
                    }, LoginRespMsg.class));
        }
    }
}
