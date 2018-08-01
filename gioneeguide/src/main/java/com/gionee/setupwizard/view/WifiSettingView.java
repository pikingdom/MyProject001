package com.gionee.setupwizard.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gionee.setupwizard.R;
import com.gionee.setupwizard.utils.WifiUtils;
import com.gionee.setupwizard.widget.TimeView;
import com.gionee.setupwizard.widget.WifiConnectDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：xiaomao on 2017/7/14.
 */

public class WifiSettingView extends LinearLayout {
    public static final String WIFI_AUTH_OPEN = "";
    public static final String WIFI_AUTH_ROAM = "[ESS]";

    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    private TimeView mTimeView;
    private ListView mWifiListView;
    private Button mJumpButton;

    private Handler handler = new Handler();
    private WifiUtils wifiUtils;
    private WifiReceiver mWifiStateReceiver;
    private List<ScanResult> list = new ArrayList<>();
    private WifiAdapter adapter;
    public WifiSettingView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initView();
        initWifi();
        initListener();
    }

    private void initWifi(){
        mWifiStateReceiver = new WifiReceiver();
        wifiUtils = WifiUtils.getInstance(context);
        try{
            wifiThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_wifi,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        mTimeView =  (TimeView) findViewById(R.id.gn_sw_layout_wifi_time);
        mTimeView.updateTime(System.currentTimeMillis(),false);
        mWifiListView = (ListView) findViewById(R.id.gn_sw_layout_wifi_list);
        adapter = new WifiAdapter();
        mWifiListView.setAdapter(adapter);
        mJumpButton = (Button) findViewById(R.id.gn_sw_layout_wifi_jumpbutton);
    }

    /**
     * 初始化监听
     */
    private void initListener(){
        mWifiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final ScanResult scanResult = list.get(i);
                if (wifiUtils.getConnectedSSID().equals(scanResult.SSID)){//已连接上当前网络，直接返回
                    return;
                }
                int netId = wifiUtils.IsConfiguration(scanResult.SSID);
                if (netId != -1){//有配置好过
                    if (wifiUtils.ConnectWifi(netId)){//已连接
                        ViewHolder viewHolder = (ViewHolder)view.getTag();
                        viewHolder.stateView.setText(R.string.gn_sw_string_wificonnect);
                        //把按钮变成下一步
                        mJumpButton.setText(R.string.gn_sw_string_next);
                    }
                }else{//弹对话框输入密码
                   WifiConnectDialog.Builder builder = new WifiConnectDialog.Builder(context);
                    builder.setTitle(scanResult.SSID+"");
                    WifiConnectDialog dialog = builder.create(new WifiConnectDialog.OnCustomDialogListener() {
                        @Override
                        public void back(String str) {
                            int netId = wifiUtils.AddWifiConfig(list,scanResult.SSID, str);
                            if(netId != -1){
                                wifiUtils.getConfiguration();//添加了配置信息，要重新得到配置信息
                                if(wifiUtils.ConnectWifi(netId)){
                                    ViewHolder viewHolder = (ViewHolder)view.getTag();
                                    viewHolder.stateView.setText(R.string.gn_sw_string_wificonnect);
                                    //把按钮变成下一步
                                    mJumpButton.setText(R.string.gn_sw_string_next);
                                }
                            }else{
                                Toast.makeText(context, R.string.gn_sw_string_wifi_not_connect, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Window dialogWindow = dialog.getWindow();
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    dialogWindow.setGravity(Gravity.CENTER);
                    lp.width = WifiConnectDialog.getScreenWH(context)[0]; // 宽度
                    dialogWindow.setAttributes(lp);
                    dialog.show();
                }
            }
        });
        mJumpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.TIME_VIEW);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.SCAN_RESULTS");
        filter.addAction("android.net.wifi.NETWORK_IDS_CHANGED");
        filter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        filter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        filter.addAction("android.net.wifi.LINK_CONFIGURATION_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.net.wifi.RSSI_CHANGED");
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.intent.action.TIMEZONE_CHANGED");
        filter.addAction("android.intent.action.TIME_SET");
        filter.addAction("android.intent.action.TIME_TICK");
        context.registerReceiver(this.mWifiStateReceiver, filter);
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除广播
        if (mWifiStateReceiver != null){
            context.unregisterReceiver(this.mWifiStateReceiver);
        }
    }
    /**
     * 监听WIFI变化广播
     */
    private class WifiReceiver extends BroadcastReceiver {
        private WifiReceiver() {
        }
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.intent.action.TIMEZONE_CHANGED") || action.equals("android.intent.action.TIME_SET") || action.equals("android.intent.action.TIME_TICK")) {
                mTimeView.updateTime(System.currentTimeMillis(),false);
            }else{
                try{
                    wifiThread.start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private Thread wifiThread = new Thread(new Runnable() {
        @Override
        public void run() {
            //开始扫描
            wifiUtils.WifiOpen();
            wifiUtils.WifiStartScan();
            while(wifiUtils.WifiCheckState() != WifiManager.WIFI_STATE_ENABLED){//等待Wifi开启
            }
            wifiUtils.getConfiguration();
            list = wifiUtils.getScanResults();
            //去除同名的wifi
            List<ScanResult> tmp = new ArrayList<>();
            for (ScanResult sr : list){
                boolean isRepeat = false;
                for(ScanResult sr1 : tmp){
                    if (sr1.SSID.equals(sr.SSID)){
                        isRepeat = true;
                        break;
                    }
                }
                if (!isRepeat){
                    tmp.add(sr);
                }
            }
            list.clear();
            list.addAll(tmp);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    });

    private class WifiAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int i) {
            return list.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null){
                view = inflate(context,R.layout.gn_item_layout_wifi,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.gn_sw_layout_wifi_item_image);
                viewHolder.nameView = (TextView) view.findViewById(R.id.gn_sw_layout_wifi_item_wifiname);
                viewHolder.stateView = (TextView) view.findViewById(R.id.gn_sw_layout_wifi_item_wifistate);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)view.getTag();
            }
            //开始赋值
            ScanResult scanResult = list.get(i);
            viewHolder.nameView.setText(scanResult.SSID);
            if (wifiUtils.getConnectedSSID().equals(scanResult.SSID)){
                viewHolder.stateView.setText(R.string.gn_sw_string_wificonnect);
                //把按钮变成下一步
                mJumpButton.setText(R.string.gn_sw_string_next);
            }else{
                viewHolder.stateView.setText("");
            }
            int level = wifiUtils.getWifiLevel(scanResult.level);//总共分为4个等级
            if (scanResult.capabilities != null &&(scanResult.capabilities.equals(WIFI_AUTH_OPEN) || scanResult.capabilities.equals(WIFI_AUTH_ROAM))){//不要密码
                switch (level){
                    case 1:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_signal_1_light);
                        break;
                    case 2:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_signal_2_light);
                        break;
                    case 3:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_signal_3_light);
                        break;
                    case 4:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_signal_4_light);
                        break;
                    default://默认0
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_signal_0_light);
                        break;
                }
            }else{//需要密码
                switch (level){
                    case 1:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_lock_signal_1_light);
                        break;
                    case 2:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_lock_signal_2_light);
                        break;
                    case 3:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_lock_signal_3_light);
                        break;
                    case 4:
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_lock_signal_4_light);
                        break;
                    default://默认0
                        viewHolder.imageView.setImageResource(R.drawable.gn_sw_wifi_lock_signal_0_light);
                        break;
                }
            }
            return view;
        }
    }
    private class ViewHolder {
        public ImageView imageView;
        public TextView nameView;
        public TextView stateView;
    }
}
