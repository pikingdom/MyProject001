package com.adapter.honoradapter.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.hwstartupguide.R;


public class FingerprintBeforeStartDialogView extends RelativeLayout {
    public FingerprintBeforeStartDialogView(Context context) {
        super(context);
        initView();
    }

    public FingerprintBeforeStartDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FingerprintBeforeStartDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View mRoot = inflate(getContext(), R.layout.view_fingerprint_beforestart_dialog,null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        addView(mRoot,lp);

        findViewById(R.id.dialog_cancle).setOnClickListener(clickListener);
        findViewById(R.id.dialog_submit).setOnClickListener(clickListener);
        setBackgroundColor(Color.parseColor("#77444444"));
    }
    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.dialog_cancle) {
                if (onOKClickListener != null) {
                    onOKClickListener.onClick(FingerprintBeforeStartDialogView.this);
                }
                FingerprintBeforeStartDialogView.this.setVisibility(View.GONE);
            } else if (i == R.id.dialog_submit) {
                FingerprintBeforeStartDialogView.this.setVisibility(View.GONE);
            }
        }
    };
    private OnClickListener onOKClickListener;

    public void setOnOKClickListener(OnClickListener onOKClickListener) {
        this.onOKClickListener = onOKClickListener;
    }
}
