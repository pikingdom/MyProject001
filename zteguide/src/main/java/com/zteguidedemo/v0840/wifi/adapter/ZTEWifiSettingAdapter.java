package com.zteguidedemo.v0840.wifi.adapter;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zteguidedemo.R;
import com.zteguidedemo.v0840.wifi.Bean.WifiBean;
import com.zteguidedemo.v0840.wifi.Business.WifiConnect;
import com.zteguidedemo.v0840.wifi.Business.WifiHelper;
import com.zteguidedemo.v0840.wifi.util.LogUtil;
import com.zteguidedemo.v0840.wifi.util.WifiUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by llf on 2017/8/7.
 */

public class ZTEWifiSettingAdapter extends BaseAdapter {
    public static int WIFI_CONNECTED_STATE = -1;

    private Context mContext;
    private List<WifiBean> mData;
    private LayoutInflater mInflater;
    private String mCurrentUsedSSID;

    private WifiUtil mWifiUtil;

    /**连接过的热点*/
    private Map<String, WifiConfiguration> mUsedPoint = new HashMap<String, WifiConfiguration>();

    public ZTEWifiSettingAdapter(Context activity, List<WifiBean> data) {
        this.mContext = activity;
        //this.mData = data;
        mInflater = LayoutInflater.from(mContext);
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
            converView = mInflater.inflate(R.layout.zx_wifi_settint_item_view, null);
            holder = new ViewHolder();
            holder.wifiItem = (View) converView.findViewById(R.id.zx_wifi_item);
            holder.wifiFlagIcon = (ImageView) converView.findViewById(R.id.zx_wifi_flag_icon);
            holder.wifiKeyLock = (ImageView) converView.findViewById(R.id.zx_wifi_key_lock);
            holder.wifiSsid = (TextView) converView.findViewById(R.id.zx_wifi_ssid);

            converView.setTag(holder);
        } else {
            holder = (ViewHolder) converView.getTag();
        }

        final WifiBean info = mData.get(position);
        holder.wifiFlagIcon.setImageResource(mWifiUtil.getRssi(info.getLevel()).getIconRes(mContext));
        holder.wifiSsid.setTextColor(this.mContext.getResources().getColor(R.color.black));
        holder.wifiSsid.setText(info.getSsid());

        if (mCurrentUsedSSID != null && mCurrentUsedSSID.equals(info.getSsid())) {
            holder.wifiSsid.setTextColor(this.mContext.getResources().getColor(R.color.light_black));

            if (!WifiConnect.isWifiConnect(mContext)) {
                setSpannableText(holder.wifiSsid, info.getSsid(), mContext.getString(R.string.saved));
            }else {
                setSpannableStyleText(holder.wifiSsid, mCurrentUsedSSID);
            }
            LogUtil.i("==== if llf:", info.getSsid());
        } else if (mUsedPoint != null && mUsedPoint.size() > 0 && mUsedPoint.containsKey(info.getSsid())) {
            setSpannableText(holder.wifiSsid, info.getSsid(), mContext.getString(R.string.saved));
            LogUtil.i("====else if llf:", info.getSsid());
        }else {
            LogUtil.i("====else llf:", info.getSsid());
        }

        holder.wifiItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnWifiItemClickListener != null) {
                    mOnWifiItemClickListener.onWifiItemClick(holder, position, info, mCurrentUsedSSID);
                }
            }
        });

        return converView;
    }

    private void setSpannableText(final TextView wifiSsidTxt, final String value, String value2) {

        final SpannableString styledText = new SpannableString(value + "\n" + value2);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.zx_saved_ssid), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.zx_connected_status), value.length(), value.length() + value2.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wifiSsidTxt.setText(styledText, TextView.BufferType.EDITABLE);

    }

    private void setSpannableStyleText(final TextView wifiSsidTxt, final String value) {

        String showStateStr = mContext.getString(R.string.connected);
        final SpannableString styledText = new SpannableString(value + "\n" + showStateStr);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.zx_connect_ssid), 0, value.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(mContext, R.style.zx_connected_status), value.length(), value.length() + showStateStr.length()+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        wifiSsidTxt.setText(styledText, TextView.BufferType.EDITABLE);

    }

    public class ViewHolder {
        public View wifiItem;
        public ImageView wifiFlagIcon;
        public ImageView wifiKeyLock;
        public TextView wifiSsid;
    }

    public interface OnWifiItemClickListener {
        void onWifiItemClick(ViewHolder holder, int position, WifiBean bean, String curUsedSsid);
    }

    private ZTEWifiSettingAdapter.OnWifiItemClickListener mOnWifiItemClickListener;
    public void setOnRecyclerViewListener(OnWifiItemClickListener onWifiItemClickListener) {
        this.mOnWifiItemClickListener = onWifiItemClickListener;
    }
}
