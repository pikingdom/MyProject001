package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.gionee.setupwizard.R;

/**
 * Created by xuqunxing on 2017/8/16.
 */
public class ToLoginAccountDialogView extends RelativeLayout{

    public ToLoginAccountDialogView(Context context) {
        super(context);
        initView();
    }

    public ToLoginAccountDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ToLoginAccountDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_view_to_accoutn_dialog,this);
    }

    public void showDialog() {
        AccountDialog accountDialog = new AccountDialog(getContext(),getContext().getResources().getString(R.string.account_dialog_title_accout_title),
                getContext().getResources().getString(R.string.gn_sw_string_account_dialog_tip),
                getContext().getResources().getString(R.string.account_dialog_title_accout_cancle),
                getContext().getResources().getString(R.string.account_dialog_title_accout_submit));
        accountDialog.show();
        accountDialog.setCanceledOnTouchOutside(false);
        accountDialog.setOnGnClickListener(new AccountDialog.OnGnClickListener() {
            @Override
            public void onSubmitClick(View v) {
                if(onGnClickListener != null){
                    onGnClickListener.onSubmitClick(v);
                }
            }

            @Override
            public void onCancleClick(View v) {
                if(onGnClickListener != null){
                    onGnClickListener.onCancleClick(v);
                }
            }
        });
    }

    private AccountDialog.OnGnClickListener onGnClickListener;

    public void setOnGnClickListener(AccountDialog.OnGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface OnGnClickListener {
        public void onSubmitClick(View v);
        public void onCancleClick(View v);
    }
}
