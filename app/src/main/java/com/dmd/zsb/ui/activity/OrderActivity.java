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

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.bar_my_order_back)
    TextView barMyOrderBack;
    @Bind(R.id.my_order_group_menu_incomplete)
    RadioButton myOrderGroupMenuIncomplete;
    @Bind(R.id.my_order_group_menu_recent_completed)
    RadioButton myOrderGroupMenuRecentCompleted;
    @Bind(R.id.my_order_menu_group)
    RadioGroup myOrderMenuGroup;
    @Bind(R.id.fragment_my_order_list_list_view)
    LoadMoreListView fragmentMyOrderListListView;
    @Bind(R.id.fragment_my_order_list_swipe_layout)
    XSwipeRefreshLayout fragmentMyOrderListSwipeLayout;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentMyOrderListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        barMyOrderBack.setOnClickListener(this);
        myOrderGroupMenuIncomplete.setOnClickListener(this);
        myOrderGroupMenuRecentCompleted.setOnClickListener(this);
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
        if (v==barMyOrderBack){
            finish();
        }else if (v==myOrderGroupMenuIncomplete){

        }else if (v==myOrderGroupMenuRecentCompleted){

        }
    }
}
