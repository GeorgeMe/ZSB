package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.SeekResponse;
import com.dmd.zsb.mvp.interactor.impl.SeekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.SeekPresenter;
import com.dmd.zsb.mvp.view.SeekView;
import com.google.gson.JsonObject;


/**
 * Created by Administrator on 2015/12/22.
 */
public class SeekPresenterIml implements SeekPresenter,BaseMultiLoadedListener<SeekResponse> {
    private Context mContext=null;
    private SeekView mSeekView=null;
    private SeekInteractorImpl mCommonListInteractor = null;

    public SeekPresenterIml(Context mContext, SeekView mSeekView) {
        this.mContext = mContext;
        this.mSeekView = mSeekView;
        mCommonListInteractor = new SeekInteractorImpl(this);
    }

    @Override
    public void loadListData(JsonObject data) {
        mSeekView.hideLoading();
        if (!data.get("isSwipeRefresh").getAsBoolean()) {
            mSeekView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        //提交的参数封装
        JsonObject jsonObject=new JsonObject();
        String uid= XmlDB.getInstance(mContext).getKeyString("uid","uid");
        String sid=XmlDB.getInstance(mContext).getKeyString("sid","sid");
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("sid",sid);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
        jsonObject.addProperty("page",data.get("page").getAsString());//页码
        jsonObject.addProperty("subid",data.get("subid").getAsString());//科目id
        mCommonListInteractor.getCommonListData(data.get("event").getAsInt(),jsonObject);
    }

    @Override
    public void onItemClickListener(int position, UserEntity itemData) {
        mSeekView.navigateToUserDetail(position, itemData);
    }

    @Override
    public void onSuccess(int event_tag, SeekResponse data) {
        mSeekView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mSeekView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mSeekView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mSeekView.hideLoading();
        mSeekView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mSeekView.hideLoading();
        mSeekView.showException(msg);
    }
}
