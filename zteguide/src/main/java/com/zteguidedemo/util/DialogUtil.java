package com.zteguidedemo.util;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.zteguidedemo.R;

/**
 * dilaog工具类
 *
 * @author youy
 */

public class DialogUtil {

    public static Dialog LockGMyPhoneDialog(final Context context,final View.OnClickListener cancleListener, final View.OnClickListener onClickListener) {

        Dialog dialog = new Dialog(context, R.style.Dialog_Fullscreen) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.view_my_phone_dialog);
                TextView cancleTv = (TextView) findViewById(R.id.dialog_cancle);
                TextView submitTv = (TextView) findViewById(R.id.dialog_submit);
                cancleTv.setOnClickListener(cancleListener);
                submitTv.setOnClickListener(onClickListener);
            }
        };
        if(context instanceof Application){
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        return dialog;
    }

}
