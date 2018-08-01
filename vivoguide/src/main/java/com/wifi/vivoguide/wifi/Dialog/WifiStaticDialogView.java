package com.wifi.vivoguide.wifi.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wifi.vivoguide.Bean.WifiBean;
import com.wifi.vivoguide.Business.WifiConnect;
import com.wifi.vivoguide.Business.WifiHelper;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.InputMethodsManager;
import com.wifi.vivoguide.util.WifiUtil;

/**
 * @Description: 输入wifi连接信息对话框
 * @author linyt
 * @date 2013-3-7 下午5:31:45
 *
 */
public class WifiStaticDialogView extends Dialog implements OnClickListener {

    private TextView mSsidTitle;
    private EditText mInputEdit;
    private TextView mSureBtn;
    private TextView mCancleBtn;

    private String mSsid;
    private String mCapabilities;
    private Context mActivity;
    private String mDefaultValue;

    private String mCurrentInputValue;
    private int mCurrentType = STATIC_IP;
    public static final int STATIC_IP = 0;
    public static final int STATIC_GATEWAY = 1;
    public static final int STATIC_NETMASK = 2;
    public static final int STATIC_DNS1 = 3;
    public static final int STATIC_DNS2 = 4;

    @SuppressLint("WifiManagerLeak")
    public WifiStaticDialogView(Context activity, String ssid, int type, String value) {
        super(activity, R.style.Dialog);
        mActivity = activity;
        mSsid = ssid;
        mCurrentType = type;
        mDefaultValue = value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_static_dialog_view);
        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.static_title);
        mInputEdit = (EditText) findViewById(R.id.static_edit);
        mSureBtn = (TextView) findViewById(R.id.static_sure);
        mCancleBtn = (TextView) findViewById(R.id.static_cancel);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mSureBtn.setOnClickListener(this);
    }

    private void setViews() {
        mSsidTitle.setText(mSsid);
        mInputEdit.setText(mDefaultValue);
        mInputEdit.setFocusable(true);
        mInputEdit.setFocusableInTouchMode(true);
        mInputEdit.requestFocus();
        showKeyBoard();
    }

    public void showKeyBoard() {
        InputMethodsManager.showKeyboard(mInputEdit);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.static_cancel) {
            dismissDialog();

        } else if (id == R.id.static_sure) {
            if (invalidIp()) {
                notifitionInfo();
                dismiss();
            }
        }
    }

    private void notifitionInfo() {
        Intent intent = new Intent();
        intent.putExtra("CURRENT_INPUT_TYPE",mCurrentType);
        intent.putExtra("CURRENT_INPUT_VALUE",mCurrentInputValue);

        SystemEvent.fireEvent(SystemEventConst.EVENT_INPUT_STATIC_SET_VALUE, intent);
    }

    private void dismissDialog() {
        InputMethodsManager.hideKeyboard(mInputEdit);
        dismiss();
    }

    private int transformNetworkPrefix(String len) {
        int prefix_length = 24;
        if (null == len || "".equals(len)) {
            return prefix_length;
        }
        try {
            prefix_length = Integer.valueOf(len);
        } catch (Exception e) {
        }
        return prefix_length;
    }

    /**
     * 验证输入是否有效
     * @Title: invIp
     * @Description: TODO
     * @param @return
     * @return boolean
     * @throws
     */
    private boolean invalidIp() {
        mCurrentInputValue = mInputEdit.getText().toString().trim();
        if (!WifiHelper.isValidIp(mCurrentInputValue)) {
            String tips1 = null;
            String tips2 = null;
            tips1 = mActivity.getString(R.string.wifi_ip_faild);
            switch (mCurrentType) {
                case STATIC_IP:
                    tips2 = mActivity.getString(R.string.wifi_ip_address);
                    break;
                case STATIC_GATEWAY:
                    tips2 = mActivity.getString(R.string.wifi_gateway_address);
                    break;
                case STATIC_NETMASK:
                    tips2 = mActivity.getString(R.string.wifi_prefix_length);
                    break;
                case STATIC_DNS1:
                    tips2 = mActivity.getString(R.string.wifi_dns1_address);
                    break;
                case STATIC_DNS2:
                    tips2 = mActivity.getString(R.string.wifi_dns2_address);
                    break;
            }

            Toast.makeText(mActivity, String.format(tips1, tips2),Toast.LENGTH_SHORT).show();
           // MessageUtils.makeShortToast(mActivity, String.format(tips1, tips2));
            return false;

        }

        return true;
    }

//		if (!WifiHelper.isValidIp(mWifiGateway.getText().toString())) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_gateway_address)));
//			return false;
//		}
//		int len = transformNetworkPrefix(mWifiPrefix.getText().toString());
//		if (len < 0 || len > 32) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_prefix_length)));
//			return false;
//		}
//		if (!WifiHelper.isValidIp(mWifiDns1.getText().toString())) {
//			MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_dns1_address)));
//			return false;
//		}
//		String dns2 = mWifiDns2.getText().toString();
//		if (!"".equals(dns2)) {
//			if (!WifiHelper.isValidIp(dns2)) {
//				MessageUtils.makeShortToast(mActivity, String.format(mActivity.getString(R.string.wifi_ip_faild), mActivity.getString(R.string.wifi_dns2_address)));
//				return false;
//			}
//		}
 //       return true;
 //   }
}
