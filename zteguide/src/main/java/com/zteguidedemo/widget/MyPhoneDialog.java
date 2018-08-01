package com.zteguidedemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zteguidedemo.R;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public class MyPhoneDialog extends Dialog {

    private Context mContext;
    public MyPhoneDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public MyPhoneDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext =context;
        init();
    }

    private void init() {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_my_phone_dialog);

        initViews();
        initValues();
    }

    private void initViews() {
        findViewById(R.id.dialog_cancle).setOnClickListener(clickListener);
        findViewById(R.id.dialog_submit).setOnClickListener(clickListener);

    }

    private void initValues() {
        // 不能写在init()中
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;//让dialog的宽占满屏幕的宽
        lp.gravity = Gravity.BOTTOM;//出现在底部
        window.setAttributes(lp);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.dialog_submit){
                if (onOKClickListener != null) {
                    onOKClickListener.onOKClick(v);
                }
                dismiss();
            }else if(i == R.id.dialog_cancle){
                dismiss();
            }
        }

    };

    private OnOKClickListener onOKClickListener;

    public interface OnOKClickListener {
        public void onOKClick(View v);
    }

    public void setOnOKClickListener(OnOKClickListener onOKClickListener) {
        this.onOKClickListener = onOKClickListener;
    }
}
