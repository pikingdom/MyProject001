package com.huawei.hwstartupguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;


/**
 * Created by Administrator on 2017/7/20.
 */

public class CompletePage extends LinearLayout implements View.OnClickListener,NavigationVisibilityChange {
    ChangeViewInterface changeViewInterface;
    private LayoutInflater mInflater;
    private Button finish;

    public CompletePage(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        this.changeViewInterface = changeViewInterface;
        init();
    }

    private void init() {
        this.mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.complete_page, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(v, lp);
        finish = (Button)findViewById(R.id.start_btn);
        finish .setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        changeViewInterface.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER);
    }

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) finish.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin + (int)(62 * currentDensity + 0.5F);
        }else{
            lp.bottomMargin = 0 + (int)(62 * currentDensity + 0.5F);
        }
        finish.setLayoutParams(lp);
        requestLayout();
    }
}
