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

import com.dmd.tutor.adapter.LazyViewHolderCreator;
import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.R;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.response.OrderResponse;
import com.dmd.zsb.mvp.presenter.impl.OrderPresenterImpl;
import com.dmd.zsb.mvp.view.OrderView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends BaseActivity implements OrderView,LoadMoreListView.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener {

    @Bind(R.id.my_order_menu_group)
    RadioGroup myOrderMenuGroup;
    @Bind(R.id.fragment_my_order_list_list_view)
    LoadMoreListView fragmentMyOrderListListView;
    @Bind(R.id.fragment_my_order_list_swipe_layout)
    XSwipeRefreshLayout fragmentMyOrderListSwipeLayout;
    @Bind(R.id.bar_my_order_back)
    TextView barMyOrderBack;
    @Bind(R.id.my_order_group_menu_incomplete)
    RadioButton myOrderGroupMenuIncomplete;
    @Bind(R.id.my_order_group_menu_recent_completed)
    RadioButton myOrderGroupMenuRecentCompleted;

    private OrderPresenterImpl orderPresenter;
    private ListViewDataAdapter<OrderEntity> mListViewAdapter;
    private int page=0;

    @Override
    protected void getBundleExtras(Bundle extras) {
        orderPresenter = new OrderPresenterImpl(mContext, this);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA,jsonObject);
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
        mListViewAdapter=new ListViewDataAdapter<OrderEntity>(new ViewHolderCreator<OrderEntity>(){
            @Override
            public ViewHolderBase<OrderEntity> createViewHolder(int position) {
                return new ViewHolderBase<OrderEntity>() {
                    ImageView img_header;
                    TextView tv_name,tv_type,tv_sex,tv_appointed_time,tv_charging,tv_curriculum,tv_address,tv_place,tv_state;
                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view=layoutInflater.inflate(R.layout.order_list_item,null);
                        img_header=ButterKnife.findById(view, R.id.img_header);
                        tv_type=ButterKnife.findById(view, R.id.tv_type);
                        tv_sex=ButterKnife.findById(view, R.id.tv_sex);
                        tv_appointed_time=ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging=ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum=ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_address=ButterKnife.findById(view, R.id.tv_address);
                        tv_place=ButterKnife.findById(view, R.id.tv_place);
                        tv_state=ButterKnife.findById(view, R.id.tv_state);
                        return null;
                    }

                    @Override
                    public void showData(int position, OrderEntity itemData) {

                    }
                };
            }
        });

        fragmentMyOrderListListView.setAdapter(mListViewAdapter);
        fragmentMyOrderListListView.setOnLoadMoreListener(this);
        fragmentMyOrderListListView.setOnItemClickListener(this);

        fragmentMyOrderListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentMyOrderListSwipeLayout.setOnRefreshListener(this);
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
    public void navigateToOrderDetail(OrderEntity data) {

    }

    @Override
    public void refreshListData(OrderResponse data) {
        if (fragmentMyOrderListSwipeLayout != null)
            fragmentMyOrderListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getOrderEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentMyOrderListListView.setCanLoadMore(true);
            else
                fragmentMyOrderListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(OrderResponse data) {
        if (fragmentMyOrderListListView != null)
            fragmentMyOrderListListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getOrderEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
                fragmentMyOrderListListView.setCanLoadMore(true);
            else
                fragmentMyOrderListListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        OrderEntity orderEntity=(OrderEntity)parent.getItemAtPosition(position);
        navigateToOrderDetail(orderEntity);
    }

    @Override
    public void onLoadMore() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        orderPresenter.onOrder(Constants.EVENT_LOAD_MORE_DATA,jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("","");
        orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA,jsonObject);
    }

    @OnClick({R.id.bar_my_order_back, R.id.my_order_group_menu_incomplete, R.id.my_order_group_menu_recent_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bar_my_order_back:
                finish();
                break;
            case R.id.my_order_group_menu_incomplete:
                JsonObject incomplete=new JsonObject();
                incomplete.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                incomplete.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                incomplete.addProperty("","");
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA,incomplete);
                break;
            case R.id.my_order_group_menu_recent_completed:
                JsonObject recent_completed=new JsonObject();
                recent_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                recent_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                recent_completed.addProperty("","");
                orderPresenter.onOrder(Constants.EVENT_REFRESH_DATA,recent_completed);
                break;
        }
    }
}
