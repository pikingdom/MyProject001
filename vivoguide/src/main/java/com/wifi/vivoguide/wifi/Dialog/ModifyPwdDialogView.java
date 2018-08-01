package com.wifi.vivoguide.wifi.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.wifi.vivoguide.R;
import com.wifi.vivoguide.util.InputMethodsManager;

/**
 * @Description: 输入wifi连接信息对话框
 * @author linyt
 * @date 2013-3-7 下午5:31:45
 *
 */
public class ModifyPwdDialogView extends Dialog implements OnClickListener {

    private TextView mSsidTitle;
    private EditText mInputEdit;
    private TextView mSaveBtn;
    private TextView mCancleBtn;

    private String mSsid;
    private Context mActivity;

    private String mCurrentInputValue;



    @SuppressLint("WifiManagerLeak")
    public ModifyPwdDialogView(Context activity, String ssid) {
        super(activity, R.style.Dialog);
        mActivity = activity;
        mSsid = ssid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_modify_pwd_dialog_view);
        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        findViewsById();
        setViews();
        setListeners();
    }

    private CheckBox mWifiShowpasswdCheckbox;
    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.ssid_title);
        mInputEdit = (EditText) findViewById(R.id.wifi_passwd);
        mWifiShowpasswdCheckbox = (CheckBox) findViewById(R.id.wifi_showpasswd_checkbox);

        mSaveBtn = (TextView) findViewById(R.id.wifi_button_save);
        mCancleBtn = (TextView) findViewById(R.id.wifi_button_cancle);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
    }

    private void setViews() {
        mSsidTitle.setText(mSsid);
        mInputEdit.setFocusable(true);
        mInputEdit.setFocusableInTouchMode(true);
        mInputEdit.requestFocus();

        mWifiShowpasswdCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mInputEdit.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        });
    }

    public void showKeyBoard() {
        if (mInputEdit != null)
            InputMethodsManager.showKeyboard(mInputEdit);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wifi_button_cancle) {
            dismissDialog();

        } else if (id == R.id.wifi_button_save) {
            if (invalidIp()) {
                //notifitionInfo();
                dismiss();
            }
        }
    }

//    private void notifitionInfo() {
//        Intent intent = new Intent();
//        intent.putExtra("CURRENT_INPUT_TYPE",mCurrentType);
//        intent.putExtra("CURRENT_INPUT_VALUE",mCurrentInputValue);
//
//        SystemEvent.fireEvent(SystemEventConst.EVENT_INPUT_STATIC_SET_VALUE, intent);
//    }

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
//        mCurrentInputValue = mInputEdit.getText().toString().trim();
//        if (!WifiHelper.isValidIp(mCurrentInputValue)) {
//            String tips1 = null;
//            String tips2 = null;
//            tips1 = mActivity.getString(R.string.wifi_ip_faild);
//            switch (mCurrentType) {
//                case STATIC_IP:
//                    tips2 = mActivity.getString(R.string.wifi_ip_address);
//                    break;
//                case STATIC_GATEWAY:
//                    tips2 = mActivity.getString(R.string.wifi_gateway_address);
//                    break;
//                case STATIC_NETMASK:
//                    tips2 = mActivity.getString(R.string.wifi_prefix_length);
//                    break;
//                case STATIC_DNS1:
//                    tips2 = mActivity.getString(R.string.wifi_dns1_address);
//                    break;
//                case STATIC_DNS2:
//                    tips2 = mActivity.getString(R.string.wifi_dns2_address);
//                    break;
//            }
//
//            MessageUtils.makeShortToast(mActivity, String.format(tips1, tips2));
//            return false;
//
//        }

        return true;
    }
}
