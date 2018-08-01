package com.huawei.hwstartupguide.ui.wifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.LinearLayout;

import com.bootreg.IGuideView;
import com.huawei.hwstartupguide.ChangeViewInterface;
import com.huawei.hwstartupguide.R;

/**
 * 作者：xiaomao on 2017/7/20.
 */

public class WifiSettingView extends LinearLayout{
    private Context context;
    private ChangeViewInterface changeViewInterface;

    public WifiSettingView(Context context, ChangeViewInterface changeViewInterface){
        super(context);
        this.context = context;
        this.changeViewInterface = changeViewInterface;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        //开启wifi
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        //进入到初始wifi设置界面
        intentToWifiSetupActivity(false);
    }

    /**
     * 进入到wifi具体设置界面
     */
    private void intentToWifiSetupActivity(boolean isFRPLocked) {
        Intent intent = new Intent();
        intent.setAction("com.android.net.wifi.SETUP_WIFI_NETWORK");
        intent.putExtra("frp_is_lock", isFRPLocked);
        intent.putExtra("extra_prefs_show_button_bar", true);
        intent.putExtra("extra_show_fake_status_bar", true);
        intent.putExtra("extra_from_package", "com.huawei.hwstartupguide");
        intent.putExtra("extra_prefs_set_back_text", "");
        intent.putExtra("extra_prefs_set_next_text", context.getString(R.string.skip_format));
        intent.putExtra("firstRun", true);
        ((Activity)context).startActivityForResult(intent, IGuideView.HW_REQUEST_WIFI_CODE);
        ((Activity)context).overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_from_alpah);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IGuideView.HW_REQUEST_WIFI_CODE) {
            switch (resultCode) {
//                case -1:
//                    if (StartupGuidePrefs.getInstance().isReopenFromBetaclub() && !NetworkUtil.isNetworkConnected(this.mContext)) {
//                        showConfirmDialog();
//                        return;
//                    } else if (isFRPLocked()) {
//                        intentToWifiSetupActivity(true);
//                        return;
//                    } else {
//                        this.mOOBEPageView.nextPage();
//                        return;
//                    }
//                case 0:
//                    if (StartupGuidePrefs.getInstance().isReopenFromBetaclub()) {
//                        intentToWifiSetupActivity(false);
//                        return;
//                    } else {
//                        this.mOOBEPageView.prevPage();
//                        return;
//                    }
//                case 1:
//                    HwCustPolandData mHwCustPolandData = (HwCustPolandData) HwCustUtils.createObj(HwCustPolandData.class, new Object[0]);
//                    if (mHwCustPolandData != null && mHwCustPolandData.showPolandData(this.mContext)) {
//                        this.mOOBEPageView.nextPage();
//                        return;
//                    }
//                    return;
//                default:
//                    return;
                case -1:
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_MIGRATION_VIEW);
                    break;
                case 0:
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SERVICE_ACCESS_EXPLAIN_VIEW);
                    break;
                case 1:
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_MIGRATION_VIEW);
                    break;
                default:
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.DATA_MIGRATION_VIEW);
                    break;
            }
        }
    }
}
