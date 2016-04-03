package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;

import com.dmd.zsb.entity.response.SeekResponse;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/1/7.
 */
public class SeekInteractorImpl implements CommonListInteractor{

    private BaseMultiLoadedListener<SeekResponse> loadedListener = null;

    public SeekInteractorImpl(BaseMultiLoadedListener<SeekResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final int event,JsonObject data) {
        GsonRequest<SeekResponse> gsonRequest = new GsonRequest<SeekResponse>(
                UriHelper.getInstance().userList(data),
                null,
                new TypeToken<SeekResponse>() {
                }.getType(),
                new Response.Listener<SeekResponse>() {
                    @Override
                    public void onResponse(SeekResponse response) {
                        loadedListener.onSuccess(event, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loadedListener.onException(error.getMessage());
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("userList");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
