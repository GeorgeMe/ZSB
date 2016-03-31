package com.dmd.zsb.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WalletActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.wallet_transaction_detail)
    TextView walletTransactionDetail;
    @Bind(R.id.wallet_cumulative_class)
    TextView walletCumulativeClass;
    @Bind(R.id.wallet_earn_money)
    TextView walletEarnMoney;
    @Bind(R.id.wallet_recharge)
    TextView walletRecharge;
    @Bind(R.id.wallet_withdrawals)
    TextView walletWithdrawals;
    @Bind(R.id.wallet_bank_card)
    TextView walletBankCard;
    @Bind(R.id.wallet_vouchers)
    TextView walletVouchers;
    @Bind(R.id.tv_wallet_back)
    TextView tvWalletBack;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wallet;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        tvWalletBack.setOnClickListener(this);
        walletTransactionDetail.setOnClickListener(this);
        walletRecharge.setOnClickListener(this);
        walletWithdrawals.setOnClickListener(this);
        walletBankCard.setOnClickListener(this);
        walletVouchers.setOnClickListener(this);
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
        if (v == walletTransactionDetail) {

        } else if (v == tvWalletBack) {
            finish();
        } else if (v == walletRecharge) {
        } else if (v == walletWithdrawals) {
        } else if (v == walletBankCard) {
        } else if (v == walletVouchers) {

        }
    }

}
