package com.dmd.zsb.mvp.interactor;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandInteractor {
    void onDemand(int event_tag, JsonObject jsonObject);
}
