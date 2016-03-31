package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.mvp.interactor.DemandInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemandInteractorImpl implements DemandInteractor {
    private BaseMultiLoadedListener<DemandResponse> loadedListener;

    public DemandInteractorImpl(BaseMultiLoadedListener<DemandResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void onDemand(final int event_tag, JsonObject jsonObject) {
        GsonRequest<DemandResponse> gsonRequest =new GsonRequest<DemandResponse>(UriHelper.getInstance().demand(jsonObject), null, new TypeToken<DemandResponse>() {
        }.getType(), new Response.Listener<DemandResponse>(){
            @Override
            public void onResponse(DemandResponse response) {
                loadedListener.onSuccess(event_tag,response);
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
