package com.wifi.vivoguide.y79;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.LanguageBusiness;
import com.wifi.vivoguide.Business.WifiDataBusiness;
import com.wifi.vivoguide.Business.WifiHelper;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.LogUtil;
import com.wifi.vivoguide.util.SharePreferencesUtil;
import com.wifi.vivoguide.util.WifiUtil;
import com.wifi.vivoguide.wifi.Activity.LongClickShowView;
import com.wifi.vivoguide.wifi.Activity.WifiSetLoginView;
import com.wifi.vivoguide.wifi.Activity.WifiStaticConnectSetView;
import com.wifi.vivoguide.wifi.Adapter.WifiListAdapter;
import com.wifi.vivoguide.wifi.Dialog.WifiConnectDialogView;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xuqunxing on 2017/11/27.
 */
public class VivoWifiSelectView_y79 extends RelativeLayout implements SystemEvent.EventListener {
    protected static final int REFRESH_VIEW = 1;
    protected static final int WIFI_ENABLED = 2;
    protected static final int REQUEST_CODE = 1;
    private WifiManager mWifiManager;

    private Context mActivity;
    private ListView mWifiList;
    private ScrollView mScrollview;
    private ProgressBar mProgressBar;
    private RelativeLayout mBackBtn;
    private TextView mTitleTxt;
    private TextView mLeftText;
    private TextView mWifiDoing;
    private View toolbarView;
    private View mWifiSetView;
    private ImageView mWifiConnectImgInWlan;
    private RelativeLayout mCurrentConnectedWifiLayout;
    private RelativeLayout mWifiConnectedItem;
    private ImageView mWifiFlagIcon;
    private ImageView mWifiKeyLock;
    private TextView mWifiSsid;
    private RelativeLayout mViewContainer;
    private RelativeLayout mWifiListLayout;
    private ImageView mGoWifiSet;

    private WifiUtil mWifiUtil;
    private WifiListAdapter mWifiListAdapter;
    private List<WifiBean> mWifiBeanList;
    private Timer mTime;
    private WifiStaticConnectSetView mStaticView;
    private ArrayList<String> mSaveConnectedInfo =new ArrayList<>();
    private String mWifiConnectedInfo = null;
    private WifiDataBusiness mBaseWifiBusiness;
    private String mCurrentSSID;
    public static boolean mIsLongPress = false;
    IWifiListViewOnReturn mListeners;
    private WifiSetLoginView mSetLoginView;
    private boolean mIsPauseRemove = true;
    private ChangeViewInterface mGuideCallbackListener;
    private LinearLayout advanceSetLL;

    public VivoWifiSelectView_y79(Context context) {
        super(context);
    }

    public VivoWifiSelectView_y79(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VivoWifiSelectView_y79(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View getmWifiSetView(){
        return mWifiSetView;
    }

    @SuppressLint("WifiManagerLeak")
    public VivoWifiSelectView_y79(Context activity, ChangeViewInterface changeViewInterface1) {
        super(activity);
        mActivity = activity;
        mGuideCallbackListener = changeViewInterface1;
        //mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mWifiManager = (WifiManager)mActivity.getSystemService(Context.WIFI_SERVICE);
        mWifiSetView = LayoutInflater.from(activity).inflate(R.layout.vivo_wifi_list_main_y79, this);

        findViewsById();
        setViews();
        setListeners();
        ///setWifi((Activity)mActivity,true);
        onResume();
    }

    private void findViewsById() {
        mWifiListLayout =  (RelativeLayout) mWifiSetView.findViewById(R.id.wifi_list_layout);
        mScrollview = (ScrollView) mWifiSetView.findViewById(R.id.scrollview);
        mWifiDoing = (TextView) mWifiSetView.findViewById(R.id.doing_wifi);
        toolbarView = (View)mWifiSetView.findViewById(R.id.common_title);
        mWifiConnectImgInWlan = (ImageView) mWifiSetView.findViewById(R.id.wifi_connect_img_in_wlan);

        mCurrentConnectedWifiLayout = (RelativeLayout) mWifiSetView.findViewById(R.id.show_connected_wifi_info);
        mWifiConnectedItem = (RelativeLayout) mWifiSetView.findViewById(R.id.wifi_ssid_layout);
        mWifiFlagIcon = (ImageView) mWifiSetView.findViewById(R.id.wifi_flag_icon);
        mWifiKeyLock = (ImageView) mWifiSetView.findViewById(R.id.wifi_key_lock);
        mWifiSsid = (TextView) mWifiSetView.findViewById(R.id.wifi_ssid);
        mGoWifiSet = (ImageView) mWifiSetView.findViewById(R.id.go_wifi_set);

        mProgressBar = (ProgressBar)mWifiSetView.findViewById(R.id.progressbar);
        mBackBtn = (RelativeLayout) mWifiSetView.findViewById(R.id.cancel);
        mTitleTxt = (TextView) mWifiSetView.findViewById(R.id.toobarTitle);
        mLeftText = (TextView) mWifiSetView.findViewById(R.id.tooba_left_text);
        mWifiList = (ListView) mWifiSetView.findViewById(R.id.wifi_list);
        mViewContainer = (RelativeLayout) mWifiSetView.findViewById(R.id.view);
        advanceSetLL = (LinearLayout) findViewById(R.id.advance_set_ll);
        mTitleTxt.setText(getContext().getString(R.string.vivo_wifi_title_y79));
        mLeftText.setVisibility(VISIBLE);

        advanceSetLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try{//进入高级设置（暂时不能跳进去）
                    Intent accountIntent = new Intent();
                    accountIntent.setComponent(new ComponentName("com.android.wifisettings", "com.android.wifisettings.SubSettings"));
                    accountIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mActivity.startActivity(accountIntent);  //startActivityForResult(accountIntent, 1);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mLeftText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToNextView();
            }
        });
    }

    private void setViews() {
        mBaseWifiBusiness = new WifiDataBusiness(mActivity);
        mScrollview.smoothScrollTo(0, 0);
        mProgressBar.setVisibility(View.GONE);
        mViewContainer.setVisibility(View.GONE);
        mWifiConnectImgInWlan.setVisibility(View.GONE);
        mCurrentConnectedWifiLayout.setVisibility(View.GONE);
        mWifiUtil = WifiUtil.getInstance(mActivity);
        mWifiList.setVisibility(View.VISIBLE);
    }


    public void onBackPressed() {
        if(mGuideCallbackListener != null){
            mGuideCallbackListener.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
        }
    }

    public interface IWifiListViewOnReturn {
        void onBackClick();
    }

    public void setOnBackListeners(IWifiListViewOnReturn l) {
        mListeners = l;
    }

    private void setListeners() {
        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mGuideCallbackListener != null){
                    mGuideCallbackListener.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW);
                }

            }
        });
        mWifiConnectedItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsLongPress) return;
                onClickConnectedWifi();
            }
        });

        mWifiConnectedItem.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mIsLongPress = true;
                openLongClickShowView(mActivity, mCurrentSSID, LongClickShowView.CONNECTING_WIFI);
                return false;
            }
        });

        mGoWifiSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("TITLE_SSID", mCurrentSSID);
                SystemEvent.fireEvent(SystemEventConst.EVENT_SHOW__WIFI_CONNECT_CONTAINER_VIEW, intent);
            }
        });

        SystemEvent.addListener(SystemEventConst.EVENT_SHOW_CONNECTING_WIFI_INFO, this);
        SystemEvent.addListener(SystemEventConst.EVENT_SHOW__WIFI_CONNECT_CONTAINER_VIEW, this);
        SystemEvent.addListener(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW, this);
        SystemEvent.addListener(SystemEventConst.EVENT_SHOW_WIFI_CONNECT_DIALOG_VIEW, this);
        SystemEvent.addListener(SystemEventConst.EVENT_SHOW_LONG_CLICK_VIEW, this);
    }

    public void setCurrentLanguageType(int pos) {
        Configuration config = LanguageBusiness.getCurrentLanguage(mActivity, pos);
        DisplayMetrics dm = mActivity.getResources().getDisplayMetrics();
        mActivity.getResources().updateConfiguration(config, dm);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            doWithDoingScanTips(View.VISIBLE, mActivity.getString(R.string.doing_scan));
            switch (msg.what) {
                case REFRESH_VIEW:
                    mWifiUtil.startScan();
                    break;
                case WIFI_ENABLED:
                    mWifiUtil.startScan();
                    break;
                default:
                    break;
            }
        }
    };

    public void onDestroyBordCast() {
        if (receiver != null) {
            mActivity.unregisterReceiver(receiver);
            receiver = null;
        }

        if (scanCompletereceiver != null) {
            mActivity.unregisterReceiver(scanCompletereceiver);
            scanCompletereceiver = null;
        }
        SystemEvent.removeListener(SystemEventConst.EVENT_SHOW_CONNECTING_WIFI_INFO, this);
        SystemEvent.removeListener(SystemEventConst.EVENT_SHOW__WIFI_CONNECT_CONTAINER_VIEW, this);
        SystemEvent.removeListener(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW, this);
        SystemEvent.removeListener(SystemEventConst.EVENT_SHOW_WIFI_CONNECT_DIALOG_VIEW, this);
        SystemEvent.removeListener(SystemEventConst.EVENT_SHOW_LONG_CLICK_VIEW, this);

        if (mStaticView != null) {
            mStaticView.onDestoryEvent();
        }

        if (mSetLoginView!= null) {
            mSetLoginView.onDestroyEvent();
        }
    }

    private void registerWifiBordcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        mActivity.registerReceiver(receiver, filter);
        mActivity.registerReceiver(scanCompletereceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    private void refreshWifiView() {
        mTime = new Timer();
        mTime.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(REFRESH_VIEW);
            }
        }, 5000, 6000);
    }

    private void checkWifiState() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiUtil.openWifi();
        }
    }

    public void onResume() {
        checkWifiState();
        registerWifiBordcast();
        refreshWifiView();
    }

    public void onPause() {
        if (null != mTime) {
            mTime.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onPause();
        onDestroyBordCast();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            int state = mWifiUtil.checkState();
            switch (state) {
                case WifiManager.WIFI_STATE_ENABLED:
                    mHandler.sendEmptyMessage(WIFI_ENABLED);
                    break;
                default:
                    break;
            }
        }
    };

    private void doWithDoingScanTips(int b, String tips) {
        if (b == View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

        mCurrentSSID = WifiHelper.dealWithSSID(WifiUtil.getInstance(mActivity).getSSID());
        LogUtil.i("WifiSetView_error", mCurrentSSID);
        if (!"NULL".equals(mCurrentSSID) && !"0x".equals(mCurrentSSID)
                && !"<unknown ssid>".equals(mCurrentSSID)) {
            LogUtil.i("WifiSetView", mCurrentSSID);
            mCurrentConnectedWifiLayout.setVisibility(View.VISIBLE);
            mWifiConnectImgInWlan.setVisibility(View.VISIBLE);
            mWifiSsid.setText(mCurrentSSID);
            mWifiDoing.setText(mActivity.getString(R.string.connected_to_wifi_name, mCurrentSSID));
            return;
        } else {
            mCurrentConnectedWifiLayout.setVisibility(View.GONE);
        }
        mWifiConnectImgInWlan.setVisibility(View.GONE);
        mWifiDoing.setVisibility(b);
        mWifiDoing.setText(tips);
    }

    private void jumpToNextView() {
        if(mGuideCallbackListener != null){//LOGIN_VIVO_ACCOUNT
            mGuideCallbackListener.setCurrentlyShowView(ChangeViewInterface.SELECT_SERVICE);
        }
//        mViewContainer.setVisibility(View.VISIBLE);
//        mViewContainer.removeAllViews();
//        mViewContainer.bringToFront();
//        mSetLoginView = new WifiSetLoginView(mActivity, mGuideCallbackListener);
//        mViewContainer.addView(mSetLoginView.getWifiSetLoginView());
    }
    private BroadcastReceiver scanCompletereceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (!mIsPauseRemove) return;
            mWifiUtil.dualWithScanResult();
            mWifiBeanList = mWifiUtil.getWifiData();
            if (mWifiListAdapter != null) {
                mWifiListAdapter.setData(mWifiBeanList);
                mWifiListAdapter.notifyDataSetChanged();
            } else {
                mWifiListAdapter = new WifiListAdapter(mActivity, mWifiBeanList);
                mWifiList.setAdapter(mWifiListAdapter);
            }
            doWithDoingScanTips(View.GONE,"");
        }
    };

    public void openWifiConnectView(String ssid, String capabilities) {
        final WifiConnectDialogView dialog = new WifiConnectDialogView(mActivity, ssid, capabilities);
        dialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.showKeyBoard();
            }
        }, 300);
    }

    public void openLongClickShowView(Context activity, String ssid, String currentShowView) {
        LongClickShowView longClickShowView = new LongClickShowView(activity, null, ssid, currentShowView);
        mViewContainer.setVisibility(View.VISIBLE);
        mViewContainer.removeAllViews();
        mViewContainer.bringToFront();
        mViewContainer.addView(longClickShowView.getLongClickShowView());
    }

    private void getLongClickView() {
        mViewContainer.setVisibility(View.VISIBLE);
        mViewContainer.removeAllViews();
        mViewContainer.bringToFront();
        mViewContainer.addView( mWifiListAdapter.getLongClickView().getLongClickShowView());
    }

    @Override
    public void onEvent(SystemEventConst eventType, Intent data) {
        if (eventType.equals(SystemEventConst.EVENT_SHOW_CONNECTING_WIFI_INFO)) {
            if (data != null) {
                mCurrentConnectedWifiLayout.setVisibility(View.VISIBLE);
                mWifiSsid.setText(data.getStringExtra("SSID"));
                doWithDoingScanTips(View.VISIBLE, "");
                mSaveConnectedInfo.add(data.getStringExtra("SSID"));
                mSaveConnectedInfo.add(mActivity.getString(R.string.wifi_connected_info
                        , data.getStringExtra("CURRENT_SPEED")
                        ,data.getStringExtra("CAPABILITIES")
                        ,data.getStringExtra("IP_ADDRESS")) );
                mSaveConnectedInfo.add(data.getStringExtra("CAPABILITIES"));
                mSaveConnectedInfo.add(data.getStringExtra("NETWORK_ID"));
                SharePreferencesUtil.putString(mActivity, SharePreferencesUtil.SAVE_WIFI_CONNECTED_INFO, mSaveConnectedInfo.toString());

                //跳转到下一步   补上需延迟秒
                Message msg = new Message();
                msg.arg1 = 1;
                jumpStepHandler.sendMessageDelayed(msg, 3000);

            }
        } else if (eventType.equals(SystemEventConst.EVENT_SHOW__WIFI_CONNECT_CONTAINER_VIEW)){
            if (data == null)  return;
            mIsPauseRemove = false;
            mViewContainer.setVisibility(View.VISIBLE);
            mViewContainer.removeAllViews();
            mStaticView = new WifiStaticConnectSetView(mActivity, data.getStringExtra("TITLE_SSID"));
            mViewContainer.addView(mStaticView.getWifiStaticConnectView());
            mViewContainer.bringToFront();
        } else if (eventType.equals(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW)) {
            if (data != null) {
                mIsPauseRemove = data.getBooleanExtra("PAUSE_REMOVE", false);
            }
            mViewContainer.setVisibility(View.GONE);
            mIsLongPress = false;

        } else if (eventType.equals(SystemEventConst.EVENT_SHOW_WIFI_CONNECT_DIALOG_VIEW)) {
            if (data != null) {
                openWifiConnectView(data.getStringExtra("SSID"), data.getStringExtra("CAPABILITIES"));
            }
        } else if (eventType.equals(SystemEventConst.EVENT_SHOW_LONG_CLICK_VIEW)) {
            getLongClickView();
        }
    }

    public Handler jumpStepHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            jumpToNextView();
        }
    };

    private void onClickConnectedWifi() {
//        mWifiConnectedInfo = SharePreferencesUtil.getString(mActivity, SharePreferencesUtil.SAVE_WIFI_CONNECTED_INFO);
//        mWifiConnectedInfo = mWifiConnectedInfo.replace("[", " ").replace("]", " ");
//        final String[] arr = mWifiConnectedInfo.split(",");
//        if (mWifiConnectedInfo == null || mWifiConnectedInfo.length() == 0) return;
        if (mBaseWifiBusiness == null) return;
        final ArrayList<String> info =  mBaseWifiBusiness.getCurrentConnectWifiInfo(mCurrentSSID);
        if (info.size() == 0) return;

        WifiHelper.showAlertDialog(mActivity
                , mCurrentSSID
                , mActivity.getString(R.string.wifi_connected_info, info.get(1) ,"802.1x EAP" ,info.get(2))
                , mActivity.getString(R.string.forgot_wifi), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBaseWifiBusiness.ignoreWifiByConnected(mCurrentSSID);
                        //mWifiManager.removeNetwork(Integer.valueOf(arr[2]));
                        dialog.dismiss();
                    }
                }
                , "", null
                ,mActivity.getString(R.string.cancel_wifi), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                } );
    }
}
