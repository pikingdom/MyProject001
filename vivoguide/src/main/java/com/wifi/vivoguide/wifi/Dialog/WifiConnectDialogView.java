package com.wifi.vivoguide.wifi.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.WifiDataBusiness;
import com.wifi.vivoguide.Business.WifiConnect;
import com.wifi.vivoguide.Business.WifiHelper;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.util.InputMethodsManager;

/**
 * @Description: 输入wifi连接信息对话框
 * @author linyt
 * @date 2013-3-7 下午5:31:45
 *
 */
public class WifiConnectDialogView extends Dialog implements OnClickListener {

    private EditText mIdentity, mWifiPasswd;
    private TextView mIdentityTxt, mSsidTitle;
    /**
     * 切换显示明文密码
     */
    private CheckBox mWifiShowpasswdCheckbox;
    private TextView mCancleBtn;
    private TextView mConnectBtn;
    private WifiManager mWifiManager;
    private String mSsid;
    private String mCapabilities;
    private WifiHelper.PskType mPskType;
    /**
     * 根据android版本是否高于2.3，决定是否显示切换静态动态ip的layout
     */
    private boolean showline = false;
    private Context mActivity;


    @SuppressLint("WifiManagerLeak")
    public WifiConnectDialogView(Context activity, String ssid, String capabilities) {
        super(activity, R.style.Dialog);
        mActivity = activity;
        mSsid = ssid;
        mCapabilities = capabilities;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_connect_dialog_view);
        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        mWifiManager = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        //	mIsZh = TelephoneUtil.isZh(mActivity);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.ssid_title);
        mIdentityTxt = (TextView) findViewById(R.id.identity_txt);
        mIdentity = (EditText) findViewById(R.id.identity_edit);
        mWifiPasswd = (EditText) findViewById(R.id.wifi_passwd);
        mWifiShowpasswdCheckbox = (CheckBox) findViewById(R.id.wifi_showpasswd_checkbox);
        mCancleBtn = (TextView) findViewById(R.id.wifi_button_cancle);
        mConnectBtn = (TextView) findViewById(R.id.wifi_button_connect);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mConnectBtn.setOnClickListener(this);

        mWifiShowpasswdCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mWifiPasswd.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });
    }

    private WifiDataBusiness mBaseWifiBusiness;
    private void setViews() {

        mBaseWifiBusiness = new WifiDataBusiness(mActivity);
        mSsidTitle.setText(mSsid);
        mIdentity.setFocusable(true);
        mIdentity.setFocusableInTouchMode(true);
        mIdentity.requestFocus();

        mPskType = WifiHelper.getSecurityType(mCapabilities);
        showKeyBoard();
        showOrHideIdentityView();
    }

    public void showKeyBoard() {
        if (mIdentity != null)
            InputMethodsManager.showKeyboard(mIdentity);
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
        if (id == R.id.wifi_button_cancle) {
            dismissDialog();

        } else if (id == R.id.wifi_button_connect) {
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

            mBaseWifiBusiness.notifitionConnectedWifiInfo(bean);

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
