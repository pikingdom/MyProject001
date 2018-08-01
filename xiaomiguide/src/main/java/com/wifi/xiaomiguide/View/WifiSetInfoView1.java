package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.Bean.WifiBean;
import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.Business.WifiConnect;
import com.wifi.xiaomiguide.Business.WifiHelper;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.Dialog.DeleteWlanDialogView;
import com.wifi.xiaomiguide.Dialog.IpChooseDialogView;
import com.wifi.xiaomiguide.Dialog.ModifyWlanDialogView;
import com.wifi.xiaomiguide.R;
import com.wifi.xiaomiguide.SlipButtonView;
import com.wifi.xiaomiguide.Util.WifiUtil;

import java.util.Map;

/**
 * Created by llf on 2017/8/7.
 */

public class WifiSetInfoView1 extends RelativeLayout implements View.OnClickListener, IpChooseDialogView.IChooseWhichIpSet{
    private ChangeViewInterface mChangeViewInterface;
    private Context mContext;
    private RelativeLayout mAutoConnectLayout, mPhoneHotLayout, mStateInfoLayout, mConnectSpeedLayout;
    private RelativeLayout mIpAdressLayout, mSubnetMaskLayout, mRouterLayout;
    private RelativeLayout mSignalStrengthLayout, mSafeLayout;
    private TextView mConnectSpeed, mIpAdress,mConnectStateTxt, mSubnetMask, mRouter;
    private SlipButtonView mAutoConnectBtn, mPhoneBtn;
    private TextView mSignValueTxt;
    private TextView mSaveValueTxt;
    private TextView mCancelBtn, mWifiSsidTxt, mSureBtn;
    private RelativeLayout mIpSetLayout;
    private TextView mSetText;
    private LinearLayout mStaticSetView;
    private TextView mModifyWlanBtn, mDeleteWlanBtn;
    private WifiUtil mWifiUtil;
    private WifiManager mWifiManager;

    public WifiSetInfoView1(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.mChangeViewInterface = changeViewInterface1;
        mContext = context;

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        View.inflate(getContext(), R.layout.wifi_set_info_view,this);
        mAutoConnectBtn = (SlipButtonView) findViewById(R.id.auto_slipbtn);
        mPhoneBtn = (SlipButtonView) findViewById(R.id.phone_slipbtn);
        mAutoConnectBtn.setCheck(true);
        mPhoneBtn.setCheck(false);
        mCancelBtn = (TextView) findViewById(R.id.cancel);
        mWifiSsidTxt = (TextView) findViewById(R.id.wifi_ssid);
        mSureBtn = (TextView) findViewById(R.id.sure);

        mAutoConnectLayout = (RelativeLayout) findViewById(R.id.static_layout1);
        mPhoneHotLayout = (RelativeLayout) findViewById(R.id.static_layout2);
        mStateInfoLayout = (RelativeLayout) findViewById(R.id.static_layout3);
        mConnectSpeedLayout = (RelativeLayout) findViewById(R.id.static_layout4);
        mIpAdressLayout = (RelativeLayout) findViewById(R.id.static_layout5);
        mSubnetMaskLayout = (RelativeLayout) findViewById(R.id.static_layout6);
        mRouterLayout = (RelativeLayout) findViewById(R.id.static_layout7);

        mSignalStrengthLayout = (RelativeLayout) findViewById(R.id.static_layout8);
        mSafeLayout = (RelativeLayout) findViewById(R.id.static_layout9);

        mConnectStateTxt = (TextView) findViewById(R.id.connected);

        mSignValueTxt = (TextView) findViewById(R.id.signal_value);
        mSaveValueTxt = (TextView) findViewById(R.id.save_value);

        mIpSetLayout = (RelativeLayout) findViewById(R.id.ip_set_layout);
        mSetText = (TextView) findViewById(R.id.text);
        mStaticSetView = (LinearLayout) findViewById(R.id.static_set_view);

        mModifyWlanBtn = (TextView) findViewById(R.id.modify_wlan);
        mDeleteWlanBtn = (TextView) findViewById(R.id.delete_wlan);

        mConnectSpeed = (TextView) findViewById(R.id.connect_speed_value);
        mIpAdress = (TextView) findViewById(R.id.ip_address_value);
        mSubnetMask = (TextView) findViewById(R.id.subnet_mask_value);
        mRouter = (TextView) findViewById(R.id.router_value);

    }

    private void setViews() {
        mWifiUtil = WifiUtil.getInstance(mContext);
        mStaticSetView.setVisibility(View.GONE);
    }

    private void setListeners() {
        mCancelBtn.setOnClickListener(this);
        mSureBtn.setOnClickListener(this);
        mIpSetLayout.setOnClickListener(this);
        mModifyWlanBtn.setOnClickListener(this);
        mDeleteWlanBtn.setOnClickListener(this);
    }

    private WifiBean mBean;
    public void setWifiBeanData(WifiBean bean) {
        mBean = bean;
        Map<String, WifiBean> neverUsedWlan = WifiUtil.getInstance(mContext).getNeverUsedPoint();
        String currentUsedSSID = WifiHelper.dealWithSSID(WifiUtil.getInstance(mContext).getSSID());
        if (neverUsedWlan != null && neverUsedWlan.size() > 0 && neverUsedWlan.containsKey(bean.getSsid())){
            mAutoConnectLayout.setVisibility(View.GONE);
            mPhoneHotLayout.setVisibility(View.GONE);
            mStateInfoLayout.setVisibility(View.GONE);
            mConnectSpeedLayout.setVisibility(View.GONE);
            mIpAdressLayout.setVisibility(View.GONE);
            mSubnetMaskLayout.setVisibility(View.GONE);
            mRouterLayout.setVisibility(View.GONE);

            mModifyWlanBtn.setVisibility(View.GONE);
            mDeleteWlanBtn.setVisibility(View.GONE);

            mSignalStrengthLayout.setVisibility(View.VISIBLE);

        } else if (bean.getSsid().equals(currentUsedSSID)) {
            //正在使用的wlan
            mAutoConnectLayout.setVisibility(View.VISIBLE);
            mPhoneHotLayout.setVisibility(View.VISIBLE);
            mStateInfoLayout.setVisibility(View.VISIBLE);
            mConnectSpeedLayout.setVisibility(View.VISIBLE);
            mIpAdressLayout.setVisibility(View.VISIBLE);
            mSubnetMaskLayout.setVisibility(View.VISIBLE);
            mRouterLayout.setVisibility(View.VISIBLE);
            mSignalStrengthLayout.setVisibility(View.VISIBLE);
            mModifyWlanBtn.setVisibility(View.VISIBLE);
            mDeleteWlanBtn.setVisibility(View.VISIBLE);

            if (WifiConnect.isWifiConnect(mContext)) {
                mConnectStateTxt.setText(mContext.getString(R.string.connected));
            }else {
                mConnectStateTxt.setText(mContext.getString(R.string.saved));
            }
            mConnectSpeed.setText(mWifiUtil.getLinkSpeed());
            mIpAdress.setText(WifiHelper.intIpToStringIp(mWifiUtil.getIpAddress()));

        } else if (bean.getType()!= WifiBean.TYPE_WIFICONFIGURATION) {
          //点击不在使用的连接，但是可用的
            mConnectSpeedLayout.setVisibility(View.GONE);
            mIpAdressLayout.setVisibility(View.GONE);
            mSubnetMaskLayout.setVisibility(View.GONE);
            mRouterLayout.setVisibility(View.GONE);
            mStateInfoLayout.setVisibility(View.GONE);
            mConnectSpeedLayout.setVisibility(View.GONE);

            mSignalStrengthLayout.setVisibility(View.VISIBLE);
            mAutoConnectLayout.setVisibility(View.VISIBLE);
            mPhoneHotLayout.setVisibility(View.VISIBLE);

            mModifyWlanBtn.setVisibility(View.VISIBLE);
            mDeleteWlanBtn.setVisibility(View.VISIBLE);
        }

        mWifiSsidTxt.setText(mContext.getString(R.string.ssid_wlan_detail, bean.getSsid()));
        mSignValueTxt.setText(mWifiUtil.getRssi(bean.getLevel()).getName(mContext));
        mSaveValueTxt.setText(WifiHelper.changeName(mContext, WifiHelper.getKeyMgmt(bean.getCapabilities())));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sure) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);


        } else if (id == R.id.cancel) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

        } else if (id == R.id.ip_set_layout) {
            IpChooseDialogView ipChooseDialogView = new IpChooseDialogView(mContext);
            ipChooseDialogView.setChooseWhichIpSetListener(this);
            ipChooseDialogView.show();
        } else if (id == R.id.modify_wlan) {
            ModifyWlanDialogView dialogView = new ModifyWlanDialogView(mContext, mBean.getSsid());
            dialogView.show();

        }else if (id == R.id.delete_wlan) {
            DeleteWlanDialogView deleteWlanDialogView = new DeleteWlanDialogView(mContext);
            deleteWlanDialogView.show();

            deleteWlanDialogView.setDeleteWlanListeners(new DeleteWlanDialogView.IDeleteWlanListener() {
                @Override
                public void deleteWlan() {
                    if (mBean != null) {
                        mWifiManager.removeNetwork(mBean.getNetworkId());
                        mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
                        mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

                    }
                }

                @Override
                public void closeView() {
                    mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
                    mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

                }
            });
        }
    }

    @Override
    public void showChangeIpSet(String chooseWhich) {
        if (chooseWhich.equals("DHCP")) {
            mSetText.setText(mContext.getString(R.string.dhcp_tips));
            mStaticSetView.setVisibility(View.GONE);
        } else if (chooseWhich.equals("STATIC")) {
            mSetText.setText(mContext.getString(R.string.static_tips));
            mStaticSetView.setVisibility(View.VISIBLE);
        }
    }
}
