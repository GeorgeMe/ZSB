package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VouchersActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.tv_vouchers_back)
    TextView tvVouchersBack;
    @Bind(R.id.vouchers_list_view)
    LoadMoreListView vouchersListView;
    @Bind(R.id.vouchers_list_swipe_layout)
    XSwipeRefreshLayout vouchersListSwipeLayout;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vouchers;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return vouchersListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        tvVouchersBack.setOnClickListener(this);
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
        if (v==tvVouchersBack){
            finish();
        }
    }
}
