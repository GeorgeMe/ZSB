package com.dmd.zsb.mvp.presenter;

import com.dmd.zsb.entity.UserEntity;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2015/12/15.
 */
public interface HomePresenter {

   // void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh);
    void loadListData(JsonObject data);

    void onItemClickListener(int i,UserEntity data);
}
