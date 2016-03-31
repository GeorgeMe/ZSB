package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.dmd.zsb.entity.response.EvaluationResponse;
import com.dmd.zsb.mvp.interactor.EvaluationInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;


/**
 * Created by Administrator on 2016/3/29.
 */
public class EvaluationInteractorImpl implements EvaluationInteractor {
    private BaseMultiLoadedListener<EvaluationResponse> loadedListener;

    public EvaluationInteractorImpl(BaseMultiLoadedListener<EvaluationResponse> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void onEvaluation(final int event_tag,JsonObject jsonObject) {
        GsonRequest<EvaluationResponse> gsonRequest =new GsonRequest<EvaluationResponse>(UriHelper.getInstance().evaluation(jsonObject), null, new TypeToken<EvaluationResponse>() {
        }.getType(), new Response.Listener<EvaluationResponse>(){
            @Override
            public void onResponse(EvaluationResponse response) {
                loadedListener.onSuccess(event_tag,response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("evaluation");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
