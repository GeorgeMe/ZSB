package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.BriefInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.BriefPresenter;
import com.dmd.zsb.mvp.view.BriefView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class BriefPresenterImpl implements BriefPresenter,BaseSingleLoadedListener<JsonObject> {
    private BriefInteractorImpl briefInteractor;
    private Context mContext;
    private BriefView briefView;

    public BriefPresenterImpl(BriefView briefView, Context mContext) {
        this.briefView = briefView;
        this.mContext = mContext;
        briefInteractor=new BriefInteractorImpl(this);
    }

    @Override
    public void onChangeProfile(JsonObject jsonObject) {
        briefInteractor.onChangeProfile(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        briefView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        briefView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
