package com.zteguidedemo.v0840.wifi.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.v0840.ChangeViewInterface;
import com.zteguidedemo.v0840.wifi.Bean.WifiBean;
import com.zteguidedemo.v0840.wifi.Business.WifiConnect;
import com.zteguidedemo.v0840.wifi.Business.WifiHelper;
import com.zteguidedemo.v0840.wifi.adapter.ZTEWifiSettingAdapter;
import com.zteguidedemo.v0840.wifi.util.WifiUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ZTEWifiSettingView extends RelativeLayout implements View.OnClickListener, ZTEWifiSettingAdapter.OnWifiItemClickListener {

    private Context mContext;
    private ChangeViewInterface mChangeViewInterface;
    private ListView mListView;
    private TextView mPreStepBtn, mNextBtn;

    private static final int WIFI_ENABLED = 2;
    private List<WifiBean> mWifiBeanList;
    private ZTEWifiSettingAdapter mWifiListAdapter;

    private WifiUtil mWifiUtil;
    private WifiConnect mWifiConnect;
    private WifiManager mWifiManager;

    /**从未连接过的热点*/
    private Map<String, WifiBean> mNeverUsedPoint = new HashMap<String, WifiBean>();
    private List<WifiConfiguration> mConfigurations;
    private WifiConfiguration mWifiConfiguration;

    public ZTEWifiSettingView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.mChangeViewInterface = changeViewInterface1;
        mContext = context;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mWifiUtil = WifiUtil.getInstance(mContext);
        mWifiConnect = new WifiConnect(mWifiManager);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        View.inflate(getContext(), R.layout.zx_wifi_setting_view,this);
        mListView = (ListView) findViewById(R.id.zx_listview);

        mPreStepBtn = (TextView) findViewById(R.id.zte_previous_step_btn);
        mNextBtn = (TextView) findViewById(R.id.zte_next_step_btn);
    }

    private void setViews() {
        checkWifiState();
        mWifiListAdapter = new ZTEWifiSettingAdapter(mContext, mWifiBeanList);
        mListView.setAdapter(mWifiListAdapter);

    }

    private void setListeners() {
        mPreStepBtn.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        setListViewClickEnent();
        registerBordcast();
    }

    private void checkWifiState() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiUtil.openWifi();
        }
    }

    private void registerBordcast() {
        IntentFilter filter = new IntentFilter();
        //注册网络监听
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mContext.registerReceiver(mNetworkStateReceiver, filter);

        IntentFilter scanFilter = new IntentFilter();
        scanFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mContext.registerReceiver(mScanCompletereceiver, scanFilter);
    }

    public void onDestroyBordCast() {

        if (mNetworkStateReceiver != null) {
            mContext.unregisterReceiver(mNetworkStateReceiver);
            mNetworkStateReceiver = null;
        }

        if (mScanCompletereceiver != null) {
            mContext.unregisterReceiver(mScanCompletereceiver);
            mScanCompletereceiver = null;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WIFI_ENABLED:
                    mWifiUtil.startScan();
                    break;
                default:
                    break;
            }
        }
    };

    private BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {

            int state1 = mWifiUtil.checkState();
            switch (state1) {
                case WifiManager.WIFI_STATE_ENABLED:
                    mHandler.sendEmptyMessage(WIFI_ENABLED);
                    break;
                default:
                    break;
            }
        }
    };

    private BroadcastReceiver mScanCompletereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            mWifiUtil.dualWithScanResult();
            mWifiBeanList = mWifiUtil.getWifiData();
            if (mWifiListAdapter != null) {
                mWifiListAdapter.setData(mWifiBeanList);
                mWifiListAdapter.notifyDataSetChanged();
            }
        }
    };

    private void setListViewClickEnent() {
        mWifiListAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.zte_next_step_btn) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_VIEW);
            onDestroyBordCast();
        } else if (id == R.id.zte_previous_step_btn) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
            onDestroyBordCast();
        }

    }

    private void showConnectedDialogStateInfo(WifiBean bean) {
        String level = mWifiUtil.getRssi(bean.getLevel()).getName(mContext);
        String keyMgmt = WifiHelper.changeName(mContext, WifiHelper.getKeyMgmt(bean.getCapabilities()));
        String ipAddress = WifiHelper.intIpToStringIp(mWifiUtil.getIpAddress());
        final int netWorkId = bean.getNetworkId();
        String paramStr = bean.getSsid()+","+level+","+keyMgmt+","+ipAddress+","+netWorkId;
        ZTEConnectedWifiDialogView dialog = new ZTEConnectedWifiDialogView(mContext, paramStr);

        dialog.show();
    }

    @Override
    public void onWifiItemClick(ZTEWifiSettingAdapter.ViewHolder holder, int position, WifiBean bean, String currentUsedSSID) {
        final String ssid = bean.getSsid();
        if (currentUsedSSID != null && currentUsedSSID.equals(ssid)) {
            showConnectedDialogStateInfo(bean);
        } else {
            //弹出连接框
            mNeverUsedPoint = WifiUtil.getInstance(mContext).getNeverUsedPoint();
            /**从来没使用过的热点，弹出输入对话框**/
            if (mNeverUsedPoint.containsKey(ssid)) {
                ZTEConnectWifiDialogView dialog = new ZTEConnectWifiDialogView(mContext, ssid, bean.getCapabilities());
                dialog.show();
            } else {
                connectWifiByConnected(bean);
            }
        }
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
                WifiConfiguration cc = mWifiConnect.IsExsits(w_ssid);
                mWifiConnect.connect(cc);
            }
        }
    }
}
