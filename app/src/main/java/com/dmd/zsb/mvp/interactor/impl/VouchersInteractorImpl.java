package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.VouchersResponse;
import com.dmd.zsb.mvp.interactor.VouchersInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2016/3/28.
 */
public class VouchersInteractorImpl implements VouchersInteractor {
    private BaseMultiLoadedListener<VouchersResponse> loadedListener;

    public VouchersInteractorImpl(BaseMultiLoadedListener<VouchersResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void onVouchers(final int event_tag,JsonObject jsonObject) {
        GsonRequest<VouchersResponse> gsonRequest = new GsonRequest<VouchersResponse>(UriHelper.getInstance().vouchers(jsonObject), null, new TypeToken<VouchersResponse>() {
        }.getType(), new Response.Listener<VouchersResponse>() {
            @Override
            public void onResponse(VouchersResponse response) {
                loadedListener.onSuccess(event_tag,response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("vouchers");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
