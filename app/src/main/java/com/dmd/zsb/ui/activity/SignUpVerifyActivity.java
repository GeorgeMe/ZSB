package com.dmd.zsb.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.R;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.SignUpVerifyPresenterImpl;
import com.dmd.zsb.mvp.view.SignUpVerifyView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.ToastView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import java.util.HashMap;

import butterknife.Bind;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SignUpVerifyActivity extends BaseActivity implements SignUpVerifyView, View.OnClickListener{

    @Bind(R.id.et_mobile)
    EditText etMobile;
    @Bind(R.id.et_verify_code)
    EditText etVerifyCode;
    @Bind(R.id.btn_get_verify_code_again)
    Button btnGetVerifyCodeAgain;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    @Bind(R.id.signup_verify_frame)
    FrameLayout signupVerifyFrame;

    private TimeCount mTime;
    private SignUpVerifyPresenterImpl signUpVerifyPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sign_up_verify;
    }
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return signupVerifyFrame;
    }

    @Override
    protected void initViewsAndEvents() {
        SMSSDK.initSDK(this, Constants.SMSAPPKEY, Constants.SMSAPPSECRET, true);
        etMobile.setOnClickListener(this);
        etVerifyCode.setOnClickListener(this);
        btnGetVerifyCodeAgain.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        signUpVerifyPresenter = new SignUpVerifyPresenterImpl(mContext, this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @Override
    public void onClick(View v) {
        String mobile = etMobile.getText().toString();
        String verify_code = etVerifyCode.getText().toString().trim();
        switch (v.getId()) {
            case R.id.tv_login:
                CloseKeyBoard();
                readyGoThenKill(SignInActivity.class);
                break;
            case R.id.btn_next:
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if ("".equals(verify_code)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_verify_code));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etVerifyCode.setText("");
                    etVerifyCode.requestFocus();
                }else {
                    //校验验证码
                    SMSSDK.submitVerificationCode("86",mobile,verify_code);
                }

                break;
            case R.id.btn_get_verify_code_again:
                if ("".equals(mobile)) {
                    ToastView toast = new ToastView(this, getString(R.string.please_input_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else if (mobile.length() < 11) {
                    ToastView toast = new ToastView(this, getString(R.string.wrong_mobile_phone));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etMobile.requestFocus();
                } else {
                    SMSSDK.getVerificationCode("86",mobile);
                    mTime = new TimeCount(60000, 1000);//构造CountDownTimer对象
                    mTime.start();
                    btnNext.requestFocus();
                }
                break;
        }
    }

    @Override
    public void navigateToSignUpView(JsonObject jsonObject) {
        if (jsonObject.get("flag").getAsBoolean() == true) {
            Bundle bundle = new Bundle();
            bundle.putString("mobile", etMobile.getText().toString());
            readyGo(SignUpActivity.class, bundle);
        } else {
            ToastView toast = new ToastView(this, "验证失败，请重试");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    // 关闭键盘
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etMobile.getWindowToken(), 0);
    }

    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Log.e(TAG_LOG,"提交验证码成功");
                    //提交验证码成功
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    Log.e(TAG_LOG,"获取验证码成功");
                    btnNext.requestFocus();
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    Log.e(TAG_LOG,"返回支持发送验证码的国家列表");
                    //返回支持发送验证码的国家列表

                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;

                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    JsonObject jsonObject=new JsonObject();
                    jsonObject.addProperty("mobile",phone);
                    jsonObject.addProperty("country",country);
                    signUpVerifyPresenter.checkVerifyCode(jsonObject);
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };
    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            btnGetVerifyCodeAgain.setText(getString(R.string.get_verify_code_again));
            btnGetVerifyCodeAgain.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            btnGetVerifyCodeAgain.setClickable(false);
            btnGetVerifyCodeAgain.setText(millisUntilFinished / 1000 + getString(R.string.resend_after));
        }
    }

}
