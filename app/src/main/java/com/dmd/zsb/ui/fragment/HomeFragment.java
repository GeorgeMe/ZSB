package com.dmd.zsb.ui.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.ninelayout.NineGridlayout;
import com.dmd.tutor.rollviewpager.RollPagerView;
import com.dmd.tutor.utils.BusHelper;
import com.dmd.tutor.utils.CommonUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.presenter.HomePresenter;
import com.dmd.zsb.mvp.presenter.impl.HomePresenterImpl;
import com.dmd.zsb.mvp.view.HomeView;
import com.dmd.zsb.ui.activity.DemandActivity;
import com.dmd.zsb.ui.activity.ReleaseOrderActivity;
import com.dmd.zsb.ui.activity.TestActivity;
import com.dmd.zsb.ui.activity.UserDetailActivity;
import com.dmd.zsb.ui.activity.base.BaseFragment;
import com.dmd.zsb.ui.adapter.HomeCarouselAdapter;
import com.dmd.zsb.ui.adapter.HomeCoursesAdapter;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener,NineGridlayout.OnItemClickListerner {

    @Bind(R.id.bar_home_address)
    TextView barHomeAddress;
    @Bind(R.id.bar_home_demand)
    TextView barHomeDemand;
    @Bind(R.id.fragment_home_list_view)
    LoadMoreListView fragmentHomeListView;
    @Bind(R.id.fragment_home_list_swipe_layout)
    XSwipeRefreshLayout fragmentHomeListSwipeLayout;

    private View mHeaderView;
    private RollPagerView mRollPagerView;
    private NineGridlayout mNineGridlayout;
    private ListViewDataAdapter<UserEntity> mListViewAdapter;
    private List<SubjectEntity> subjectList;
    private int page=0;
    private HomePresenter mHomePresenter=null;
    private JsonObject data;
    /**
     *
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    /**
     * 第一次用户可见
     */
    @Override
    protected void onFirstUserVisible() {
        data=new JsonObject();
        data.addProperty("page",page);
        data.addProperty("subid","");//科目id
        data.addProperty("event",Constants.EVENT_REFRESH_DATA);
        data.addProperty("isSwipeRefresh",false);

        mHomePresenter = new HomePresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentHomeListSwipeLayout) {
                fragmentHomeListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHomePresenter.loadListData(data);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHomePresenter.loadListData(data);
                }
            });
        }
        LocationManager.getInstance().refreshLocation();
    }

    /**
     * 用户可见
     */
    @Override
    protected void onUserVisible() {

    }

    /**
     * 用户不可见
     */
    @Override
    protected void onUserInvisible() {

    }

    /**
     * 加载视图的根视图
     */
    @Override
    protected View getLoadingTargetView() {
        return fragmentHomeListSwipeLayout;
    }

    /**
     * 初始化视图事件
     */
    @Override
    protected void initViewsAndEvents() {
        barHomeAddress.setText(XmlDB.getInstance(mContext).getKeyString("BDLocation","定位"));
        barHomeDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ReleaseOrderActivity.class);
            }
        });
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.tutor_home_list_header, null);
        mRollPagerView = (RollPagerView) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_roll_view_pager);
        mNineGridlayout = (NineGridlayout) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_nine_grid_layout);
        mListViewAdapter = new ListViewDataAdapter<UserEntity>(new ViewHolderCreator<UserEntity>() {

            @Override
            public ViewHolderBase<UserEntity> createViewHolder(int position) {
                return new ViewHolderBase<UserEntity>() {
                    ImageView tutor_list_teacher_header_img;
                    TextView tutor_list_teacher_name,
                            tutor_list_teacher_type,
                            tutor_list_teacher_sex,
                            tutor_list_teacher_time,
                            tutor_list_teacher_Level,
                            tutor_list_teacher_content,
                            tutor_list_teacher_one2one,
                            tutor_list_teacher_one2more,
                            tutor_list_teacher_distance;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.tutor_teacher_list_item, null);
                        tutor_list_teacher_header_img = ButterKnife.findById(view, R.id.tutor_list_teacher_header_img);
                        tutor_list_teacher_name = ButterKnife.findById(view, R.id.tutor_list_teacher_name);
                        tutor_list_teacher_type = ButterKnife.findById(view, R.id.tutor_list_teacher_type);
                        tutor_list_teacher_sex = ButterKnife.findById(view, R.id.tutor_list_teacher_sex);
                        tutor_list_teacher_time = ButterKnife.findById(view, R.id.tutor_list_teacher_time);
                        tutor_list_teacher_Level = ButterKnife.findById(view, R.id.tutor_list_teacher_Level);
                        tutor_list_teacher_content = ButterKnife.findById(view, R.id.tutor_list_teacher_content);
                        tutor_list_teacher_one2one = ButterKnife.findById(view, R.id.tutor_list_teacher_one2one);
                        tutor_list_teacher_one2more = ButterKnife.findById(view, R.id.tutor_list_teacher_one2more);
                        tutor_list_teacher_distance = ButterKnife.findById(view, R.id.tutor_list_teacher_distance);
                        return view;
                    }

                    @Override
                    public void showData(int position, UserEntity itemData) {
                        if (itemData != null) {

                            if (!CommonUtils.isEmpty(itemData.getAvatar())) {
                                Picasso.with(mContext).load(itemData.getAvatar()).into(tutor_list_teacher_header_img);
                            } else {
                                tutor_list_teacher_header_img.setImageResource(R.drawable.img_head_for_empty);
                            }

                            if (!CommonUtils.isEmpty(itemData.getUsername())) {
                                tutor_list_teacher_name.setText(itemData.getUsername());
                            } else {
                                tutor_list_teacher_name.setText("");
                            }

                            if (!CommonUtils.isEmpty(itemData.getRole())) {
                                tutor_list_teacher_type.setText("("+itemData.getRole()+")");
                            } else {
                                tutor_list_teacher_type.setText("");
                            }

                            if (!CommonUtils.isEmpty(itemData.getGender())) {
                                tutor_list_teacher_sex.setText(itemData.getGender());
                            } else {
                                tutor_list_teacher_sex.setText("");
                            }

                            if (!CommonUtils.isEmpty(itemData.getTotal_hours()+"")) {
                                tutor_list_teacher_time.setText(itemData.getTotal_hours()+"");
                            } else {
                                tutor_list_teacher_time.setText("");
                            }

                            if (!CommonUtils.isEmpty("")) {
                                tutor_list_teacher_Level.setText("");
                            } else {
                                tutor_list_teacher_Level.setText("未认证");
                            }

                            if (!CommonUtils.isEmpty(itemData.getSignature())) {
                                tutor_list_teacher_content.setText(itemData.getSignature());
                            } else {
                                tutor_list_teacher_content.setText("");
                            }

                            if (!CommonUtils.isEmpty("")) {
                                tutor_list_teacher_one2one.setText("");
                            } else {
                                tutor_list_teacher_one2one.setText("");
                            }

                            if (!CommonUtils.isEmpty("")) {
                                tutor_list_teacher_one2more.setText("");
                            } else {
                                tutor_list_teacher_one2more.setText("");
                            }

                            if (!CommonUtils.isEmpty(itemData.getLat())&&!CommonUtils.isEmpty(itemData.getLon())) {
                                tutor_list_teacher_distance.setText(LocationManager.getLocation(Double.parseDouble(itemData.getLat()),Double.parseDouble(itemData.getLon())));
                            } else {
                                tutor_list_teacher_distance.setText("");
                            }
                        }
                    }
                };
            }
        });

        //TODO 数据适配

        if (fragmentHomeListView.getHeaderViewsCount() == 0)
            fragmentHomeListView.addHeaderView(mHeaderView);
        fragmentHomeListView.setAdapter(mListViewAdapter);
        fragmentHomeListView.setOnItemClickListener(this);
        fragmentHomeListView.setOnLoadMoreListener(this);
        mNineGridlayout.setOnItemClickListerner(this);

        fragmentHomeListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentHomeListSwipeLayout.setOnRefreshListener(this);

    }

    /**
     * 绑定事件 true
     */
    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 接受事件
     */
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }
//===============================HomeView============================================

    @Override
    public void navigateToUserDetail(UserEntity data) {
        Bundle bundle=new Bundle();
        bundle.putString("teacherId",data.getUser_id());
        readyGo(UserDetailActivity.class,bundle);
    }

    @Override
    public void refreshListData(HomeResponse data) {
        if (fragmentHomeListSwipeLayout != null)
            fragmentHomeListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getUsers().size() >= 2) {//用户列表
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getUsers());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getAdvertisements().size() >= 2) {//广告
                mRollPagerView.setAdapter(new HomeCarouselAdapter(mContext, data.getAdvertisements()));
            }
            if (data.getSubjects().size() >= 2) {//科目
                subjectList=new ArrayList<SubjectEntity>();
                subjectList.addAll(data.getSubjects());
                mNineGridlayout.setAdapter(new HomeCoursesAdapter(mContext, data.getSubjects(), 5));
            }
            if (data.getTotal_page() > page)
                fragmentHomeListView.setCanLoadMore(true);
            else
                fragmentHomeListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(HomeResponse data) {
        if (fragmentHomeListView != null)
            fragmentHomeListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getUsers());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page()> page)
                fragmentHomeListView.setCanLoadMore(true);
            else
                fragmentHomeListView.setCanLoadMore(false);
        }
    }
    //==============================AdapterView.OnItemClickListener=============================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListViewAdapter != null) {
            int j = position + -1;
            if (j >= 0 && j < mListViewAdapter.getDataList().size()){
                mHomePresenter.onItemClickListener(j,mListViewAdapter.getDataList().get(j));
            }
        }
    }
    //==============================NineGridlayout.OnItemClickListerner=============================================

    @Override
    public void onItemClick(View view, int position) {
       SubjectEntity entity= subjectList.get(position);
        BusHelper.post(new EventCenter(Constants.EVENT_RECOMMEND_COURSES_HOME,entity));
    }
    //==============================LoadMoreListView.OnLoadMoreListener=============================================
    @Override
    public void onLoadMore() {
        page = 1 + page;
        data.addProperty("page",page);
        data.addProperty("subid","");
        data.addProperty("isSwipeRefresh",true);
        data.addProperty("event",Constants.EVENT_LOAD_MORE_DATA);
        mHomePresenter.loadListData(data);
    }
    //==============================SwipeRefreshLayout.OnRefreshListener=============================================


    @Override
    public void onRefresh() {

        data.addProperty("page",0);
        data.addProperty("subid","");
        data.addProperty("isSwipeRefresh",true);
        data.addProperty("event",Constants.EVENT_REFRESH_DATA);

        mHomePresenter.loadListData(data);
    }
}
