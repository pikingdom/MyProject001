package com.gionee.setupwizard.s10bl.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.gionee.setupwizard.R;
import com.gionee.setupwizard.s10bl.Bean.WifiBean;
import com.gionee.setupwizard.s10bl.Business.WifiHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: 获取连接点信息、扫描周围热点信息、开关wifi、连接热点等功能
 * @author llf
 * @date 2017-07-11
 * 
 */
public class WifiUtil {
	private static WifiUtil mWifiUtil;
	private WifiManager mWifiManager;
	private WifiInfo mWifiInfo;
	private List<WifiBean> mWifiList;
	private List<WifiConfiguration> mWifiConfigurations;
	/**在范围内**/
	public static final String SCAN_LIST = "scan";
	/**不在范围内**/
	public static final String NO_IN_SCAPE_LIST = "notinscape";

	public enum WIFI_RSSI {

		/***强*****/
		RSSI_PERFECT {
			public String getName(Context ctx) {
				return ctx.getString(R.string.wifi_rssi_level_perfect);
			}
			public int getIconRes(Context ctx) {
				return R.drawable.gn_wifi_rssi_level_perfect;
			}
		},

		/***较强****/
		RSSI_GOOD {
			public String getName(Context ctx) {
				return ctx.getString(R.string.wifi_rssi_level_good);
			}
			public int getIconRes(Context ctx) {
				return R.drawable.gn_wifi_rssi_level_good;
			}
		},

		/***一般****/
		RSSI_NORMAL {
			public String getName(Context ctx) {
				return ctx.getString(R.string.wifi_rssi_level_normal);
			}
			public int getIconRes(Context ctx) {
				return R.drawable.gn_wifi_rssi_level_normal;
			}
		},

		/***弱****/
		RSSI_BAD {
			public String getName(Context ctx) {
				return ctx.getString(R.string.wifi_rssi_level_bad);
			}
			public int getIconRes(Context ctx) {
				return R.drawable.gn_wifi_rssi_level_bad;
			}
		},

		/***无信号****/
		RSSI_NOTHING {
			public String getName(Context ctx) {
				return ctx.getString(R.string.wifi_rssi_level_nothing);
			}
			public int getIconRes(Context ctx) {
				return R.drawable.gn_wifi_rssi_level_nothing;
			}
		};

		public abstract String getName(Context ctx);
		public abstract int getIconRes(Context ctx);
	}

	public static WifiUtil getInstance(Context context) {
		if (null == mWifiUtil) {
			mWifiUtil = new WifiUtil(context);
		}
		return mWifiUtil;
	}

	private WifiUtil(Context context) {
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	/**
	 * 打开wifi
	 * @param
	 * @return
	 *
	 */
	public void openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/**
	 * 关闭wifi
	 * @param
	 * @return
	 *
	 */
	public void closeWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * 查看wifi状态
	 * @param
	 * @return
	 *
	 */
	public int checkState() {
		return mWifiManager.getWifiState();
	}

	/**
	 * 包含一些已经配置好的WI-FI网络，如当前正在连接的WI-FI网络信息
	 * @param
	 * @return void
	 */
	public List<WifiConfiguration> getConfiguration() {
		return mWifiConfigurations;
	}

	/**
	 * @Description: 扫描热点信息
	 * @param
	 * @return void
	 * @throws
	 */
	public void startScan() {
		//在2.3.3上可能报出 SecurityException, requires android.permission.BROADCAST_STICKY
		try{
			mWifiInfo = mWifiManager.getConnectionInfo();
		}catch(Exception e) {
			e.printStackTrace();
		}

		/**包含一些已经配置好的WI-FI网络，如当前正在连接的WI-FI网络信息**/
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();
		mWifiManager.startScan();
	}

	/**
	 * 扫描到的热点保存到mWifiList中
	 * @param
	 * @return
	 *
	 */
	public void dualWithScanResult() {
		String current_ssid = WifiHelper.dealWithSSID(getSSID());
		int rssi = getIntRssi();
		mWifiList = new ArrayList<WifiBean>();

		/**扫描到的热点**/
		List<ScanResult> scanlist = mWifiManager.getScanResults();
		if (scanlist == null) {
			return;
		}
		WifiBean bean = null;
		ScanResult scanResult = null;
		Map<String, WifiBean> tmpMap = new HashMap<String, WifiBean>();
		for (Iterator<ScanResult> iterator = scanlist.iterator(); iterator.hasNext();) {
			scanResult = iterator.next();
			if (tmpMap.containsKey(scanResult.SSID)) {
				continue;
			}
			bean = new WifiBean();
			String ssid = WifiHelper.dealWithSSID(scanResult.SSID);
			bean.setSsid(ssid);
			if (ssid.equals(current_ssid)) {
				bean.setCurrentUsed(true);
				bean.setLevel(rssi);
			} else {
				bean.setLevel(scanResult.level);
			}
			bean.setType(WifiBean.TYPE_SCANRESULT);
			bean.setCapabilities(scanResult.capabilities);
			tmpMap.put(scanResult.SSID, bean);
			mWifiList.add(bean);
		}

		if (mWifiConfigurations != null) {
			for (Iterator<WifiConfiguration> iterator = mWifiConfigurations.iterator(); iterator.hasNext();) {
				WifiConfiguration configuaration = iterator.next();
				String ssid = WifiHelper.dealWithSSID(configuaration.SSID);
				if (tmpMap.containsKey(ssid)) {
					bean = tmpMap.get(ssid);
					bean.setNetworkId(configuaration.networkId);
				} else {
					//不在使用范围的wifi
//					bean = new WifiBean();
//					bean.setSsid(ssid);
//					bean.setType(WifiBean.TYPE_WIFICONFIGURATION);
//					bean.setNetworkId(configuaration.networkId);
//					mWifiList.add(bean);
				}
			}
		}

		try {
			Collections.sort(mWifiList);
			for(WifiBean info:mWifiList){
				Log.e("llf","wlan name:"+info.getSsid() + "====networdid:" + info.getNetworkId());
			}
		} catch (Exception e) {
			//比较的时候会报比较器异常错误，如果有异常，就不执行排序。
			//后果就是wifi列表的显示顺序是乱的。但是不至于导致桌面崩溃。
			e.printStackTrace();
		}
	}

	private List<WifiConfiguration> mConfigurations;
	private WifiConfiguration mWifiConfiguration;
	public Map<String, WifiBean> getNeverUsedPoint() {
		Map<String, WifiBean> neverUsedPoint = new HashMap<>();
		if (mWifiUtil == null){
			return null;
		}
		try {
			Map<String, WifiConfiguration> usedPoint = getUsedPoint();
			if (mWifiList != null && usedPoint != null) {
				Iterator<WifiBean> iterator = mWifiList.iterator();
				while (iterator != null && iterator.hasNext()) {
					WifiBean bean = iterator.next();
					if (bean == null || usedPoint.containsKey(bean.getSsid())){
						continue;
					}
					neverUsedPoint.put(bean.getSsid(), bean);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			neverUsedPoint = null;
		}

		return neverUsedPoint;
	}

	public Map<String, WifiConfiguration> getUsedPoint() {
		Map<String, WifiConfiguration> usedPoint = new HashMap<>();
		if (mWifiUtil == null){
			return null;
		}
		try {
			mConfigurations = mWifiUtil.getConfiguration();
			if (mConfigurations != null) {
				Iterator<WifiConfiguration> iterator = mConfigurations.iterator();
				while (iterator != null && iterator.hasNext()) {
					mWifiConfiguration = iterator.next();
					if (mWifiConfiguration == null) {
						continue;
					}
					String ssid = WifiHelper.dealWithSSID(mWifiConfiguration.SSID);
					usedPoint.put(ssid, mWifiConfiguration);
				}
			}

		}catch (Exception e){
			e.printStackTrace();
			return null;
		}

		return usedPoint;
	}

	/**
	 * @Description: 返回扫描到的热点信息（包括在范围的热点、不在范围的热点，按可连接性带排序）
	 * @param @return
	 * @return List<WifiBean>
	 */
	public List<WifiBean> getWifiData() {
		return mWifiList;

	}

	public String getMacAddress() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
	}

	public String getBSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
	}

	public String getSSID() {
		return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
	}

	public int getIpAddress() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
	}

	public String getLinkSpeed() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getLinkSpeed() + WifiInfo.LINK_SPEED_UNITS;
	}

	public WIFI_RSSI getRssi() {
		int RSSI = mWifiInfo.getRssi();
		return getRssi(RSSI);
	}

	/***
	 * 返回信号强弱信息
	 * @param RSSI
	 * @return
	 */
	public WIFI_RSSI getRssi(int RSSI) {
		if (RSSI < -95) {
			return WIFI_RSSI.RSSI_NOTHING;
		} else if (RSSI >= -95 && RSSI < -90) {
			return WIFI_RSSI.RSSI_BAD;
		} else if (RSSI >= -90 && RSSI <= -70) {
			return WIFI_RSSI.RSSI_NORMAL;
		} else if (RSSI >= -70 && RSSI <= -50) {
			return WIFI_RSSI.RSSI_GOOD;
		} else {
			return WIFI_RSSI.RSSI_PERFECT;
		}
	}

	/**
	 * 获取wifi显示等级信号
	 * @param
	 * @return
	 */
	private int getIntRssi() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getRssi();
	}

	public int getNetWordId() {
		return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
	}

//	 public StringBuffer lookUpScan() {
//		 StringBuffer sb = new StringBuffer();
//		 for (int i = 0; i < mWifiList.size(); i++) {
//			 sb.append("Index_" + new Integer(i + 1).toString() + ":");
//			 sb.append((mWifiList.get(i)).toString()).append("\n");
//		 }
//		 return sb;
//	 }

//	 public void disConnectionWifi(int netId) {
//		 mWifiManager.disableNetwork(netId);
//		 mWifiManager.disconnect();
//	 }

}
