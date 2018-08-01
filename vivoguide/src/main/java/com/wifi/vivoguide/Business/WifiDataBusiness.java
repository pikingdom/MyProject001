package com.wifi.vivoguide.Business;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.LogUtil;
import com.wifi.vivoguide.util.WifiUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/17.
 */

public class WifiDataBusiness {

    private Context mContext;
    public WifiUtil mWifiUtil;
    public static List<WifiConfiguration> mConfigurations = null;
    public WifiConfiguration mWifiConfiguration;
    public WifiManager mWifiManager;
    public WifiConnect mWc;

    /**连接过的热点*/
    private Map<String, WifiConfiguration> mUsedPoint = new HashMap<String, WifiConfiguration>();
    /**从未连接过的热点*/
    private Map<String, WifiBean> mNeverUsedPoint = new HashMap<String, WifiBean>();
    private static List<WifiBean> mWifiBeanList;

    public WifiDataBusiness(Context context) {
//        if (mConfigurations != null && mConfigurations.size() >= 0) {
//            mConfigurations.clear();
//        }

        if (mWifiBeanList != null && mWifiBeanList.size() > 0) {
            mWifiBeanList.clear();
        }
        mContext = context;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mWifiUtil = WifiUtil.getInstance(mContext);
       // mConfigurations = mWifiUtil.getConfiguration();
        mWc = new WifiConnect(mWifiManager);
        mWifiBeanList = mWifiUtil.getWifiData();
    }


    public String getCurrentConnectWifiSsid() {
        return WifiHelper.dealWithSSID(mWifiUtil.getSSID());
    }

    private static ArrayList<String> mCueentConnectWifiInfo = null;
    public ArrayList<String> getCurrentConnectWifiInfo(String currentSsid) {
        mConfigurations = mWifiUtil.getConfiguration();
        if (mConfigurations == null) return null;
        mUsedPoint.clear();
        for (Iterator<WifiConfiguration> iterator = mConfigurations.iterator(); iterator != null && iterator.hasNext();) {
            mWifiConfiguration = iterator.next();
            mUsedPoint.put(WifiHelper.dealWithSSID(mWifiConfiguration.SSID), mWifiConfiguration);
        }
        if (mCueentConnectWifiInfo != null && mCueentConnectWifiInfo.size() > 0) {
            mCueentConnectWifiInfo.clear();
        } else {
            mCueentConnectWifiInfo = new ArrayList<>();
        }
        if (mUsedPoint.containsKey(currentSsid)) {
            mCueentConnectWifiInfo.add(mWifiConfiguration.SSID);
            mCueentConnectWifiInfo.add(mWifiUtil.getLinkSpeed());
            mCueentConnectWifiInfo.add(WifiHelper.intIpToStringIp(mWifiUtil.getIpAddress()));
            mCueentConnectWifiInfo.add(String.valueOf(mWifiConfiguration.networkId));
        }

        return mCueentConnectWifiInfo;
    }

    public void ignoreWifiByConnected(String ssid){
        //获取当前正在连接的网络id
        if (mCueentConnectWifiInfo != null && mCueentConnectWifiInfo.size() > 0) {
            if (ssid.equals(WifiHelper.dealWithSSID(mCueentConnectWifiInfo.get(0)))) {
                int netWorkId = Integer.valueOf(mCueentConnectWifiInfo.get(3));
                if (netWorkId <= 0) {
                    LogUtil.i("llf_ignore", netWorkId+"");
                }
                mWifiManager.removeNetwork(netWorkId);
            }
        }
    }

    public Map<String, WifiBean> getmNeverUsedPoint() {
        mConfigurations = mWifiUtil.getConfiguration();
        if (mConfigurations == null) return null;
        mUsedPoint.clear();
        mNeverUsedPoint.clear();

        for (Iterator<WifiConfiguration> iterator = mConfigurations.iterator(); iterator != null && iterator.hasNext();) {
            mWifiConfiguration = iterator.next();
            mUsedPoint.put(WifiHelper.dealWithSSID(mWifiConfiguration.SSID), mWifiConfiguration);
        }
        for (Iterator<WifiBean> iterator = mWifiBeanList.iterator(); iterator != null && iterator.hasNext();) {
            WifiBean bean = iterator.next();
            if (!mUsedPoint.containsKey(bean.getSsid())) {
                mNeverUsedPoint.put(bean.getSsid(), bean);
            }
        }
        return mNeverUsedPoint;
    }

    public void ignoreWifiByConnected(WifiBean bean) {
        mConfigurations = mWifiUtil.getConfiguration();
        if (mConfigurations == null) return;
        for (Iterator<WifiConfiguration> iterator = mConfigurations.iterator(); iterator.hasNext();) {
            mWifiConfiguration = iterator.next();
            String w_ssid = WifiHelper.dealWithSSID(mWifiConfiguration.SSID);
            String currentSsid = bean.getSsid();
            if (currentSsid.equals(w_ssid)) {
                mWifiManager.removeNetwork(mWifiConfiguration.networkId);
            }
        }
    }

    public void notifitionConnectedWifiInfo(WifiBean bean) {
        Intent intent = new Intent();
        intent.putExtra("SSID", bean.getSsid());
        intent.putExtra("CAPABILITIES", bean.getCapabilities());
        WifiUtil wifiUtil = WifiUtil.getInstance(mContext);
        intent.putExtra("CURRENT_SPEED", wifiUtil.getLinkSpeed());
        intent.putExtra("IP_ADDRESS", WifiHelper.intIpToStringIp(wifiUtil.getIpAddress()));
        intent.putExtra("NETWORK_ID", bean.getNetworkId()+"");
        SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW_CONNECTING_WIFI_INFO, intent);
    }

    public void connectWifiByConnected(WifiBean bean) {
        mWifiUtil.startScan();
        mConfigurations = mWifiUtil.getConfiguration();
        if (mConfigurations == null) return;
        for (Iterator<WifiConfiguration> iterator = mConfigurations.iterator(); iterator.hasNext();) {
            mWifiConfiguration = iterator.next();
            String w_ssid = WifiHelper.dealWithSSID(mWifiConfiguration.SSID);
            String currentSsid = bean.getSsid();
            if (currentSsid.equals(w_ssid)) {
                WifiConfiguration cc = mWc.IsExsits(w_ssid);
                mWc.connect(cc);

                notifitionConnectedWifiInfo(bean);
            }
        }
    }

    public void connectWifibyFirstConnect(WifiBean bean) {
        Intent intent = new Intent();
        intent.putExtra("SSID", bean.getSsid());
        intent.putExtra("CAPABILITIES", bean.getCapabilities());
        SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW_WIFI_CONNECT_DIALOG_VIEW, intent);
        //((MainActivity)mContext).openWifiConnectView(currentSsid, bean.getCapabilities());
    }

}
