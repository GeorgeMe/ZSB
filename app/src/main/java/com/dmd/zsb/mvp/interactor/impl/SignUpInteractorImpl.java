package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.mvp.interactor.SignUpInteractor;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignUpInteractorImpl implements SignUpInteractor {
    private BaseSingleLoadedListener<JsonObject> loadedListener;

    public SignUpInteractorImpl(BaseSingleLoadedListener<JsonObject> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void signUp(JsonObject data) {
        GsonRequest<JsonObject> gsonRequest=new GsonRequest<JsonObject>(UriHelper.getInstance().signUp(data),null,new TypeToken<JsonObject>(){}.getType(), new Response.Listener<JsonObject>(){
            @Override
            public void onResponse(JsonObject response) {
                loadedListener.onSuccess(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("signUp");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
