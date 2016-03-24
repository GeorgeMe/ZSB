package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.R;
import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.mvp.presenter.impl.UserDetailPresenterImpl;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.ui.activity.base.BaseActivity;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class UserDetailActivity extends BaseActivity implements UserDetailView,View.OnClickListener {


    @Bind(R.id.bar_user_detail_back)
    TextView barUserDetailBack;
    @Bind(R.id.user_img_header)
    ImageView userImgHeader;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_gender)
    TextView userGender;
    @Bind(R.id.user_praise)
    TextView userPraise;
    @Bind(R.id.user_audition)
    TextView userAudition;
    @Bind(R.id.user_appointment)
    TextView userAppointment;
    @Bind(R.id.user_follow)
    TextView userFollow;
    @Bind(R.id.teacher_identity)
    TextView teacherIdentity;
    @Bind(R.id.teacher_price)
    TextView teacherPrice;
    @Bind(R.id.appointment_time)
    TextView appointmentTime;
    @Bind(R.id.good_subjects)
    TextView goodSubjects;
    @Bind(R.id.teaching_place)
    TextView teachingPlace;
    @Bind(R.id.teaching_methods)
    TextView teachingMethods;
    @Bind(R.id.self_evaluation)
    TextView selfEvaluation;
    @Bind(R.id.good_subjects2)
    TextView goodSubjects2;
    @Bind(R.id.professional_accreditation)
    TextView professionalAccreditation;
    @Bind(R.id.send_msg)
    Button sendMsg;
    @Bind(R.id.user_loading_view)
    FrameLayout userLoadingView;

    private UserDetailPresenterImpl userDetailPresenter;
    private UserDetailResponse userDetailResponse;
    private String teacherId;
    @Override
    protected void getBundleExtras(Bundle extras) {
        teacherId=extras.getString("teacherId","0");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_detail;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return userLoadingView;
    }

    @Override
    protected void initViewsAndEvents() {
        userDetailPresenter=new UserDetailPresenterImpl(mContext,this);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("tid",teacherId);
        userDetailPresenter.getUserDetail(jsonObject);
        userAudition.setOnClickListener(this);
        userAppointment.setOnClickListener(this);
        userFollow.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        barUserDetailBack.setOnClickListener(this);
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
    public void setUserInfo(UserDetailResponse userInfo) {
        userDetailResponse=userInfo;
        Picasso.with(mContext).load(userInfo.getUserImgHeader()).into(userImgHeader);
        userName.setText(userInfo.getUserName());
        userGender.setText(userInfo.getUserGender());
        userPraise.setText(userInfo.getUserPraise());
        userAudition.setText(userInfo.getUserAudition());
        userAppointment.setText(userInfo.getUserAppointment());
        userFollow.setText(userInfo.getUserFollow());
        teacherIdentity.setText(userInfo.getTeacherIdentity());
        teacherPrice.setText(userInfo.getTeacherPrice());
        appointmentTime.setText(userInfo.getAppointmentTime());
        goodSubjects.setText(userInfo.getGoodSubjects());
        teachingPlace.setText(userInfo.getTeachingPlace());
        teachingMethods.setText(userInfo.getTeachingMethods());
        selfEvaluation.setText(userInfo.getSelfEvaluation());
        goodSubjects2.setText(userInfo.getGoodSubjects2());
        professionalAccreditation.setText(userInfo.getProfessionalAccreditation());
    }

    @Override
    public void userAppointment() {
        readyGoThenKill(ReleaseOrderActivity.class);
    }

    @Override
    public void userFollow() {
        showToast("关注");
    }

    @Override
    public void sendMsg() {
        showToast("发消息");
    }

    @Override
    public void onClick(View v) {
        if (v==userAppointment){
            userAppointment();
        }else if(v==sendMsg){
            sendMsg();
        }else if(v==userFollow){
            userFollow();
        }else if(v==barUserDetailBack){
            finish();
        }
    }
}
