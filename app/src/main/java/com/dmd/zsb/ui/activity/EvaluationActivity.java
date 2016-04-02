package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.EvaluationEntity;
import com.dmd.zsb.entity.response.EvaluationResponse;
import com.dmd.zsb.mvp.presenter.impl.DemandPresenterImpl;
import com.dmd.zsb.mvp.presenter.impl.EvaluationPresenterImpl;
import com.dmd.zsb.mvp.view.EvaluationView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvaluationActivity extends BaseActivity implements EvaluationView ,LoadMoreListView.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{

    @Bind(R.id.evaluation_menu_group)
    RadioGroup evaluationMenuGroup;
    @Bind(R.id.fragment_evaluation_list_list_view)
    LoadMoreListView fragmentEvaluationListListView;
    @Bind(R.id.fragment_evaluation_list_swipe_layout)
    XSwipeRefreshLayout fragmentEvaluationListSwipeLayout;
    @Bind(R.id.bar_evaluation_back)
    TextView barEvaluationBack;
    @Bind(R.id.bar_evaluation_title)
    TextView barEvaluationTitle;
    @Bind(R.id.evaluation_group_menu_incomplete)
    RadioButton evaluationGroupMenuIncomplete;
    @Bind(R.id.evaluation_group_menu_recent_completed)
    RadioButton evaluationGroupMenuRecentCompleted;

    private EvaluationPresenterImpl evaluationPresenter;
    private ListViewDataAdapter<EvaluationEntity> mListViewAdapter;
    private int page=1;
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
        evaluationGroupMenuIncomplete.setChecked(true);
        evaluationPresenter = new EvaluationPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentEvaluationListSwipeLayout) {
                fragmentEvaluationListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.addProperty("page", page);
                        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                        if (evaluationGroupMenuIncomplete.isChecked()) {
                            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
                        } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                            jsonObject.addProperty("group_menu", "evaluationGroupMenuRecentCompleted");
                        }else {
                            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
                        }
                        evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.addProperty("page", page);
                    jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                    if (evaluationGroupMenuIncomplete.isChecked()) {
                        jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
                    } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
                        jsonObject.addProperty("group_menu", "evaluationGroupMenuRecentCompleted");
                    }else {
                        jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
                    }
                    evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }
        mListViewAdapter=new ListViewDataAdapter<EvaluationEntity>(new ViewHolderCreator<EvaluationEntity>(){


            @Override
            public ViewHolderBase<EvaluationEntity> createViewHolder(int position) {


                return new ViewHolderBase<EvaluationEntity>(){
                    ImageView img_header;
                    TextView tv_name, tv_type, tv_sex, tv_appointed_time, tv_charging, tv_curriculum,tv_note,tv_comment_level;
                    //定义UI控件
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                       // 实例化UI控件
                        View view = layoutInflater.inflate(R.layout.evaluation_list_item, null);
                        img_header = ButterKnife.findById(view, R.id.img_header);
                        tv_name = ButterKnife.findById(view, R.id.tv_name);
                        tv_type = ButterKnife.findById(view, R.id.tv_type);
                        tv_sex = ButterKnife.findById(view, R.id.tv_sex);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_note = ButterKnife.findById(view, R.id.tv_note);
                        tv_comment_level = ButterKnife.findById(view, R.id.tv_comment_level);
                        return view;
                    }

                    @Override
                    public void showData(int position, EvaluationEntity itemData) {
                        //数据展示set
                        if (evaluationGroupMenuIncomplete.isChecked()){
                            tv_note.setVisibility(View.GONE);
                            tv_comment_level.setText("评论");
                        }else if (evaluationGroupMenuRecentCompleted.isChecked()){
                            tv_note.setVisibility(View.VISIBLE);
                            tv_note.setText(itemData.getNote());
                            tv_comment_level.setText(itemData.getComment_level());
                        }
                        Picasso.with(mContext).load(ApiConstants.Urls.API_BASE_URLS+itemData.getImg_header()).into(img_header);
                        tv_name.setText(itemData.getName());
                        tv_type.setText(itemData.getType());
                        tv_sex.setText(itemData.getSex());
                        tv_appointed_time.setText(itemData.getAppointed_time());
                        tv_charging.setText(itemData.getCharging());
                        tv_curriculum.setText(itemData.getCurriculum());

                    }
                };
            }
        });
        fragmentEvaluationListListView.setAdapter(mListViewAdapter);
        fragmentEvaluationListListView.setOnItemClickListener(this);
        fragmentEvaluationListListView.setOnLoadMoreListener(this);

        fragmentEvaluationListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentEvaluationListSwipeLayout.setOnRefreshListener(this);

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
    public void navigateToEvaluationDetail(EvaluationEntity data) {
        //readyGo();
        showToast("评论");
    }

    @Override
    public void onLoadMore() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (evaluationGroupMenuIncomplete.isChecked()) {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
        } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuRecentCompleted");
        }else {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
        }
        evaluationPresenter.onEvaluation(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (evaluationGroupMenuIncomplete.isChecked()) {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
        } else if (evaluationGroupMenuRecentCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuRecentCompleted");
        }else {
            jsonObject.addProperty("group_menu", "evaluationGroupMenuIncomplete");
        }
        evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        EvaluationEntity evaluationEntity=(EvaluationEntity) parent.getItemAtPosition(position);
        navigateToEvaluationDetail(evaluationEntity);
    }

    @Override
    public void refreshListData(EvaluationResponse data) {
        if (fragmentEvaluationListSwipeLayout != null)
            fragmentEvaluationListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getEvaluationEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getEvaluationEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentEvaluationListListView.setCanLoadMore(true);
            else
                fragmentEvaluationListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(EvaluationResponse data) {
        if (fragmentEvaluationListListView != null)
            fragmentEvaluationListListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getEvaluationEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                fragmentEvaluationListListView.setCanLoadMore(true);
            else
                fragmentEvaluationListListView.setCanLoadMore(false);
        }
    }


    @OnClick({R.id.bar_evaluation_back, R.id.evaluation_group_menu_incomplete, R.id.evaluation_group_menu_recent_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_evaluation_back:
                finish();
                break;
            case R.id.evaluation_group_menu_incomplete:
                JsonObject evaluation_group_menu_incomplete = new JsonObject();
                evaluation_group_menu_incomplete.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                evaluation_group_menu_incomplete.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                evaluation_group_menu_incomplete.addProperty("page", 1);
                evaluation_group_menu_incomplete.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                evaluation_group_menu_incomplete.addProperty("group_menu", "evaluationGroupMenuIncomplete");
                evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, evaluation_group_menu_incomplete);
                break;
            case R.id.evaluation_group_menu_recent_completed:
                JsonObject evaluation_group_menu_recent_completed = new JsonObject();
                evaluation_group_menu_recent_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                evaluation_group_menu_recent_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                evaluation_group_menu_recent_completed.addProperty("page", 1);
                evaluation_group_menu_recent_completed.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                evaluation_group_menu_recent_completed.addProperty("group_menu", "evaluationGroupMenuRecentCompleted");
                evaluationPresenter.onEvaluation(Constants.EVENT_REFRESH_DATA, evaluation_group_menu_recent_completed);
                break;
        }
    }
}
