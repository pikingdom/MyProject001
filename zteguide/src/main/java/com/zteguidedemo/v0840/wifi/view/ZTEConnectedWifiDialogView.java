package com.zteguidedemo.v0840.wifi.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.v0840.wifi.Bean.WifiBean;
import com.zteguidedemo.v0840.wifi.Business.WifiConnect;
import com.zteguidedemo.v0840.wifi.Business.WifiHelper;
import com.zteguidedemo.v0840.wifi.util.InputMethodsManager;

/**
 * @Description: 连接wifi对话框
 * @author llf
 * @date 2017-08-07
 *
 */
public class ZTEConnectedWifiDialogView extends Dialog implements OnClickListener {

    private Context mContext;
    private TextView  mSsidTitle, mMessage;
    private TextView mNegativeButton, mPositiveButton;
    private String mNetWorkId;
    private String[] mParamArr;

    private WifiManager mWifiManager;

    @SuppressLint("WifiManagerLeak")
    public ZTEConnectedWifiDialogView(Context context, String param) {
        super(context, R.style.Connect_Dialog);
        mContext = context;
        mParamArr = param.split(",");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zx_connected_dialog_view);

        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.zx_ssid_title);
        mMessage = (TextView) findViewById(R.id.zx_message);
        mNegativeButton = (TextView) findViewById(R.id.zx_negativeButton);
        mPositiveButton = (TextView) findViewById(R.id.zx_positiveButton);
    }

    private void setListeners() {
        mNegativeButton.setOnClickListener(this);
        mPositiveButton.setOnClickListener(this);
    }

    private void setViews() {
        if (mParamArr == null || mParamArr.length == 0) return;
        this.mNetWorkId = mParamArr[4];
        mSsidTitle.setText(mParamArr[0]);
        mMessage.setText(mContext.getString(R.string.zx_wifi_connected_info, mParamArr[1] ,mParamArr[2] ,mParamArr[3]));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

         if (id == R.id.zx_negativeButton) {
         } else if (id == R.id.zx_positiveButton) {
             mWifiManager.removeNetwork(Integer.valueOf(mNetWorkId));
        }
        dismiss();
    }
}
