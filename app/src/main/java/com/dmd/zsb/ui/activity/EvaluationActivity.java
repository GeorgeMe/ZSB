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

public class EvaluationActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.bar_evaluation_back)
    TextView barEvaluationBack;
    @Bind(R.id.evaluation_group_menu_incomplete)
    RadioButton evaluationGroupMenuIncomplete;
    @Bind(R.id.evaluation_group_menu_recent_completed)
    RadioButton evaluationGroupMenuRecentCompleted;
    @Bind(R.id.evaluation_group_menu_my_evaluation)
    RadioButton evaluationGroupMenuMyEvaluation;
    @Bind(R.id.evaluation_menu_group)
    RadioGroup evaluationMenuGroup;
    @Bind(R.id.fragment_evaluation_list_list_view)
    LoadMoreListView fragmentEvaluationListListView;
    @Bind(R.id.fragment_evaluation_list_swipe_layout)
    XSwipeRefreshLayout fragmentEvaluationListSwipeLayout;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_evaluation;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentEvaluationListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        barEvaluationBack.setOnClickListener(this);
        evaluationGroupMenuIncomplete.setOnClickListener(this);
        evaluationGroupMenuRecentCompleted.setOnClickListener(this);
        evaluationGroupMenuMyEvaluation.setOnClickListener(this);
        evaluationGroupMenuIncomplete.setChecked(true);
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
        if (v==barEvaluationBack){
            finish();
        }else if (v==evaluationGroupMenuIncomplete){

        }else if (v==evaluationGroupMenuRecentCompleted){

        }else if (v==evaluationGroupMenuMyEvaluation){

        }
    }
}
