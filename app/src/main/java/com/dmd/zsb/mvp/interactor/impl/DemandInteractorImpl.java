package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemandInteractorImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<DemandResponse> loadedListener;

    public DemandInteractorImpl(BaseMultiLoadedListener<DemandResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final int event, JsonObject gson) {
        GsonRequest<DemandResponse> gsonRequest =new GsonRequest<DemandResponse>(UriHelper.getInstance().demand(gson), null, new TypeToken<DemandResponse>() {
        }.getType(), new Response.Listener<DemandResponse>(){
            @Override
            public void onResponse(DemandResponse response) {
                loadedListener.onSuccess(event,response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("demand");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
