package com.zteguidedemo.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.util.ScreenUtil;
import com.zteguidedemo.widget.AutoBgButton;

/**
 * Created by xuqunxing on 2017/7/18.
 */

public class AccountSettingsView extends BaseRelativilayout implements View.OnClickListener {

    private AutoBgButton loginTv;
    private AutoBgButton registerTv;
    public static final int REQUEST_CODE_WIFI_PICKER_LOGIN = 222;
    public static final int REQUEST_CODE_WIFI_PICKER_REGISTER = 111;
    private TextView previousTv;
    private TextView nextTv;
    private ImageView toolebarImageView;

    public AccountSettingsView(Context context) {
        this(context, null);
    }

    public AccountSettingsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccountSettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.my_account_settings_activity, this);
        toolebarImageView = (ImageView) findViewById(R.id.titlebar_imageview);
        loginTv = (AutoBgButton) findViewById(R.id.my_account_login);
        registerTv = (AutoBgButton) findViewById(R.id.my_account_register);
        previousTv = (TextView) findViewById(R.id.view_previous);
        nextTv = (TextView) findViewById(R.id.view_next);

        loginTv.setBackgroundColor(getContext().getResources().getColor(R.color.sign_in));
        registerTv.setBackgroundColor(getContext().getResources().getColor(R.color.sign_up));

        float scale = 720 * 1.0f/297;
        int currentScreenWidth = ScreenUtil.getCurrentScreenWidth(getContext());
        int imageHeigh = (int) (currentScreenWidth / scale);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) toolebarImageView.getLayoutParams();
        layoutParams.height = imageHeigh;

        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == loginTv) {
            launchZteAccountSettings(REQUEST_CODE_WIFI_PICKER_LOGIN);
        } else if (view == registerTv) {
            launchZteAccountSettings(REQUEST_CODE_WIFI_PICKER_REGISTER);
        } else if (view == nextTv) {
            if (onZteClickListener != null) {
                onZteClickListener.OnNext(this);
            }
        } else if (view == previousTv) {
            if (onZteClickListener != null) {
                onZteClickListener.OnPrevious(this);
            }
        }
    }

    private void launchZteAccountSettings(int requestCode) {
        if (!checkNetworkStatus(getContext())) {
            WifiManager wm = (WifiManager) getContext().getSystemService("wifi");
            if (!wm.isWifiEnabled()) {
                wm.setWifiEnabled(true);
            }
            try {
                Intent localIntent = new Intent("com.android.net.wifi.ZTE_SETUP_WIFI_NETWORK");
                localIntent.putExtra("wifi_enable_next_on_connect", true);
                localIntent.putExtra("wifiSwitchShow", false);
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).startActivityForResult(localIntent, requestCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (REQUEST_CODE_WIFI_PICKER_REGISTER == requestCode) {
            startZteAccountRegisterActivity();
        } else if (REQUEST_CODE_WIFI_PICKER_LOGIN == requestCode) {
            startZteAccountLoginActivity();
        }
    }

    public static boolean checkNetworkStatus(Context context) {
        NetworkInfo netInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_WIFI_PICKER_LOGIN == requestCode && -1 == resultCode) {
            startZteAccountLoginActivity();
        }
        if (REQUEST_CODE_WIFI_PICKER_REGISTER == requestCode && -1 == resultCode) {
            startZteAccountRegisterActivity();
        }

    }

    private void startZteAccountRegisterActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("org.zx.AuthComp", "org.zx.AuthActivity.SignUpVipActivity");
            intent.setComponent(cn);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startZteAccountLoginActivity() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("org.zx.AuthComp", "org.zx.AuthActivity.LoginActivity");
            intent.setComponent(cn);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
