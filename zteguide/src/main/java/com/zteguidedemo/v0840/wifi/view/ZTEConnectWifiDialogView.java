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
import android.widget.ImageView;
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
public class ZTEConnectWifiDialogView extends Dialog implements OnClickListener {

    private Context mContext;
    private EditText mIdentity, mWifiPasswd;
    private TextView mIdentityTxt, mSsidTitle;

    private TextView mCancleBtn;
    private TextView mConnectBtn;
    private CheckBox mShwoPasswordBtn;

    private String mSsid;
    private String mCapabilities;
    private WifiHelper.PskType mPskType;

    private WifiManager mWifiManager;

    @SuppressLint("WifiManagerLeak")
    public ZTEConnectWifiDialogView(Context activity, String ssid, String capabilities) {
        super(activity, R.style.Connect_Dialog);
        mContext = activity;
        mSsid = ssid;
        mCapabilities = capabilities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zx_connect_wifi_dialog_view);

        //弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.zx_ssid_title);
        mIdentityTxt = (TextView) findViewById(R.id.zx_identity_txt);
        mIdentity = (EditText) findViewById(R.id.zx_identity_edit);
        mWifiPasswd = (EditText) findViewById(R.id.zx_wifi_passwd);
        mCancleBtn = (TextView) findViewById(R.id.zx_wifi_button_cancle);
        mConnectBtn = (TextView) findViewById(R.id.zx_wifi_button_connect);
        mShwoPasswordBtn = (CheckBox) findViewById(R.id.zx_wifi_showpasswd_checkbox);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mConnectBtn.setOnClickListener(this);
        mShwoPasswordBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mWifiPasswd.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
                mWifiPasswd.requestFocus();
                int length = mWifiPasswd.getText().toString().trim().length();
                mWifiPasswd.setSelection(length);
            }
        });

    }

    private void setViews() {

        mSsidTitle.setText(mSsid);
        mIdentity.requestFocus();

        mPskType = WifiHelper.getSecurityType(mCapabilities);
        showKeyBoard();
        showOrHideIdentityView();
    }

    public void showKeyBoard() {
        if (mIdentity != null) {
            InputMethodsManager.showKeyboard(mIdentity);
        }
    }

    private void showOrHideIdentityView() {
        if (mCapabilities != null && mCapabilities.contains("WPA2-EAP")) {
            mIdentity.setVisibility(View.VISIBLE);
            mIdentityTxt.setVisibility(View.VISIBLE);
        }else {
            mIdentity.setVisibility(View.GONE);
            mIdentityTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

         if (id == R.id.zx_wifi_button_cancle) {
            dismissDialog();

        } else if (id == R.id.zx_wifi_button_connect) {
            InputMethodsManager.hideKeyboard(mIdentity);
            WifiConnect wc = new WifiConnect(mWifiManager);
            WifiBean bean = new WifiBean();
            bean.setSsid(mSsid);
            bean.setCapabilities(mCapabilities);
            bean.setPasswd(mWifiPasswd.getText().toString());
            if (mIdentity.getVisibility() == View.VISIBLE) {
                String identityName = mIdentity.getText().toString().trim();
                bean.setIdentity(identityName);
            }
            bean.setPskType(mPskType);
            wc.connect(bean);

            dismissDialog();

        }
    }

    private void dismissDialog() {
        InputMethodsManager.hideKeyboard(mIdentity);
        dismiss();
    }

    private int transformNetworkPrefix(String len) {
        int prefix_length = 24;
        if (null == len || "".equals(len)) {
            return prefix_length;
        }
        try {
            prefix_length = Integer.valueOf(len);
        } catch (Exception e) {
        }
        return prefix_length;
    }

    /**
     * 验证输入是否有效
     * @Title: invIp
     * @Description: TODO
     * @param @return
     * @return boolean
     * @throws
     */
    private boolean invalidIp() {
//		if (!WifiHelper.isValidIp(mWifiIp.getText().toString())) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_ip_address)));
//			return false;
//		}
//		if (!WifiHelper.isValidIp(mWifiGateway.getText().toString())) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_gateway_address)));
//			return false;
//		}
//		int len = transformNetworkPrefix(mWifiPrefix.getText().toString());
//		if (len < 0 || len > 32) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_prefix_length)));
//			return false;
//		}
//		if (!WifiHelper.isValidIp(mWifiDns1.getText().toString())) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_dns1_address)));
//			return false;
//		}
//		String dns2 = mWifiDns2.getText().toString();
//		if (!"".equals(dns2)) {
//			if (!WifiHelper.isValidIp(dns2)) {
//				MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_dns2_address)));
//				return false;
//			}
//		}
        return true;
    }
}
