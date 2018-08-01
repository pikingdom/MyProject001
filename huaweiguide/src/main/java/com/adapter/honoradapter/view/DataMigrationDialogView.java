package com.adapter.honoradapter.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.hwstartupguide.R;


public class DataMigrationDialogView extends RelativeLayout {
    public DataMigrationDialogView(Context context) {
        super(context);
        initView();
    }

    public DataMigrationDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DataMigrationDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View mRoot = inflate(getContext(), R.layout.view_data_migration_dialog,null);
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
            if (i == R.id.dialog_submit) {
                if (onOKClickListener != null) {
                    onOKClickListener.onClick(DataMigrationDialogView.this);
                }
                DataMigrationDialogView.this.setVisibility(View.GONE);
            } else if (i == R.id.dialog_cancle) {
                DataMigrationDialogView.this.setVisibility(View.GONE);
            }
        }
    };
    private View.OnClickListener onOKClickListener;

    public void setOnOKClickListener(OnClickListener onOKClickListener) {
        this.onOKClickListener = onOKClickListener;
    }
}
