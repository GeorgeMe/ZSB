package com.dmd.zsb.mvp.interactor;

import com.google.gson.JsonObject;

/**
 *
 */
public interface CommonListInteractor {

   // void getCommonListData(String requestTag, final int event_tag, String keywords, int page);
    void getCommonListData(final int event,JsonObject data);
}
