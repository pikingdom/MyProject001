package com.gionee.setupwizard.s10bl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;

/**
 * Created by xuqunxing on 2017/8/16.
 */
public class GineeAccountView extends RelativeLayout implements View.OnClickListener {

    private ChangeViewInterface ChangeViewInterface;
    private TextView nextTv;
    private Button loginBt;
    private RelativeLayout mainRl;
    private ToLoginAccountDialogView toLoginAccountDialogView;
    private Button registerBt;
    private TextView accountNotifyTv;

    public GineeAccountView(Context context) {
        super(context);
        initView();
    }

    public GineeAccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GineeAccountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public GineeAccountView(Context context, ChangeViewInterface ChangeViewInterface1) {
        super(context);
        this.ChangeViewInterface = ChangeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_sw_layout_account, this);
        mainRl = (RelativeLayout) findViewById(R.id.account_main);
        accountNotifyTv = (TextView) findViewById(R.id.gn_sw_layout_account_notify);
        registerBt = (Button) findViewById(R.id.gn_sw_layout_account_register);
        loginBt = (Button) findViewById(R.id.gn_sw_layout_account_login_btn);
        nextTv = (TextView) findViewById(R.id.gn_sw_layout_account_jump);
        nextTv.setOnClickListener(this);
        registerBt.setOnClickListener(this);
        loginBt.setOnClickListener(this);
        toLoginAccountDialogView = new ToLoginAccountDialogView(getContext());
        toLoginAccountDialogView.setVisibility(GONE);
        mainRl.addView(toLoginAccountDialogView);
        toLoginAccountDialogView.setOnGnClickListener(new AccountDialog.OnGnClickListener() {
            @Override
            public void onSubmitClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.gionee.account", "com.gionee.account.activity.LoginMainActivity");
                    if (!(getContext() instanceof Activity)) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                toLoginAccountDialogView.setVisibility(GONE);
            }

            @Override
            public void onCancleClick(View v) {
                toLoginAccountDialogView.setVisibility(GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == nextTv) {
            final AccountDialog accountDialog = new AccountDialog(getContext(),getContext().getResources().getString(R.string.account_dialog_title_wxts),
                    getContext().getResources().getString(R.string.gn_sw_string_term_of_user_dialog_tip),
                    getContext().getResources().getString(R.string.account_dialog_title_wxts_cancle),
                    getContext().getResources().getString(R.string.account_dialog_title_wxts_submit));
            accountDialog.show();
            accountDialog.setOnGnClickListener(new AccountDialog.OnGnClickListener() {
                @Override
                public void onSubmitClick(View v) {
                    accountDialog.dismiss();
                    if (ChangeViewInterface != null) {
                        ChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.TERM_OF_USE);
                    }
                }

                @Override
                public void onCancleClick(View v) {
                    accountDialog.dismiss();
                }
            });
        } else if (view == loginBt) {
            toLoginAccountDialogView.setVisibility(VISIBLE);
            toLoginAccountDialogView.showDialog();
        } else if (view == registerBt) {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            String simSer = tm.getSimSerialNumber();
            if (simSer == null || simSer.equals("")) {
                accountNotifyTv.setText(getContext().getResources().getString(R.string.account_dialog_title_wxts_no_find_sim));
            }else{
                try {
                    Intent intent = new Intent();
                    intent.setClassName("com.gionee.account", "com.gionee.account.activity.SmsDownRegisterStep1Activity");
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        ((Activity) getContext()).startActivity(intent);
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
