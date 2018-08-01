package com.wifi.xiaomiguide.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;
import com.wifi.xiaomiguide.Util.InputMethodsManager;

/**
 * @Description: 修改网络
 * @author llf
 * @date 2017-08-10
 *
 */
public class ModifyWlanDialogView extends Dialog implements OnClickListener {

    private Context mContext;
    private EditText mWifiPasswd;
    private TextView  mSsidTitle;

    private TextView mCancleBtn;
    private TextView mSaveBtn;
    private ImageView mShwoPasswordBtn;

    private String mSsid;


    @SuppressLint("WifiManagerLeak")
    public ModifyWlanDialogView(Context activity, String ssid) {
        super(activity, R.style.Dialog);
        mContext = activity;
        mSsid = ssid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_wlan_dialog_view);

        //弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置为点击对话框之外的区域对话框不消失
        this.setCanceledOnTouchOutside(false);

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mSsidTitle = (TextView) findViewById(R.id.ssid_title);
        mWifiPasswd = (EditText) findViewById(R.id.wifi_passwd);
        mCancleBtn = (TextView) findViewById(R.id.wifi_button_cancle);
        mSaveBtn = (TextView) findViewById(R.id.wifi_button_save);
        mShwoPasswordBtn = (ImageView) findViewById(R.id.show_pwd);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mShwoPasswordBtn.setOnClickListener(this);
    }

    private void setViews() {

        mSsidTitle.setText(mSsid);
        mWifiPasswd.setFocusable(true);
        mWifiPasswd.setFocusableInTouchMode(true);
        mWifiPasswd.requestFocus();

        showKeyBoard();
    }

    public void showKeyBoard() {
        if (mWifiPasswd != null) {
            InputMethodsManager.showKeyboard(mWifiPasswd);
        }
    }

    private int recordClickCount = 0;
    private boolean isChecked = false;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.show_pwd) {
            recordClickCount++;
            if (recordClickCount % 2 == 0) {
                isChecked = false;
                mShwoPasswordBtn.setBackgroundResource(0);
                mShwoPasswordBtn.setBackgroundResource(R.drawable.show_pwd_n);
            } else {
                isChecked = true;
                mShwoPasswordBtn.setBackgroundResource(0);
                mShwoPasswordBtn.setBackgroundResource(R.drawable.show_pwd_p);
            }
            mWifiPasswd.setInputType(InputType.TYPE_CLASS_TEXT | (isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        } else if (id == R.id.wifi_button_cancle) {
            dismissDialog();

        } else if (id == R.id.wifi_button_save) {
            dismissDialog();
        }
    }

    private void dismissDialog() {
        InputMethodsManager.hideKeyboard(mWifiPasswd);
        dismiss();
    }
}
