package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.interactor.impl.SettingInteracterImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SettingPresenter;
import com.dmd.zsb.mvp.view.SettingView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public class SettingPresenterImpl implements SettingPresenter,BaseSingleLoadedListener<JsonObject>{
    private Context mContext=null;
    private SettingInteracterImpl settingInteracter;
    private SettingView settingView;
    private OnUploadProcessListener uploadProcessListener;
    public SettingPresenterImpl(Context mContext, SettingView settingView,OnUploadProcessListener uploadProcessListener) {
        this.mContext = mContext;
        this.settingView = settingView;
        this.uploadProcessListener=uploadProcessListener;
        settingInteracter=new SettingInteracterImpl(this,uploadProcessListener);
    }

    @Override
    public void uploadAvatar(JsonObject jsonObject) {
        settingInteracter.uploadAvatar(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        settingView.showTip(data.get("msg").getAsString());
    }

    @Override
    public void onError(String msg) {
        settingView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void onSignOut(JsonObject jsonObject) {
        settingInteracter.onSignOut(jsonObject);
    }
}
