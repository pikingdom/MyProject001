package com.adapter.honoradapter.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huawei.hwstartupguide.R;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public class PrivacyDialog extends Dialog {

    private Context mContext;
    public PrivacyDialog(Context context) {
        this(context, R.style.Dialog_Fullscreen);
    }

    public PrivacyDialog(Context context, int themeResId) {
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
        setContentView(R.layout.view_privacy_dialog);

        initViews();
        initValues();
    }

    private void initViews() {
        findViewById(R.id.dialog_cancle).setOnClickListener(clickListener);
        findViewById(R.id.dialog_submit).setOnClickListener(clickListener);
        TextView privacy_tip = (TextView) findViewById(R.id.privacy_tip);
        String tipStr = getContext().getResources().getString(R.string.device_agreement_tip_new);
        String agreeStr = getContext().getResources().getString(R.string.device_permitted_agreement);
        privacy_tip.setText(String.format(tipStr,agreeStr));
        String tipColor = privacy_tip.getText().toString();
        int startleng = tipStr.length() - 3;
        int endleng = agreeStr.length() + startleng ;
        ColorStateList redColors = ColorStateList.valueOf(getContext().getResources().getColor(R.color.light_blue));
        SpannableString styledText = new SpannableString(tipColor);
        styledText.setSpan(new TextAppearanceSpan(null, Typeface.NORMAL, 0, redColors, null), startleng, endleng, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        privacy_tip.setText(styledText);
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
            // TODO Auto-generated method stub
            int i = v.getId();
            if (i == R.id.dialog_submit) {
                if (onOKClickListener != null) {
                    onOKClickListener.onOKClick(v);
                }
                dismiss();

            } else if (i == R.id.dialog_cancle) {
                dismiss();

            } else {
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
