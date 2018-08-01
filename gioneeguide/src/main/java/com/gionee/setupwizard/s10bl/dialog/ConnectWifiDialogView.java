package com.gionee.setupwizard.s10bl.dialog;

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
import com.gionee.setupwizard.R;
import com.gionee.setupwizard.s10bl.Bean.WifiBean;
import com.gionee.setupwizard.s10bl.Business.WifiConnect;
import com.gionee.setupwizard.s10bl.Business.WifiHelper;
import com.gionee.setupwizard.s10bl.util.InputMethodsManager;

/**
 * @Description: 连接wifi对话框
 * @author llf
 * @date 2017-08-07
 *
 */
public class ConnectWifiDialogView extends Dialog implements OnClickListener {

    private Context mContext;
    private EditText mWifiPasswd;
    private TextView mSsidTitle;

    private TextView mCancleBtn;
    private TextView mConnectBtn;
//    private ImageView mShwoPasswordBtn;

    private String mSsid;
    private String mCapabilities;
    private WifiHelper.PskType mPskType;

    private WifiManager mWifiManager;
    private CheckBox checkBox;

    @SuppressLint("WifiManagerLeak")
    public ConnectWifiDialogView(Context activity, String ssid, String capabilities) {
        super(activity, R.style.Dialog);
        mContext = activity;
        mSsid = ssid;
        mCapabilities = capabilities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gn_connect_wifi_dialog_view);

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
        mSsidTitle = (TextView) findViewById(R.id.ssid_title);
        mWifiPasswd = (EditText) findViewById(R.id.wifi_passwd);
        mCancleBtn = (TextView) findViewById(R.id.wifi_button_cancle);
        mConnectBtn = (TextView) findViewById(R.id.wifi_button_connect);
      //  mShwoPasswordBtn = (ImageView) findViewById(R.id.show_pwd);
        checkBox = (CheckBox) findViewById(R.id.gn_sw_layout_contract_checkbox);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mConnectBtn.setOnClickListener(this);
        //mShwoPasswordBtn.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mWifiPasswd.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });
    }

    private void setViews() {

        mSsidTitle.setText(mSsid);

        mPskType = WifiHelper.getSecurityType(mCapabilities);
        showKeyBoard();
        showOrHideIdentityView();
    }

    public void showKeyBoard() {
        if (mWifiPasswd != null) {
            InputMethodsManager.showKeyboard(mWifiPasswd);
        }
    }

    private void showOrHideIdentityView() {
//        if (mCapabilities != null && mCapabilities.contains("WPA2-EAP")) {
//            mIdentity.setVisibility(View.VISIBLE);
//            mIdentityTxt.setVisibility(View.VISIBLE);
//        }else {
//            mIdentity.setVisibility(View.GONE);
//            mIdentityTxt.setVisibility(View.GONE);
//        }
    }

    private int recordClickCount = 0;
    private boolean isChecked = false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wifi_button_cancle) {
            dismissDialog();

        } else if (id == R.id.wifi_button_connect) {
            InputMethodsManager.hideKeyboard(mWifiPasswd);
            WifiConnect wc = new WifiConnect(mWifiManager);
            WifiBean bean = new WifiBean();
            bean.setSsid(mSsid);
            bean.setCapabilities(mCapabilities);
            bean.setPasswd(mWifiPasswd.getText().toString());
            if (mWifiPasswd.getVisibility() == View.VISIBLE) {
                String identityName = mWifiPasswd.getText().toString().trim();
                bean.setIdentity(identityName);
            }
            bean.setPskType(mPskType);
            wc.connect(bean);

            dismissDialog();

        }
    }

    private void dismissDialog() {
        InputMethodsManager.hideKeyboard(mWifiPasswd);
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
