package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface VouchersPresenter {
    void onVouchers(int event_tag,JsonObject jsonObject);
}
