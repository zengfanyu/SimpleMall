package com.zfy.simplemall;

import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.zfy.simplemall.activity.BaseActivity;
import com.zfy.simplemall.activity.MainActivity;
import com.zfy.simplemall.bean.LoginRespMsg;
import com.zfy.simplemall.config.Constant;
import com.zfy.simplemall.config.MallApplication;
import com.zfy.simplemall.listener.onToolbarRightButtonClickListener;
import com.zfy.simplemall.utils.CountTimerView;
import com.zfy.simplemall.utils.DESUtil;
import com.zfy.simplemall.utils.ManifestUtil;
import com.zfy.simplemall.utils.ToastUtils;
import com.zfy.simplemall.utils.okhttpplus.CommonOkHttpClient;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataHandle;
import com.zfy.simplemall.utils.okhttpplus.datadispose.DisposeDataListener;
import com.zfy.simplemall.utils.okhttpplus.exception.OkHttpException;
import com.zfy.simplemall.utils.okhttpplus.request.CommonRequest;
import com.zfy.simplemall.utils.okhttpplus.request.RequestParams;
import com.zfy.simplemall.widget.ClearEditText;
import com.zfy.simplemall.widget.SearchToolBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by ZFY on 2017/05/18.
 *
 * @function:用户注册的第二界面
 */

public class RegisterSecoActivity extends BaseActivity {
    @BindView(R.id.id_tool_bar)
    SearchToolBar mToolBar;
    @BindView(R.id.id_tv_tip)
    TextView mTvTip;
    @BindView(R.id.id_et_verification_code)
    ClearEditText mEtVerificationCode;
    @BindView(R.id.id_btn_resend)
    Button mBtnResend;
    private String mPhone;
    private String mPassword;
    private String mcode;
    private Object mExtraData;
    private SMSEventHandler mHandler;

    @Override
    protected int convertLayoutResId() {
        return R.layout.activity_register2;
    }

    @Override
    protected void initViews() {
        initSDK();
        getExtraData();
        initToolBar();
        setData();

    }

    private void initToolBar() {
        mToolBar.getRightButton().setText("完成");
        mToolBar.setRightButtonOnClickListener(new onToolbarRightButtonClickListener() {
            @Override
            public void onClick(View view) {
                submitCode();
            }
        });
    }

    private void submitCode() {
        String verifitioncode = mEtVerificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(verifitioncode)) {
            ToastUtils.showToast(this, "请输入验证码");
        }
        SMSSDK.submitVerificationCode(mcode, mPhone, verifitioncode);

    }

    private void initSDK() {
        SMSSDK.initSDK(this, ManifestUtil.getMetaDataValue(this, "mob_sms_appKey"),
                ManifestUtil.getMetaDataValue(this, "mob_sms_appSecrect"));
        mHandler = new SMSEventHandler();
        SMSSDK.registerEventHandler(mHandler);
    }


    @OnClick(R.id.id_btn_resend)
    public void onViewClicked() {

    }

    private void requestData() {
        Map<String, String> params = new HashMap<>(2);
        params.put("phone", mPhone);
        params.put("password", DESUtil.encode(Constant.DES_KEY, mPassword));
        CommonOkHttpClient.post(CommonRequest.createPostRequest(Constant.URL_LOGIN, new RequestParams(params)),
                new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        MallApplication application = MallApplication.getInstance();
                        LoginRespMsg userLoginRespMsg = (LoginRespMsg) responseObj;
                        Logger.d("status:"+userLoginRespMsg.getStatus());
                        application.putUser(userLoginRespMsg.getData(), userLoginRespMsg.getToken());
                        startActivity(new Intent(RegisterSecoActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        OkHttpException exception = (OkHttpException) reasonObj;
                        int ecode = exception.getEcode();
                        String emsg = (String) exception.getEmsg();
                        ToastUtils.showToast(RegisterSecoActivity.this, "emsg:" + emsg + ",ecode:" + ecode);
                    }
                },LoginRespMsg.class));
    }

    /**
     * 从Intent中拿到数据的方法
     *
     * @author zfy
     * @created at 2017/5/18/018 11:21
     */
    public void getExtraData() {
        Intent intent = getIntent();
        mPhone = intent.getStringExtra("phone");
        mPassword = intent.getStringExtra("password");
        mcode = intent.getStringExtra("countryCode");
    }

    /**
     * 现实Intent中的数据并且开始倒计时
     *
     * @author zfy
     * @created at 2017/5/18/018 11:22
     */
    private void setData() {
        String formatedPhone = "+" + mcode + " " + splitPhoneNum(mPhone);
        String tipText = getString(R.string.smssdk_send_mobile_detail) + formatedPhone;
        mTvTip.setText(Html.fromHtml(tipText));
        CountTimerView timerView = new CountTimerView(mBtnResend);
        timerView.start();
    }

    /**
     * 分割电话号码的方法
     *
     * @author zfy
     * @created at 2017/5/18/018 11:22
     */
    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        for (int i = 4, len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }
        builder.reverse();
        return builder.toString();
    }

    class SMSEventHandler extends EventHandler {
        @Override
        public void afterEvent(final int event, final int result, final Object data) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            requestData();
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
                                ToastUtils.showToast(RegisterSecoActivity.this, des);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mHandler);
    }
}
