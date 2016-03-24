package com.dmd.zsb.mvp.interactor;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2015/12/22.
 */
public interface SeekInteractor {
    void onAudition(final int event,JsonObject data);
}
