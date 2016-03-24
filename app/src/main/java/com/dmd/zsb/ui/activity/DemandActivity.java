package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;

import butterknife.Bind;


public class DemandActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.bar_demand_back)
    TextView barDemandBack;
    @Bind(R.id.demand_levy_concentration)
    RadioButton demandLevyConcentration;
    @Bind(R.id.demand_to_be_completed)
    RadioButton demandToBeCompleted;
    @Bind(R.id.demand_has_been_completed)
    RadioButton demandHasBeenCompleted;
    @Bind(R.id.demand_menu_group)
    RadioGroup demandMenuGroup;
    @Bind(R.id.fragment_demand_list_view)
    LoadMoreListView fragmentDemandListView;
    @Bind(R.id.fragment_demand_list_swipe_layout)
    XSwipeRefreshLayout fragmentDemandListSwipeLayout;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_demand;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentDemandListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        barDemandBack.setOnClickListener(this);
        demandLevyConcentration.setOnClickListener(this);
        demandToBeCompleted.setOnClickListener(this);
        demandHasBeenCompleted.setOnClickListener(this);
        demandLevyConcentration.setChecked(true);
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
        if (v==barDemandBack){
            finish();
        }else if (v==demandLevyConcentration){

        }else if (v==demandToBeCompleted){

        }else if (v==demandHasBeenCompleted){

        }
    }

}
