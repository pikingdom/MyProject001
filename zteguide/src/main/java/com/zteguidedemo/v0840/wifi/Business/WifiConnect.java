package com.zteguidedemo.v0840.wifi.Business;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.zteguidedemo.v0840.wifi.Bean.WifiBean;

import java.net.InetAddress;
import java.util.List;

public class WifiConnect {

	WifiManager mWifiManager;

	public WifiConnect(WifiManager wifiManager) {
		this.mWifiManager = wifiManager;
	}

	private boolean OpenWifi() {
		boolean bRet = true;
		if (!mWifiManager.isWifiEnabled()) {
			bRet = mWifiManager.setWifiEnabled(true);
		}
		return bRet;
	}

	/**
	 * @Description: wifi连接时间长，需在线程中运行
	 * @param wifiBean wifi连接点的具体配置信息
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean connect(WifiBean wifiBean) {
		if (!this.OpenWifi()) {
			return false;
		}
		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		WifiConfiguration wifiConfig = this.CreateWifiInfo(wifiBean);
		if (wifiConfig == null) {
			return false;
		}

		WifiConfiguration tempConfig = this.IsExsits(wifiBean.getSsid());
		if (tempConfig != null) {
			mWifiManager.removeNetwork(tempConfig.networkId);
		}
		try {
			boolean isStatic = wifiBean.isStatic();
			if(Build.VERSION.SDK_INT > 10){
				WifiHelper.setIpAssignment(isStatic ? "STATIC" : "DHCP", wifiConfig);
			}
			if (isStatic) {
				WifiHelper.setIpAddress(InetAddress.getByName(wifiBean.getIp()), wifiBean.getNetworkPrefix(), wifiConfig);
				WifiHelper.setGateway(InetAddress.getByName(wifiBean.getGateway()), wifiConfig);
				
				InetAddress[] dns = new InetAddress[2];
				dns[0] = InetAddress.getByName(wifiBean.getDns1());
				dns[1] = InetAddress.getByName(wifiBean.getDns2());
				WifiHelper.setDNS(dns, wifiConfig);
			}
			// wifiManager.updateNetwork(wifiConfig); // apply the setting
		} catch (Exception e) {
			e.printStackTrace();
		}

		int netID = mWifiManager.addNetwork(wifiConfig);
		boolean bRet = mWifiManager.enableNetwork(netID, true);
		return bRet;
	}

	private int ssidToNetworkId(String ssid) {
		List<WifiConfiguration> currentNetworks = mWifiManager.getConfiguredNetworks();
		int networkId = -1;
		for (WifiConfiguration test : currentNetworks) {
			if ( test.SSID.equals(ssid) ) {
				networkId = test.networkId;
				break;
			}
		}

		return networkId;
	}

	/**
	 * @Description: wifi连接时间长，需在线程中运行
	 * @param config wifi连接点的具体配置信息
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public boolean connect(WifiConfiguration config) {
		if (!this.OpenWifi()) {
			return false;
		}
		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (config == null) {
			return false;
		}

		boolean bRet = mWifiManager.enableNetwork(config.networkId, true);

		return bRet;
	}

	/**
	 * 查看以前是否也配置过这个网络
	 * @param @SSID
	 * @param @return
	 * @return WifiConfiguration
	 */
	public WifiConfiguration IsExsits(String SSID) {
		List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (SSID.equals(WifiHelper.dealWithSSID(existingConfig.SSID))) {
				return existingConfig;
			}
		}
		return null;
	}

	private WifiConfiguration CreateWifiInfo(WifiBean wifiBean) {
		String SSID = wifiBean.getSsid();
		String password = wifiBean.getPasswd();
		WifiHelper.PskType type = WifiHelper.getSecurityType(wifiBean.getCapabilities());
		String identity = wifiBean.getIdentity();

		WifiConfiguration config = new WifiConfiguration();
		config.allowedAuthAlgorithms.clear();
		config.allowedGroupCiphers.clear();
		config.allowedKeyManagement.clear();
		config.allowedPairwiseCiphers.clear();
		config.allowedProtocols.clear();
		config.SSID = "\"" + SSID + "\"";

		if (type == WifiHelper.PskType.NOPASS) {
			config.wepKeys[0] = "";
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == WifiHelper.PskType.WEP) {
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
			config.wepTxKeyIndex = 0;
		}
		if (type == WifiHelper.PskType.WPA_PSK) {
			config.preSharedKey = "\"" + password + "\"";
			config.hiddenSSID = true;
			config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
			config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);//
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
			config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);//
			config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);// wpa
			if (getProtocolByKeyMgmt(wifiBean.getCapabilities())) {
				config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);// wpa2
			}
			config.status = WifiConfiguration.Status.ENABLED;
		} else if (type == WifiHelper.PskType.WPA_EAP){
			//config = WPAConfiguration.setWifiConfigurations(SSID, identity, Password, config);

			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
			config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
			WifiEnterpriseConfig enterpriseConfig = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
				enterpriseConfig = new WifiEnterpriseConfig();
				enterpriseConfig.setIdentity(identity);
				enterpriseConfig.setPassword(password);
				enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.PEAP);
				config.enterpriseConfig = enterpriseConfig;
			}
		}

		return config;
	}

	public static boolean isWifiConnect(Context cxt) {
		ConnectivityManager connManager = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE); //CONNECTIVITY_SERVICE
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi.isConnected();
	}

	/**
	 * 检测安全协议是否为wpa2,仅适用于检测wpa方式加密
	 * @Description: TODO
	 * @param @param capabilities
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	private boolean getProtocolByKeyMgmt(String capabilities) {
		if (null == capabilities || "".equals(capabilities)) {
			return true;
		}
		WifiHelper.KeyMgmt keyMgmt = WifiHelper.getKeyMgmt(capabilities);
		return !(WifiHelper.KeyMgmt.SECURITY_WPA_PSK == keyMgmt);
	}

	// public String getSecurityString(boolean concise) {
	// Context context = getContext();
	// switch(security) {
	// case SECURITY_EAP:
	// return concise ? context.getString(R.string.wifi_security_short_eap) :
	// context.getString(R.string.wifi_security_eap);
	// case SECURITY_PSK:
	// switch (pskType) {
	// case WPA:
	// return concise ? context.getString(R.string.wifi_security_short_wpa) :
	// context.getString(R.string.wifi_security_wpa);
	// case WPA2:
	// return concise ? context.getString(R.string.wifi_security_short_wpa2) :
	// context.getString(R.string.wifi_security_wpa2);
	// case WPA_WPA2:
	// return concise ? context.getString(R.string.wifi_security_short_wpa_wpa2)
	// :
	// context.getString(R.string.wifi_security_wpa_wpa2);
	// case UNKNOWN:
	// default:
	// return concise ?
	// context.getString(R.string.wifi_security_short_psk_generic)
	// : context.getString(R.string.wifi_security_psk_generic);
	// }
	// case SECURITY_WEP:
	// return concise ? context.getString(R.string.wifi_security_short_wep) :
	// context.getString(R.string.wifi_security_wep);
	// case SECURITY_NONE:
	// default:
	// return concise ? "" : context.getString(R.string.wifi_security_none);
	// }
	// }

}