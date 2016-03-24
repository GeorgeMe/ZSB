package com.dmd.zsb.ui.activity;

import android.content.Context;
import android.os.Bundle;
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
import com.dmd.zsb.mvp.presenter.impl.SignUpVerifyPresenterImpl;
import com.dmd.zsb.mvp.view.SignUpVerifyView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.ToastView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignUpVerifyActivity extends BaseActivity implements SignUpVerifyView, View.OnClickListener {
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
                } else if (verify_code.length() < 4 || !"1234".equals(verify_code)) {
                    ToastView toast = new ToastView(this, "输入验证码1234完成注册第一步");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etVerifyCode.requestFocus();
                } else if ("1234".equals(verify_code)) {
                    //校验验证码
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("mobile", mobile);
                    jsonObject.addProperty("verify_code", "1234");

                    signUpVerifyPresenter.checkVerifyCode(jsonObject);
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
                    ToastView toast = new ToastView(this, "输入验证码1234完成注册第一步");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etVerifyCode.requestFocus();
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

}
