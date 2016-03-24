package com.dmd.zsb.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.AboutUsActivity;
import com.dmd.zsb.ui.activity.DemandActivity;
import com.dmd.zsb.ui.activity.EvaluationActivity;
import com.dmd.zsb.ui.activity.OrderActivity;
import com.dmd.zsb.ui.activity.VouchersActivity;
import com.dmd.zsb.ui.activity.WalletActivity;
import com.dmd.zsb.ui.activity.base.BaseFragment;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.bar_home_title)
    TextView barHomeTitle;
    @Bind(R.id.mine_sign_out_header_img)
    ImageView mineSignOutHeaderImg;
    @Bind(R.id.mine_sign_out_header)
    LinearLayout mineSignOutHeader;
    @Bind(R.id.mine_logout_header_img)
    ImageView mineLogoutHeaderImg;
    @Bind(R.id.mine_name)
    TextView mineName;
    @Bind(R.id.mine_job_type)
    TextView mineJobType;
    @Bind(R.id.mine_gender)
    TextView mineGender;
    @Bind(R.id.mine_address)
    TextView mineAddress;
    @Bind(R.id.mine_one_to_one)
    TextView mineOneToOne;
    @Bind(R.id.mine_one_to_many)
    TextView mineOneToMany;
    @Bind(R.id.mine_good_at_subjects)
    TextView mineGoodAtSubjects;
    @Bind(R.id.mine_modify_data)
    TextView mineModifyData;
    @Bind(R.id.mine_logout_header)
    LinearLayout mineLogoutHeader;
    @Bind(R.id.mine_wallet)
    TextView mineWallet;
    @Bind(R.id.mine_order)
    TextView mineOrder;
    @Bind(R.id.mine_evaluation)
    TextView mineEvaluation;
    @Bind(R.id.mine_vouchers)
    TextView mineVouchers;
    @Bind(R.id.mine_about_us)
    TextView mineAboutUs;
    @Bind(R.id.mine_switch_account)
    TextView mineSwitchAccount;
    @Bind(R.id.mine_sign_out)
    TextView mineSignOut;
    @Bind(R.id.mine_demand)
    TextView mineDemand;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mineWallet.setOnClickListener(this);
        mineOrder.setOnClickListener(this);
        mineDemand.setOnClickListener(this);
        mineEvaluation.setOnClickListener(this);
        mineVouchers.setOnClickListener(this);
        mineAboutUs.setOnClickListener(this);
        mineSwitchAccount.setOnClickListener(this);
        mineSignOut.setOnClickListener(this);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View v) {
        if (mineWallet == v) {
            readyGo(WalletActivity.class);
        } else if (mineOrder == v) {
            readyGo(OrderActivity.class);
        } else if (mineDemand == v) {
            readyGo(DemandActivity.class);
        } else if (mineEvaluation == v) {
            readyGo(EvaluationActivity.class);
        } else if (mineVouchers == v) {
            readyGo(VouchersActivity.class);
        } else if (mineAboutUs == v) {
            readyGo(AboutUsActivity.class);
        } else if (mineSwitchAccount == v) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.title)
                    .content("切换账户")
                    .positiveText(R.string.agree)
                    .negativeText(R.string.disagree)
                    .show();
        } else if (mineSignOut == v) {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.title)
                    .content("退出登录")
                    .positiveText(R.string.agree)
                    .negativeText(R.string.disagree)
                    .show();
        }
    }
}
