package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/25.
 */
public interface SettingPresenter {
    void uploadAvatar(JsonObject jsonObject);
    void onSignOut(JsonObject jsonObject);
}
