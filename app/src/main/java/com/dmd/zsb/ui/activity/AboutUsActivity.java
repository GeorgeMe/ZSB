package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutUsActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.tv_about_us_back)
    TextView tvAboutUsBack;
    @Bind(R.id.about_app_name)
    TextView aboutAppName;
    @Bind(R.id.about_slogan)
    TextView aboutSlogan;
    @Bind(R.id.about_web)
    TextView aboutWeb;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about_us;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        tvAboutUsBack.setOnClickListener(this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v==tvAboutUsBack){
            finish();
        }
    }
}
