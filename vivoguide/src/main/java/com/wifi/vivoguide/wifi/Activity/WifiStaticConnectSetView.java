package com.wifi.vivoguide.wifi.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.WifiDataBusiness;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.commonview.SlipButtonView;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.wifi.Dialog.WifiStaticDialogView;

import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 输入wifi连接信息对话框
 * @author llf
 * @date 2013-3-7 下午5:31:45
 * 
 */
public class WifiStaticConnectSetView extends Dialog implements OnClickListener, SlipButtonView.OnChangedListener, SystemEvent.EventListener {

	private EditText mIdentity, mWifiPasswd;
	private TextView mIdentityTxt;

	private SlipButtonView mSlipBtn;
	private RelativeLayout mIpAddressLayout, mGatewayLayout, mNetmaskLayout, mDns1Layout, mDns2Layout;
	private TextView mIpAddressValueTxt, mGateWayValueTxt, mNetmaskVlaueTxt, mDns1VlaueTxt, mDns2ValueTxt;
	private TextView mIpAddressTxt, mGateWayTxt, mNetmaskTxt, mDns1Txt, mDns2Txt;
	private TextView mSSIDTxt;
	private TextView mForgotWifi;
	private RelativeLayout mCancelBtn;
	private RelativeLayout mCommonTitleView;

	private WifiManager mWifiManager;
	private String mSsid;
	private String mCurrentConnectWifiSsid;

	private View mWifiStaticConnectView;
	private Context mActivity;
	private WifiDataBusiness mBaseWifiBusiness;

	public View getWifiStaticConnectView(){
		return mWifiStaticConnectView;
	}

    @SuppressLint("WifiManagerLeak")
	public WifiStaticConnectSetView(Context activity, String ssid) {
		super(activity, R.style.Dialog);
		mActivity = activity;
		mWifiStaticConnectView = LayoutInflater.from(activity).inflate(R.layout.wifi_static_connect_set, null);
		mWifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
		mBaseWifiBusiness = new WifiDataBusiness(mActivity);

		findViewsById();
		setViews(ssid);
		setListeners();
	}

	private void findViewsById() {
		mCommonTitleView = (RelativeLayout)mWifiStaticConnectView.findViewById(R.id.static_toolbar);
		mSSIDTxt = (TextView)mCommonTitleView.findViewById(R.id.toobarTitle);
		mCancelBtn = (RelativeLayout)mCommonTitleView.findViewById(R.id.cancel);

		mForgotWifi = (TextView) mWifiStaticConnectView.findViewById(R.id.forgot_wifi_txt);
		mSlipBtn = (SlipButtonView) mWifiStaticConnectView.findViewById(R.id.wifi_ip_assignment);

		mIpAddressLayout = (RelativeLayout) mWifiStaticConnectView.findViewById(R.id.ip_address_layout);
		mIpAddressValueTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.ip_address_value);
		mIpAddressTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.ip_address_txt);

		mGatewayLayout = (RelativeLayout) mWifiStaticConnectView.findViewById(R.id.gateway_layout);
		mGateWayValueTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.gateway_value);
		mGateWayTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.gateway_txt);

		mNetmaskLayout = (RelativeLayout) mWifiStaticConnectView.findViewById(R.id.netmask_layout);
		mNetmaskVlaueTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.netmask_value);
		mNetmaskTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.netmask_txt);

		mDns1Layout = (RelativeLayout) mWifiStaticConnectView.findViewById(R.id.dns1_layout);
		mDns1VlaueTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.dns1_value);
		mDns1Txt = (TextView) mWifiStaticConnectView.findViewById(R.id.dns1_txt);

		mDns2Layout = (RelativeLayout) mWifiStaticConnectView.findViewById(R.id.dns2_layout);
		mDns2ValueTxt = (TextView) mWifiStaticConnectView.findViewById(R.id.dns2_value);
		mDns2Txt= (TextView) mWifiStaticConnectView.findViewById(R.id.dns2_txt);
	}

	private void setListeners() {
		mSlipBtn.SetOnChangedListener(this);
		mIpAddressLayout.setOnClickListener(this);
		mGatewayLayout.setOnClickListener(this);
		mNetmaskLayout.setOnClickListener(this);
		mDns1Layout.setOnClickListener(this);
		mDns2Layout.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		mForgotWifi.setOnClickListener(this);

		SystemEvent.addListener(SystemEventConst.EVENT_INPUT_STATIC_SET_VALUE, this);
	}

	public void onDestoryEvent() {
		SystemEvent.removeListener(SystemEventConst.EVENT_INPUT_STATIC_SET_VALUE, this);
	}

	private void setViews(String ssid) {
		mSsid = ssid;
		mSSIDTxt.setText(mSsid);
		mForgotWifi.setVisibility(View.GONE);
		setDefaultColor(false);
		setDefaultValue(false);

		mCurrentConnectWifiSsid = mBaseWifiBusiness.getCurrentConnectWifiSsid();
		if (!"NULL".equals(mCurrentConnectWifiSsid) && mCurrentConnectWifiSsid.equals(mSsid)) {
			setDefaultValue(true);
		}
//		for (Map.Entry entry: neverUsedPoint.entrySet()) {
//			String key = (String) entry.getKey();
//			if (key.equals(mCurrentConnectWifiSsid)) {
//				setDefaultValue(true);
//			}
//		}
	}

	private void setDefaultColor(boolean b) {
		String defaultColor = null;
		if (b) {
			defaultColor = "#1b1b1b";
		} else {
			defaultColor = "#818181";
		}
		mIpAddressTxt.setTextColor(Color.parseColor(defaultColor));
		mGateWayTxt.setTextColor(Color.parseColor(defaultColor));
		mNetmaskTxt.setTextColor(Color.parseColor(defaultColor));
		mDns1Txt.setTextColor(Color.parseColor(defaultColor));
		mDns2Txt.setTextColor(Color.parseColor(defaultColor));

	}

	private void setDefaultValue(boolean b) {
		if (b) {
			mForgotWifi.setVisibility(View.VISIBLE);
			mIpAddressValueTxt.setText("192.168.4.255");
			mGateWayValueTxt.setText("192.168.4.1");
			mNetmaskVlaueTxt.setText("250.250.252.0");
			mDns1VlaueTxt.setText("172.17.162.175");
			mDns2ValueTxt.setText("172.17.149.59");
		} else {
			mIpAddressValueTxt.setText("");
			mGateWayValueTxt.setText("");
			mNetmaskVlaueTxt.setText("");
			mDns1VlaueTxt.setText("");
			mDns2ValueTxt.setText("");
		}

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.cancel) {
			Intent intent = new Intent();
			intent.putExtra("PAUSE_REMOVE", true);
			SystemEvent.fireEvent(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW, intent);
		}

		if (id == R.id.forgot_wifi_txt) {
			mBaseWifiBusiness.ignoreWifiByConnected(mCurrentConnectWifiSsid);
		}

		if (!mIsSetStaticEnable) return;
		if (id == R.id.ip_address_layout) {
			openWifiStaticView(mSsid, WifiStaticDialogView.STATIC_IP, mIpAddressValueTxt.getText().toString().trim());

		} else if (id == R.id.gateway_layout) {
			openWifiStaticView(mSsid, WifiStaticDialogView.STATIC_GATEWAY, mGateWayValueTxt.getText().toString().trim());

		} else if (id == R.id.netmask_layout) {
			openWifiStaticView(mSsid, WifiStaticDialogView.STATIC_NETMASK, mNetmaskVlaueTxt.getText().toString().trim());

		} else if (id == R.id.dns1_layout) {
			openWifiStaticView(mSsid, WifiStaticDialogView.STATIC_DNS1, mDns1VlaueTxt.getText().toString().trim());

		} else if (id == R.id.dns2_layout) {
			openWifiStaticView(mSsid, WifiStaticDialogView.STATIC_DNS2, mDns2ValueTxt.getText().toString().trim());

		}
	}

	public void openWifiStaticView(String ssid, int type, String defaultValue) {
		final WifiStaticDialogView dialog = new WifiStaticDialogView(mActivity, ssid, type, defaultValue);
		dialog.show();
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				dialog.showKeyBoard();
			}
		}, 200);

	}

	private boolean mIsSetStaticEnable = false;
	@Override
	public void OnChanged(boolean CheckState) {
		mIsSetStaticEnable = CheckState;
		setDefaultColor(CheckState);
		mSlipBtn.setCheck(CheckState);
	}

	@Override
	public void onEvent(SystemEventConst eventType, Intent data) {
		if (eventType.equals(SystemEventConst.EVENT_INPUT_STATIC_SET_VALUE)) {
			int type = data.getIntExtra("CURRENT_INPUT_TYPE", 0);
			String value =  data.getStringExtra("CURRENT_INPUT_VALUE");
			switch (type) {
				case WifiStaticDialogView.STATIC_IP:
					mIpAddressValueTxt.setText(value);
					break;
				case WifiStaticDialogView.STATIC_GATEWAY:
					mGateWayValueTxt.setText(value);
					break;
				case WifiStaticDialogView.STATIC_NETMASK:
					mNetmaskVlaueTxt.setText(value);
					break;
				case WifiStaticDialogView.STATIC_DNS1:
					mDns1VlaueTxt.setText(value);
					break;
				case WifiStaticDialogView.STATIC_DNS2:
					mDns2ValueTxt.setText(value);
					break;
			}
		}
	}
}
