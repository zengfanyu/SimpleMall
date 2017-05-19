package com.zfy.simplemall.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zfy.simplemall.R;
import com.zfy.simplemall.RegisterSecoActivity;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.utils.ManifestUtil;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.widget.ClearEditText;
import com.zfy.simplemall.widget.SearchToolBar;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by ZFY on 2017/05/17.
 *
 * @function:注册页面
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.id_tool_bar)
    SearchToolBar mToolBar;
    @BindView(R.id.id_tv_country_code)
    TextView mTvCountryCode;
    @BindView(R.id.id_et_phone)
    ClearEditText mEtPhone;
    @BindView(R.id.id_et_password)
    ClearEditText mEtPassword;
    @BindView(R.id.id_tv_country)
    TextView mTvCountry;
    private static final String DEFAULT_COUNTRY_ID = "42";
    private SMSEventHandler mHandler;
    private String mPhone;
    private String mCode;
    private String mPassword;

    @Override
    protected int convertLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        initToolBar();
        initSMSSDK();


    }

    private void initSMSSDK() {
        SMSSDK.initSDK(this, ManifestUtil.getMetaDataValue(this, "mob_sms_appKey"),
                ManifestUtil.getMetaDataValue(this,"mob_sms_appSecrect"));
        mHandler=new SMSEventHandler();
        SMSSDK.registerEventHandler(mHandler);
        String[] res = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
        if (res != null) {
            Logger.d("res[0]:" + res[0] + ",res[1]:" + res[1]);
            mTvCountryCode.setText("+"+res[1]);
            mTvCountry.setText(res[0]);
        }

        

    }

    private void initToolBar() {
        mToolBar.getRightButton().setText(R.string.next);
        mToolBar.setRightButtonOnClickListener(new onToolbarRightButtonClickListener() {
            @Override
            public void onClick(View view) {
                getCode();
            }
        });
    }

    private void getCode(){
        mPhone = mEtPhone.getText().toString().trim();
        mCode = mTvCountryCode.getText().toString().trim();
        mPassword = mEtPassword.getText().toString().trim();
        checkPhoneNum(mPhone, mCode);
        SMSSDK.getVerificationCode(mCode, mPhone);

    }
    /**
    *校验手机号码合法性的方法
    *@author zfy
    *@return void
    *@param phone
    *@param code
    *@created at 2017/5/18/018 10:09
    */
    private void checkPhoneNum(String phone, String code) {
        if (code.startsWith("+")) {
            code = code.substring(1);
        }

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(this, "请输入手机号码");
            return;
        }

        if (code == "86") {
            if(phone.length() != 11) {
                ToastUtils.showToast(this,"手机号码长度不对");
                return;
            }

        }

        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(phone);

        if (!m.matches()) {
            ToastUtils.showToast(this,"您输入的手机号码格式不正确");
            return;
        }

    }

    class SMSEventHandler extends EventHandler{
        @Override
        public void afterEvent(final int event,final int result,final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {


                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            // 请求验证码后，跳转到验证码填写页面
                            afterVerificationCodeRequested((Boolean) data);

                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {

                        }else if (event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){

                        }
                    } else {

                        // 根据服务器返回的网络错误，给toast提示
                        try {
                            ((Throwable) data).printStackTrace();
                            Throwable throwable = (Throwable) data;

                            JSONObject object = new JSONObject(
                                    throwable.getMessage());
                            String des = object.optString("detail");
                            if (!TextUtils.isEmpty(des)) {
                                ToastUtils.showToast(RegisterActivity.this, des);
                                return;
                            }
                        } catch (Exception e) {
                            SMSLog.getInstance().w(e);
                        }

                    }
                }
            });
        }
    }
    private void afterVerificationCodeRequested(boolean smart) {
        ToastUtils.showToast(this,"获取验证码成功");
        Intent intent=new Intent(this, RegisterSecoActivity.class);
        intent.putExtra("phone",mPhone);
        intent.putExtra("password",mPassword);
        intent.putExtra("countryCode",mCode);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mHandler);
    }
}
