package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.mvp.interactor.UserDetailInteractor;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/18.
 */
public class UserDetailInteractorImpl implements UserDetailInteractor {

    private BaseSingleLoadedListener<UserDetailResponse> loadedListener;

    public UserDetailInteractorImpl(BaseSingleLoadedListener<UserDetailResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getUserDetail(JsonObject jsonObject) {
        GsonRequest<UserDetailResponse> gsonRequest=new GsonRequest<UserDetailResponse>(UriHelper.getInstance().userDetail(jsonObject),null,new TypeToken<UserDetailResponse>(){}.getType(), new Response.Listener<UserDetailResponse>(){
            @Override
            public void onResponse(UserDetailResponse response) {
                loadedListener.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("userDetail");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
