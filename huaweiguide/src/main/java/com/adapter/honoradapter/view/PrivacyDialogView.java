package com.adapter.honoradapter.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.hwstartupguide.R;

/**
 * 权限设置对话框改成View
 * 作者：xiaomao on 2017/8/7.
 */

public class PrivacyDialogView extends RelativeLayout {
    public PrivacyDialogView(Context context) {
        super(context);
        initView();
    }

    public PrivacyDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PrivacyDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View mRoot = inflate(getContext(), R.layout.view_privacy_dialog,null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        addView(mRoot,lp);

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
        setBackgroundColor(Color.parseColor("#77444444"));
    }
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.dialog_submit) {
                if (onOKClickListener != null) {
                    onOKClickListener.onOKClick(v);
                }
                PrivacyDialogView.this.setVisibility(View.GONE);
            } else if (i == R.id.dialog_cancle) {
                PrivacyDialogView.this.setVisibility(View.GONE);
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
