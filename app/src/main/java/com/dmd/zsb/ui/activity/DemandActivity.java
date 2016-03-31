package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.DemandEntity;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.mvp.presenter.impl.DemandPresenterImpl;
import com.dmd.zsb.mvp.view.DemandView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;


public class DemandActivity extends BaseActivity implements DemandView ,LoadMoreListView.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener{


    @Bind(R.id.demand_menu_group)
    RadioGroup demandMenuGroup;
    @Bind(R.id.fragment_demand_list_view)
    LoadMoreListView fragmentDemandListView;
    @Bind(R.id.fragment_demand_list_swipe_layout)
    XSwipeRefreshLayout fragmentDemandListSwipeLayout;
    @Bind(R.id.bar_demand_back)
    TextView barDemandBack;
    @Bind(R.id.demand_levy_concentration)
    RadioButton demandLevyConcentration;
    @Bind(R.id.demand_to_be_completed)
    RadioButton demandToBeCompleted;
    @Bind(R.id.demand_has_been_completed)
    RadioButton demandHasBeenCompleted;

    private DemandPresenterImpl demandPresenter;
    private ListViewDataAdapter<DemandEntity> mListViewAdapter;
    private int page=0;
    @Override
    protected void getBundleExtras(Bundle extras) {
        demandPresenter=new DemandPresenterImpl(mContext,this);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        demandPresenter.onDemand(Constants.EVENT_LOAD_MORE_DATA,jsonObject);
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
        mListViewAdapter=new ListViewDataAdapter<DemandEntity>(new ViewHolderCreator<DemandEntity>() {
            @Override
            public ViewHolderBase<DemandEntity> createViewHolder(int position) {
                return new ViewHolderBase<DemandEntity>() {
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        return null;
                    }

                    @Override
                    public void showData(int position, DemandEntity itemData) {

                    }
                };
            }
        });
        fragmentDemandListView.setAdapter(mListViewAdapter);
        fragmentDemandListView.setOnLoadMoreListener(this);
        fragmentDemandListView.setOnItemClickListener(this);

        fragmentDemandListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentDemandListSwipeLayout.setOnRefreshListener(this);

        demandLevyConcentration.setChecked(true);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void navigateToDemandDetail(int position, DemandEntity itemData) {
        showToast("我们");
    }

    @Override
    public void refreshListData(DemandResponse data) {
        if (fragmentDemandListSwipeLayout != null)
            fragmentDemandListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getDemandEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getDemandEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentDemandListView.setCanLoadMore(true);
            else
                fragmentDemandListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(DemandResponse data) {
        if (fragmentDemandListView != null)
            fragmentDemandListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getDemandEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                fragmentDemandListView.setCanLoadMore(true);
            else
                fragmentDemandListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DemandEntity demandEntity=(DemandEntity)parent.getItemAtPosition(position);
        navigateToDemandDetail(position,demandEntity);
    }

    @Override
    public void onLoadMore() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        demandPresenter.onDemand(Constants.EVENT_LOAD_MORE_DATA,jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA,jsonObject);
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

    @OnClick({R.id.bar_demand_back, R.id.demand_levy_concentration, R.id.demand_to_be_completed, R.id.demand_has_been_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_demand_back:
                finish();
                break;
            case R.id.demand_levy_concentration:
                JsonObject concentration=new JsonObject();
                concentration.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                concentration.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                concentration.addProperty("","");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA,concentration);
                break;
            case R.id.demand_to_be_completed:
                JsonObject to_be_completed=new JsonObject();
                to_be_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                to_be_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                to_be_completed.addProperty("","");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA,to_be_completed);
                break;
            case R.id.demand_has_been_completed:
                JsonObject has_been_completed=new JsonObject();
                has_been_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                has_been_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                has_been_completed.addProperty("","");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA,has_been_completed);
                break;
        }
    }
}
