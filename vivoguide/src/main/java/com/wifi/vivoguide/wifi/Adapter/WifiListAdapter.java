package com.wifi.vivoguide.wifi.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.WifiDataBusiness;
import com.wifi.vivoguide.Business.WifiHelper;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.LogUtil;
import com.wifi.vivoguide.util.WifiUtil;
import com.wifi.vivoguide.wifi.Activity.LongClickShowView;
import com.wifi.vivoguide.wifi.Activity.WifiSetView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WifiListAdapter extends BaseAdapter {
	private final Context mActivity;
	private LayoutInflater mInflater;
	private WifiUtil mWifiUtil;
	private WifiManager mWifiManager;
	//private WifiConnect mWc;
	private List<WifiBean> mData = new ArrayList<WifiBean>();
	private List<WifiConfiguration> mConfigurations;
	private WifiConfiguration mWifiConfiguration;
	private String mCurrentUseSsid;
	private String mCurrentSpeed;
	private String mCurrentIp;

	/**连接过的热点*/
	private Map<String, WifiConfiguration> mUsedPoint = new HashMap<String, WifiConfiguration>();

	/**从未连接过的热点*/
	private Map<String, WifiBean> mNeverUsedPoint = new HashMap<String, WifiBean>();

	private static String NOT_IN_SCAPE = "";
	private static String FORGOT_WLAN = "";
	private static String NOT_SAVE = "";
	private static String CONNECT_WIFI = "";
	private static String CANCEL_WIFI = "";
	private static String TIP_TYPE4 = "";
	private WifiDataBusiness mBaseWifiBusiness;

	public WifiListAdapter(Context activity, List<WifiBean> data) {
		mBaseWifiBusiness = new WifiDataBusiness(activity);
		this.mActivity = activity;
		this.mData = data;

		init();
	}

	private void removeCurrentConnectWifiData() {
		for (int i = 0; i < mData.size(); i++) {
			if ( mCurrentUseSsid.equals(mData.get(i).getSsid())) {
				mData.remove(i);
			}
		}
	}

	@SuppressLint("WifiManagerLeak")
	private void init() {
		mWifiManager = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
		mInflater = LayoutInflater.from(mActivity);
		mWifiUtil = WifiUtil.getInstance(mActivity);
	//	mWc = new WifiConnect(mWifiManager);

		setDefaultStateValue();
		refreshWifiData();
	}

	private void setDefaultStateValue() {
		//NOT_IN_SCAPE = mActivity.getString(R.string.wifi_not_in_scape);
		FORGOT_WLAN = mActivity.getString(R.string.forgot_wifi);
		//NOT_SAVE = mActivity.getString(R.string.wifi_forget_point);
		CONNECT_WIFI = mActivity.getString(R.string.connect_wifi);
		CANCEL_WIFI =mActivity.getString(R.string.cancel_wifi);
		TIP_TYPE4 = mActivity.getString(R.string.wifi_point_type4);
	}

	private void refreshWifiData() {
		mCurrentUseSsid = WifiHelper.dealWithSSID(mWifiUtil.getSSID());
		mCurrentSpeed = mWifiUtil.getLinkSpeed();
		mCurrentIp = WifiHelper.intIpToStringIp(mWifiUtil.getIpAddress());
		LogUtil.i("WifiListAdapter","ssid, 网速， ip：" + mCurrentSpeed + " : " + mCurrentIp + ": " + mCurrentUseSsid);

		clearDataAndRefresh();
		removeCurrentConnectWifiData();
	}

	public void setData(List<WifiBean> data) {
		this.mData = data;
		refreshWifiData();
	}

	private void clearDataAndRefresh() {
		if (mWifiUtil == null || mUsedPoint == null || mNeverUsedPoint == null){
			return;
		}
		try {
			mConfigurations = mWifiUtil.getConfiguration();
			mUsedPoint.clear();
			mNeverUsedPoint.clear();
			if (mConfigurations != null) {
				Iterator<WifiConfiguration> iterator = mConfigurations.iterator();
				while (iterator != null && iterator.hasNext()) {
					mWifiConfiguration = iterator.next();
					if (mWifiConfiguration == null) {
						continue;
					}
					mUsedPoint.put(WifiHelper.dealWithSSID(mWifiConfiguration.SSID), mWifiConfiguration);
				}
			}
			if (mData != null) {
				Iterator<WifiBean> iterator = mData.iterator();
				while (iterator != null && iterator.hasNext()) {
					WifiBean bean = iterator.next();
					if (bean == null || mUsedPoint.containsKey(bean.getSsid())){
						continue;
					}
					mNeverUsedPoint.put(bean.getSsid(), bean);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public int getCount() {
		if (mData == null)
			return 0;
		return mData.size();
	}

	@Override
	public Object getItem(int paramInt) {
		return null;
	}

	@Override
	public long getItemId(int paramInt) {
		return 0;
	}

	private void notifitionConnectedWifiInfo(WifiBean bean) {
		Intent intent = new Intent();
		intent.putExtra("SSID", bean.getSsid());
		intent.putExtra("CAPABILITIES", bean.getCapabilities());
		WifiUtil wifiUtil = WifiUtil.getInstance(mActivity);
		intent.putExtra("CURRENT_SPEED", wifiUtil.getLinkSpeed());
		intent.putExtra("IP_ADDRESS", WifiHelper.intIpToStringIp(wifiUtil.getIpAddress()));
		intent.putExtra("NETWORK_ID", bean.getNetworkId()+"");
		SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW_CONNECTING_WIFI_INFO, intent);
	}

	@Override
	public View getView(int pos, View converView, ViewGroup paramViewGroup) {
		final ViewHolder holder;
		if (converView == null) {
			converView = mInflater.inflate(R.layout.vivo_wifi_list_item, null);
			holder = new ViewHolder();
			//holder.wifiItem = (View) converView.findViewById(R.id.wifi_item);
			holder.wifiItem = (View) converView.findViewById(R.id.wifi_ssid_layout);
			holder.wifiFlagIcon = (ImageView) converView.findViewById(R.id.wifi_flag_icon);
			holder.wifiKeyLock = (ImageView) converView.findViewById(R.id.wifi_key_lock);
			holder.wifiSsid = (TextView) converView.findViewById(R.id.wifi_ssid);
			holder.wifiGoToSet = (ImageView) converView.findViewById(R.id.go_wifi_set);

			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}
		final WifiBean info = mData.get(pos);
		holder.wifiSsid.setText(info.getSsid());

		holder.wifiFlagIcon.setImageResource(mWifiUtil.getRssi(info.getLevel()).getIconRes(mActivity));
		holder.wifiItem.setTag(R.id.wifi_ssid_layout, info);

		holder.wifiGoToSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ssid = info.getSsid();
				Intent intent = new Intent();
				intent.putExtra("TITLE_SSID", ssid);
				SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW__WIFI_CONNECT_CONTAINER_VIEW, intent);
			}
		});

		holder.wifiItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setClickItemEvent(v, info);
			}
		});

        holder.wifiItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
				setLongClickEvent(view);
                return false;
            }
        });

		return converView;
	}

	private void setClickItemEvent(View v, final WifiBean info) {
		if (WifiSetView.mIsLongPress) return;

		final String ssid = info.getSsid();

		/**从来没使用过的热点，弹出输入对话框**/
		if (mNeverUsedPoint.containsKey(ssid)) {
			Intent intent = new Intent();
			intent.putExtra("SSID", ssid);
			intent.putExtra("CAPABILITIES", info.getCapabilities());
			SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW_WIFI_CONNECT_DIALOG_VIEW, intent);

		} else {
//					/**点击正在使用的连接**/
//					/**取消 忘记网络**/
//					if (ssid.equals(mCurrentUseSsid)) {
//						WifiHelper.showAlertDialog(mActivity
//								, ssid
//								, String.format(TIP_TYPE3, WifiHelper.changeName(mActivity, WifiHelper.getKeyMgmt(info.getCapabilities()))
//									, mCurrentSpeed
//									, mWifiUtil.getRssi(info.getLevel()).getName(mActivity)
//									, mCurrentIp)
//								, FORGOT_WLAN, new Dialog.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										mWifiManager.removeNetwork(bean.getNetworkId());
//										//mWifiManager.disableNetwork(bean.getNetworkId());
//										dialog.dismiss();
//									}
//								}
//								, "", null
//								,CANCEL_WIFI, new Dialog.OnClickListener() {
//									@Override
//									public void onClick(DialogInterface dialog, int which) {
//										dialog.dismiss();
//									}
//								} );
//						return;
//					}

			/**点击不在使用的连接，但是可用的**/
			/**返回 不保存 连接**/
			WifiHelper.showAlertDialog(mActivity
					, ssid
					, String.format(TIP_TYPE4, WifiHelper.changeName(mActivity, WifiHelper.getKeyMgmt(info.getCapabilities()))
							, mWifiUtil.getRssi(info.getLevel()).getName(mActivity))
					, CONNECT_WIFI, new Dialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mBaseWifiBusiness.connectWifiByConnected(info);
							dialog.dismiss();
						}
					}
					,FORGOT_WLAN, new Dialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							mBaseWifiBusiness.ignoreWifiByConnected(info);
							dialog.dismiss();
						}
					}
					, CANCEL_WIFI, new Dialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
		}
	}

	private void setLongClickEvent(View view) {
		WifiSetView.mIsLongPress = true;
		final WifiBean bean = (WifiBean) view.getTag(R.id.wifi_ssid_layout);
		final String ssid = bean.getSsid();
		String currentShowViewType = null;

		/**从来没使用过的热点，弹出输入对话框**/
		if (mNeverUsedPoint.containsKey(ssid)) {
			currentShowViewType = LongClickShowView.NOT_CONNECT_WIFI;
		} else {

			/**点击正在使用的连接**/
			if (ssid.equals(mCurrentUseSsid)) {
				currentShowViewType = LongClickShowView.CONNECTING_WIFI;
			} else {
				/**点击已经连接过，是可用的**/
				currentShowViewType = LongClickShowView.CONNECTED_WIFI;
			}
		}

		mLongClickShowView = new LongClickShowView(mActivity, bean, ssid, currentShowViewType);
		SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW_LONG_CLICK_VIEW);
	}

	private LongClickShowView mLongClickShowView;
	public LongClickShowView getLongClickView(){
		return mLongClickShowView;
	}

//	public void updateLongClickStates() {
//		if (mIsLongClick)
//			mIsLongClick = false;
//	}

	class ViewHolder {
		public View wifiItem;
		public ImageView wifiFlagIcon;
		public ImageView wifiKeyLock;
		public ImageView wifiGoToSet;
		public TextView wifiSsid;

	}

}
