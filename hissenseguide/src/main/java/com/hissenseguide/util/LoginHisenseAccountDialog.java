package com.hissenseguide.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.hissenseguide.R;


/**
 * Created by xuqunxing on 2017/7/19.
 */
public class LoginHisenseAccountDialog extends Dialog {

    private Context mContext;
    private TextView titleTv;
    private TextView wlanTv;
    private TextView cancleTv;
    private TextView netDataTv;

    public LoginHisenseAccountDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public LoginHisenseAccountDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext =context;
        init();
    }

//    public LoginHisenseAccountDialog(Context context, String title1, String desc1, String cancle1, String submit1) {
//        this(context, R.style.Dialog_Fullscreen);
//        this.title = title1;
//        this.desc = desc1;
//        this.cancle = cancle1;
//        this.submit = submit1;
//        this.mContext =context;
//        init();
//    }

    private void init() {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_login_hisense_dialog);

        initViews();
        initValues();
    }

    private void initViews() {
        titleTv = (TextView) findViewById(R.id.dialog_title);
        wlanTv = (TextView) findViewById(R.id.dialog_wlan);
        cancleTv = (TextView) findViewById(R.id.dialog_cancle);
        netDataTv = (TextView) findViewById(R.id.dialog_net_data);
        wlanTv.setOnClickListener(clickListener);
        cancleTv.setOnClickListener(clickListener);
        netDataTv.setOnClickListener(clickListener);
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
            int id = v.getId();
            if(id == R.id.dialog_net_data){
                if (onGnClickListener != null) {
                    onGnClickListener.onNetDataCLick(v);
                }
                dismiss();
            }else  if(id == R.id.dialog_cancle){
                dismiss();
                if (onGnClickListener != null) {
                    onGnClickListener.onCancleClick(v);
                }
            }else if(id == R.id.dialog_wlan){
                dismiss();
                if (onGnClickListener != null) {
                    onGnClickListener.onWLANCLick(v);
                }
            }
        }
    };

    private OnGnClickListener onGnClickListener;

    public void setOnGnClickListener(OnGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface OnGnClickListener {
        public void onWLANCLick(View v);
        public void onNetDataCLick(View v);
        public void onCancleClick(View v);
    }

}
