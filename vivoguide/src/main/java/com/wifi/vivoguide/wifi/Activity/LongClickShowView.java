package com.wifi.vivoguide.wifi.Activity;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.WifiDataBusiness;
import com.wifi.vivoguide.Business.WifiConnect;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.WifiUtil;
import com.wifi.vivoguide.wifi.Dialog.ModifyPwdDialogView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 长按wifi弹出选项页面
 * @author llf
 * @date 2017-7 17
 * 
 */
public class LongClickShowView implements OnClickListener{

	private Context mActivity;
	private RelativeLayout mIgnoreWifiLayout;
	private RelativeLayout mConnectWifiLayout;
	private RelativeLayout mModifyPwdWifiLayout;
	private TextView mSsidTxt;

	public static final String CONNECTING_WIFI = "connecting_wifi";
	public static final String CONNECTED_WIFI = "connected_wifi";
	public static final String NOT_CONNECT_WIFI  ="not_connect_wifi";

	private View mLongShowView;
	private String  mCurrentShowView;
	private WifiBean mBean;

    private WifiUtil mWifiUtil;
	private List<WifiConfiguration> mConfigurations;
	private WifiConfiguration mWifiConfiguration;
	private WifiManager mWifiManager;
	private WifiConnect mWc;
	private WifiDataBusiness mBaseWifiBusiness;
    private String mSsid;

	public View getLongClickShowView(){
		return mLongShowView;
	}

	public LongClickShowView(Context activity, WifiBean bean,String ssid, String currentShowView) {
		mActivity = activity;
		mBean = bean;
        mSsid = ssid;
		mCurrentShowView = currentShowView;
		mLongShowView = LayoutInflater.from(activity).inflate(R.layout.long_click_view, null);

		findViewsById();
		setViews();
		setListeners();
	}

	private RelativeLayout mImageBgLayout;
	private void findViewsById() {

		mSsidTxt = (TextView)mLongShowView.findViewById(R.id.ssid_txt);
		mImageBgLayout  = (RelativeLayout)mLongShowView.findViewById(R.id.image_bg_layout);
		mIgnoreWifiLayout = (RelativeLayout)mLongShowView.findViewById(R.id.ignore_wifi_layout);
		mConnectWifiLayout = (RelativeLayout)mLongShowView.findViewById(R.id.connect_wifi_layout);
		mModifyPwdWifiLayout = (RelativeLayout)mLongShowView.findViewById(R.id.modify_pwd_layout);
	}

	private void setListeners() {
		mIgnoreWifiLayout.setOnClickListener(this);
		mConnectWifiLayout.setOnClickListener(this);
		mModifyPwdWifiLayout.setOnClickListener(this);
		mImageBgLayout.setOnClickListener(this);
	}

	private void setViews() {
		mBaseWifiBusiness = new WifiDataBusiness(mActivity);
        if (mBean != null)
		    mSsidTxt.setText(mBean.getSsid()+"");
        else {
            mSsidTxt.setText(mSsid);
        }

		if (mCurrentShowView.equals(CONNECTING_WIFI)) {
			mConnectWifiLayout.setVisibility(View.INVISIBLE);
			mIgnoreWifiLayout.setVisibility(View.VISIBLE);
			mModifyPwdWifiLayout.setVisibility(View.VISIBLE);

		} else if (mCurrentShowView.equals(CONNECTED_WIFI)) {
			mConnectWifiLayout.setVisibility(View.VISIBLE);
			mIgnoreWifiLayout.setVisibility(View.VISIBLE);
			mModifyPwdWifiLayout.setVisibility(View.VISIBLE);

		} else if (mCurrentShowView.equals(NOT_CONNECT_WIFI)) {
			mConnectWifiLayout.setVisibility(View.VISIBLE);
			mIgnoreWifiLayout.setVisibility(View.INVISIBLE);
			mModifyPwdWifiLayout.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.ignore_wifi_layout) {
			//两种情况，一是CONNECTING_WIFI：当前正在使用的wifi， 二是CONNECTED_WIFI：已经使用过，暂时没连接
			if (mBean == null) {
				mBaseWifiBusiness.ignoreWifiByConnected(mSsid);
			} else {
				mBaseWifiBusiness.ignoreWifiByConnected(mBean);
			}

		} else if (id == R.id.connect_wifi_layout) {
            if (mBean == null) return;
			//两种情况 一CONNECTED_WIFI、已经连接过，再次连接; 二、NOT_CONNECT_WIFI 首次连接
			if (mCurrentShowView.equals(CONNECTED_WIFI)) {
				mBaseWifiBusiness.connectWifiByConnected(mBean);
			} else if (mCurrentShowView.equals(NOT_CONNECT_WIFI)) {
				mBaseWifiBusiness.connectWifibyFirstConnect(mBean);
			}
		} else if (id == R.id.modify_pwd_layout) {
			final ModifyPwdDialogView dialog = new ModifyPwdDialogView(mActivity, mSsid);
			dialog.show();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					dialog.showKeyBoard();
				}
			}, 300);

		}
		SystemEvent.fireEvent(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW);

	}

}
