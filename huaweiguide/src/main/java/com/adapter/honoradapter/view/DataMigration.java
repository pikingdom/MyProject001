package com.adapter.honoradapter.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.honoradapter.util.UIUtil;
import com.huawei.hwstartupguide.ChangeViewInterface;
import com.huawei.hwstartupguide.R;
import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

/**
 * Created by Administrator on 2018/4/11.
 */

public class DataMigration extends FrameLayout implements View.OnClickListener,NavigationVisibilityChange {
    private TextView mPrevBtn;
    private TextView mNextBtn;
    private RelativeLayout buttonBar;
    private ChangeViewInterface changeViewInterface;

    private View line1;
    private View line2;
    private View line3;

    private DataMigrationDialogView verifyDialogView;

    public DataMigration(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        this.changeViewInterface = changeViewInterface;
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.page_data_migration,this);
        setLogoViewHeight();
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        verifyDialogView = (DataMigrationDialogView) findViewById(R.id.verify_dialog_view);
        verifyDialogView.setOnOKClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.CLOUD_SERVICE_VIEW);
                }
            }
        });
        initButtonBar();
    }

    private void setLogoViewHeight() {
        View logoView = findViewById(R.id.logo_view);
        View simLogo = findViewById(R.id.logo);
        if (logoView != null && simLogo != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int logoViewHeight;
            if (!UIUtil.isPadScreenDevice(getContext())) {
                logoViewHeight = (displayMetrics.widthPixels * 4) / 5;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 4) / 25;
            } else if (UIUtil.isLandScreen(getContext())) {
                logoViewHeight = (displayMetrics.widthPixels * 268) / 1000;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 13) / 100;
            } else {
                logoViewHeight = (displayMetrics.widthPixels * 62) / 100;
                logoView.getLayoutParams().height = logoViewHeight;
                ((LinearLayout.LayoutParams) simLogo.getLayoutParams()).topMargin = (logoViewHeight * 38) / 100;
            }
        }
    }

    protected void initButtonBar() {
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);
        this.mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        this.mPrevBtn.setVisibility(View.VISIBLE);
        this.mPrevBtn.setOnClickListener(this);
        this.mPrevBtn.setText(R.string.back_format);
        this.mNextBtn = (TextView) findViewById(R.id.next_btn);
        this.mNextBtn.setVisibility(View.VISIBLE);
        this.mNextBtn.setText(R.string.data_migration_next);
        this.mNextBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == line1){
            //从华为迁移
//            Intent intent = new Intent("com.huawei.remotecontrol.intent.action.ACTIVATION");
//            intent.setPackage("com.huawei.hidisk");
//            Context context = getContext();
//            if(context != null){
//                ((Activity)context).startActivityForResult(intent, 6);
//                ((Activity)context).overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_from_alpah);
//            }
        } else if(v == line2){
            //从其他安卓设备
        } else if(v == line3){
            //从iOS设备
        } else if(v == mPrevBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
            }
        } else if(v == mNextBtn){
            if(verifyDialogView != null){
                verifyDialogView.setVisibility(View.VISIBLE);
            }
        }
    }


    public void onBackPressed() {
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
        }
    }

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) buttonBar.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
        }
        buttonBar.setLayoutParams(lp);
        requestLayout();
    }
}
