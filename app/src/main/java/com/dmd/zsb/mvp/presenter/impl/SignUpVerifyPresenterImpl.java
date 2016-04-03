package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.os.Bundle;

import com.dmd.zsb.mvp.interactor.impl.SignUpVerifyInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignUpVerifyPresenter;
import com.dmd.zsb.mvp.view.SignUpVerifyView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignUpVerifyPresenterImpl implements SignUpVerifyPresenter ,BaseSingleLoadedListener<JsonObject> {
    private Context mContext=null;
    private SignUpVerifyView signUpVerifyView;
    private SignUpVerifyInteractorImpl signUpVerifyInteractor;

    public SignUpVerifyPresenterImpl(Context mContext, SignUpVerifyView signUpVerifyView) {
        this.mContext = mContext;
        this.signUpVerifyView = signUpVerifyView;
        signUpVerifyInteractor=new SignUpVerifyInteractorImpl(this);
    }

    @Override
    public void checkVerifyCode(JsonObject jsonObject) {
        signUpVerifyInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        signUpVerifyView.navigateToSignUpView(data);
    }

    @Override
    public void onError(String msg) {
        signUpVerifyView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
