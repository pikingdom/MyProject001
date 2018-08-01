package com.wifi.xiaomiguide.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.wifi.xiaomiguide.R;

/**
 * @Description: 删除网络
 * @author llf
 * @date 2017-08-10
 *
 */
public class DeleteWlanDialogView extends Dialog implements OnClickListener {

    private TextView mCancleBtn;
    private TextView mSureBtn;



    @SuppressLint("WifiManagerLeak")
    public DeleteWlanDialogView(Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_wlan_view);

        //设置为点击对话框之外的区域对话框不消失

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        mCancleBtn = (TextView) findViewById(R.id.wifi_button_cancle);
        mSureBtn = (TextView) findViewById(R.id.wifi_button_sure);
    }

    private void setListeners() {
        mCancleBtn.setOnClickListener(this);
        mSureBtn.setOnClickListener(this);
    }

    private void setViews() {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.wifi_button_cancle) {
            if (mIDeleteWlanListener != null) {
                mIDeleteWlanListener.closeView();
            }
        } else if (id == R.id.wifi_button_sure) {
            if (mIDeleteWlanListener != null) {
                mIDeleteWlanListener.deleteWlan();
            }
        }

        dismiss();
    }

    public interface IDeleteWlanListener {
        void deleteWlan();
        void closeView();
    }

    private IDeleteWlanListener mIDeleteWlanListener;
    public void setDeleteWlanListeners(IDeleteWlanListener l) {
        mIDeleteWlanListener = l;
    }
}
