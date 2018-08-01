package com.wifi.xiaomiguide.View;

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

import com.wifi.xiaomiguide.Adapter.WifiSettingAdapter;
import com.wifi.xiaomiguide.Bean.WifiBean;
import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.Business.WifiConnect;
import com.wifi.xiaomiguide.Business.WifiHelper;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.Dialog.ConnectWifiDialogView;
import com.wifi.xiaomiguide.R;
import com.wifi.xiaomiguide.Util.WifiUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WifiSettingView extends RelativeLayout implements View.OnClickListener, WifiSettingAdapter.OnWifiItemClickListener
        , WifiSettingAdapter.OnWifiSetIconClickListener {

    private Context mContext;
    private ChangeViewInterface mChangeViewInterface;
    private ListView mListView;
    private TextView mGoOnBtn, mJumpBtn, mBackBtn;

    private static final int WIFI_ENABLED = 2;
    private List<WifiBean> mWifiBeanList;
    private WifiSettingAdapter mWifiListAdapter;

    private WifiUtil mWifiUtil;
    private WifiConnect mWifiConnect;
    private WifiManager mWifiManager;

    /**从未连接过的热点*/
    private Map<String, WifiBean> mNeverUsedPoint = new HashMap<String, WifiBean>();
    private List<WifiConfiguration> mConfigurations;
    private WifiConfiguration mWifiConfiguration;

    public WifiSettingView(Context context, ChangeViewInterface changeViewInterface1) {
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
        View.inflate(getContext(), R.layout.wifi_setting_view,this);
        mListView = (ListView) findViewById(R.id.listview);

        mGoOnBtn = (TextView) findViewById(R.id.go_on);
        mJumpBtn = (TextView) findViewById(R.id.jump);
        mBackBtn = (TextView) findViewById(R.id.back);
        try {
            mGoOnBtn.setTextColor(getContext().getResources().getColor(R.color.wifi_gray_deep));
            mGoOnBtn.setEnabled(false);
            mJumpBtn.setTextColor(getContext().getResources().getColor(R.color.wifi_gray));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setViews() {
        checkWifiState();
        mJumpBtn.setVisibility(View.VISIBLE);
        mWifiListAdapter = new WifiSettingAdapter(mContext, mWifiBeanList);
        mListView.setAdapter(mWifiListAdapter);

    }

    private void setListeners() {
        mGoOnBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mJumpBtn.setOnClickListener(this);
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
        mWifiListAdapter.setOnWifiSetIconListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_on) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.USAGE_PRIVACY_VIEW, null);

        } else if (id == R.id.back) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW, 0);

            onDestroyBordCast();
        }

        else if (id == R.id.jump) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.USAGE_PRIVACY_VIEW, null);

        }
    }

    @Override
    public void onWifiItemClick(WifiSettingAdapter.ViewHolder holder, int position, WifiBean bean, String currentUsedSSID) {
        String ssid = bean.getSsid();
        if (currentUsedSSID != null && currentUsedSSID.equals(ssid)) {
            return;
        } else {
            //弹出连接框
            mNeverUsedPoint = WifiUtil.getInstance(mContext).getNeverUsedPoint();
            /**从来没使用过的热点，弹出输入对话框**/
            if (mNeverUsedPoint.containsKey(ssid)) {
                final ConnectWifiDialogView dialog = new ConnectWifiDialogView(mContext, ssid, bean.getCapabilities());
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

    @Override
    public void onWifiSetIconClick(WifiSettingAdapter.ViewHolder holder, int position, WifiBean bean, String curUsedSsid) {
        mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
        mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_CHECK_SET_INFO_VIEW, bean);

        onDestroyBordCast();
    }
}
