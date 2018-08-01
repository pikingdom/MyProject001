package com.gionee.setupwizard.s10bl.adapter;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gionee.setupwizard.R;
import com.gionee.setupwizard.s10bl.Bean.WifiBean;
import com.gionee.setupwizard.s10bl.Business.WifiConnect;
import com.gionee.setupwizard.s10bl.Business.WifiHelper;
import com.gionee.setupwizard.s10bl.util.WifiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by llf on 2017/8/7.
 */

public class WifiSettingAdapter extends BaseAdapter {
    public static final int WIFI_CONNECTED = 0;
    public static final int WIFI_DISCONNECTED = 1;
    public static int WIFI_CONNECTED_STATE = -1;

    private Context mContext;
    private List<WifiBean> mData;
    private LayoutInflater mInflater;
    private WifiManager mWifiManager;
    private String mCurrentUsedSSID;
    public final String WIFI_AUTH_OPEN = "";
    public final String WIFI_AUTH_ROAM = "[ESS]";
    private WifiUtil mWifiUtil;

    /**连接过的热点*/
    private Map<String, WifiConfiguration> mUsedPoint = new HashMap<String, WifiConfiguration>();
    /**从未连接过的热点*/
    private Map<String, WifiBean> mNeverUsedPoint = new HashMap<String, WifiBean>();
    private List<WifiConfiguration> mConfigurations;
    private WifiConfiguration mWifiConfiguration;

    public WifiSettingAdapter(Context activity, List<WifiBean> data) {
        this.mContext = activity;
        //this.mData = data;
        mInflater = LayoutInflater.from(mContext);
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        mInflater = LayoutInflater.from(mContext);
        mWifiUtil = WifiUtil.getInstance(mContext);

    }

    public void setData(List<WifiBean> data) {
        this.mData = data;
        this.mUsedPoint = WifiUtil.getInstance(mContext).getUsedPoint();
        mCurrentUsedSSID = WifiHelper.dealWithSSID(WifiUtil.getInstance(mContext).getSSID());
    }

    @Override
    public int getCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View converView, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (converView == null) {
            converView = mInflater.inflate(R.layout.gn_wifi_settint_item_view, null);
            holder = new ViewHolder();
            holder.wifiItem = (View) converView.findViewById(R.id.wifi_item);
            holder.wifiFlagIcon = (ImageView) converView.findViewById(R.id.wifi_flag_icon);
           // holder.wifiKeyLock = (ImageView) converView.findViewById(R.id.wifi_key_lock);
            holder.wifiSsid = (TextView) converView.findViewById(R.id.wifi_ssid);
           // holder.wifiSet = (ImageView) converView.findViewById(R.id.wifi_set);
//            holder.selImage = (ImageView) converView.findViewById(R.id.sel_image);

            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }

        final WifiBean info = mData.get(position);
        holder.wifiFlagIcon.setImageResource(mWifiUtil.getRssi(info.getLevel()).getIconRes(mContext));
        holder.wifiSsid.setTextColor(this.mContext.getResources().getColor(R.color.black));
//        holder.selImage.setVisibility(View.GONE);
        holder.wifiSsid.setText(info.getSsid());

        if (mCurrentUsedSSID != null && mCurrentUsedSSID.equals(info.getSsid())) {
            holder.wifiSsid.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));
//            holder.selImage.setVisibility(View.VISIBLE);

            if (!WifiConnect.isWifiConnect(mContext)) {
                setSpannableText(holder.wifiSsid, info.getSsid(), mContext.getString(R.string.saved));
            }else {
                setSpannableStyleText(holder.wifiSsid, mCurrentUsedSSID);
            }
//            String capabilities = info.getCapabilities().trim();
//            if(capabilities != null && (capabilities.equals(WIFI_AUTH_OPEN)) || (capabilities.equals(WIFI_AUTH_ROAM))){
//
//            }else {
//
//            }
        } else if (mUsedPoint != null && mUsedPoint.size() > 0 && mUsedPoint.containsKey(info.getSsid())) {
            setSpannableText(holder.wifiSsid, info.getSsid(), mContext.getString(R.string.saved));
        }else {
        }

        holder.wifiItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnWifiItemClickListener != null) {
                    mOnWifiItemClickListener.onWifiItemClick(holder, position, info, mCurrentUsedSSID);
                }
            }
        });
//
//        holder.wifiSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mOnWifiSetIconClickListener != null) {
//                    mOnWifiSetIconClickListener.onWifiSetIconClick(holder, position, info, mCurrentUsedSSID);
//                }
//            }
//        });

        return converView;
    }

    private void setSpannableText(final TextView wifiSsidTxt, final String value, String value2) {

        final SpannableString styledText = new SpannableString(value + "\n" + value2);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.saved_ssid), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.connected_status), value.length(), value.length() + value2.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wifiSsidTxt.setText(styledText, TextView.BufferType.EDITABLE);

    }

    private void setSpannableStyleText(final TextView wifiSsidTxt, final String value) {
       // if (WIFI_CONNECTED_STATE == -1) return;
//        String showStateStr = null;
//        if (WIFI_CONNECTED_STATE == WIFI_CONNECTED) {
//            showStateStr = mContext.getString(R.string.connected);
//        } else if(WIFI_CONNECTED_STATE == WIFI_DISCONNECTED){
//            if (!mIsErrorConnect) {
//                showStateStr = mContext.getString(R.string.identity_erroe);
//                mIsErrorConnect = true;
//            } else {
//                showStateStr = mContext.getString(R.string.saved);
//            }
//        }
        String showStateStr = mContext.getString(R.string.connected);
        final SpannableString styledText = new SpannableString(value + "\n" + showStateStr);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.connect_ssid), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.connected_status), value.length(), value.length() + showStateStr.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wifiSsidTxt.setText(styledText, TextView.BufferType.EDITABLE);

    }

    public void setCurrentConnectState(int wifiConnectState) {
        WIFI_CONNECTED_STATE = wifiConnectState;
    }

    public class ViewHolder {
        public View wifiItem;
        public ImageView wifiFlagIcon;
//        public ImageView selImage;
//        public ImageView wifiKeyLock;
//        public ImageView wifiSet;
        public TextView wifiSsid;
    }

    public interface OnWifiItemClickListener {
        void onWifiItemClick(ViewHolder holder, int position, WifiBean bean, String curUsedSsid);
    }

    private WifiSettingAdapter.OnWifiItemClickListener mOnWifiItemClickListener;
    public void setOnRecyclerViewListener(OnWifiItemClickListener onWifiItemClickListener) {
        this.mOnWifiItemClickListener = onWifiItemClickListener;
    }

    public interface OnWifiSetIconClickListener {
        void onWifiSetIconClick(ViewHolder holder, int position, WifiBean bean, String curUsedSsid);
    }

    private WifiSettingAdapter.OnWifiSetIconClickListener mOnWifiSetIconClickListener;
    public void setOnWifiSetIconListener(OnWifiSetIconClickListener l) {
        this.mOnWifiSetIconClickListener = l;
    }
}
