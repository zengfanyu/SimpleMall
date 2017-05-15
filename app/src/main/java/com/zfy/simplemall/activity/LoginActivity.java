package com.zfy.simplemall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zfy.simplemall.R;
import com.zfy.simplemall.bean.LoginRespMsg;
import com.zfy.simplemall.bean.User;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.config.MallApplication;
import com.zfy.simplemall.utils.DESUtil;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.widget.ClearEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZFY on 2017/05/14.
 *
 * @function:
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ClearEditText mEtPhone;
    private ClearEditText mEtPassword;
    private TextView mTvRegister;
    private TextView mTvForget;
    private String mPhone;
    private String mPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }


    private void initViews() {
        mEtPhone = (ClearEditText) findViewById(R.id.id_et_phone);
        mEtPassword = (ClearEditText) findViewById(R.id.id_et_password);
        mTvRegister = (TextView) findViewById(R.id.id_tv_register);
        mTvForget = (TextView) findViewById(R.id.id_tv_forget);
        mBtnLogin = (Button) findViewById(R.id.id_btn_login);
        mBtnLogin.setOnClickListener(this);
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
                            LoginRespMsg<User> userLoginRespMsg = (LoginRespMsg) responseObj;
                            MallApplication.getInstance().putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                            setResult(RESULT_OK);
                            finish();

                        }

                        @Override
                        public void onFailure(Object reasonObj) {
                            OkHttpException exception = (OkHttpException) reasonObj;
                            int ecode = exception.getEcode();
                            String emsg = (String) exception.getEmsg();
                            ToastUtils.showToast(LoginActivity.this, "emsg:" + emsg + ",ecode:" + ecode);
                        }
                    }));
        }
    }

    @Override
    public void onClick(View v) {
        requestData();
    }
}
