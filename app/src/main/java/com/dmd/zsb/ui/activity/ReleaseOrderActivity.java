package com.dmd.zsb.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.timepicker.ScreenInfo;
import com.dmd.tutor.timepicker.WheelMain;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.R;
import com.dmd.zsb.ui.activity.base.BaseActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;

public class ReleaseOrderActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.bar_demand_back)
    TextView barDemandBack;
    @Bind(R.id.publish_order_time)
    TextView publishOrderTime;
    @Bind(R.id.publish_order_location)
    EditText publishOrderLocation;
    @Bind(R.id.publish_order_text)
    EditText publishOrderText;
    @Bind(R.id.publish_order_publish)
    Button publishOrderPublish;
    @Bind(R.id.publish_order_price)
    EditText publishOrderPrice;

    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private WheelMain mWheelMain;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_release_order;
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
        barDemandBack.setOnClickListener(this);
        publishOrderTime.setOnClickListener(this);
        publishOrderPublish.setOnClickListener(this);
        Date date = new Date();
        publishOrderTime.setText(mFormat.format(date));
        publishOrderLocation.setText(XmlDB.getInstance(mContext).getKeyString("addr",""));
        //publishOrderText.setVisibility(View.GONE);
        //设置光标靠右
        CharSequence charSequencePirce = publishOrderPrice.getText();
        if (charSequencePirce instanceof Spannable) {
            Spannable spanText = (Spannable) charSequencePirce;
            Selection.setSelection(spanText, charSequencePirce.length());
        }
        CharSequence charSequenceLocation = publishOrderLocation.getText();
        if (charSequenceLocation instanceof Spannable) {
            Spannable spanText = (Spannable) charSequenceLocation;
            Selection.setSelection(spanText, charSequenceLocation.length());
        }
        publishOrderPrice.clearFocus();
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
        }else if (v==publishOrderTime){
            closeKeyBoard();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            final View timepickerview = inflater.inflate(R.layout.timepicker, null);
            ScreenInfo screenInfo = new ScreenInfo(this);
            mWheelMain = new WheelMain(timepickerview, true);
            mWheelMain.screenheight = screenInfo.getHeight();
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(mFormat.parse(publishOrderTime.getText().toString()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            mWheelMain.initDateTimePicker(year, month, day, hour, min);
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.choose_time))
                    .setView(timepickerview)
                    .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            publishOrderTime.setText(mWheelMain.getTime());
                        }
                    })
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }else if (v==publishOrderPublish){
            showToast("发布需求啊");
        }
    }
    // 关闭键盘
    public void closeKeyBoard() {
        publishOrderPrice.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(publishOrderPrice.getWindowToken(), 0);
    }
}
