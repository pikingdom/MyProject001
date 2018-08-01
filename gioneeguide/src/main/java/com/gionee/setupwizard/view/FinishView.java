package com.gionee.setupwizard.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.gionee.setupwizard.R;

/**
 * 完成协议界面
 * 作者：xiaomao on 2017/7/19.
 */

public class FinishView extends LinearLayout {
    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    public FinishView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_finish,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        findViewById(R.id.gn_sw_layout_complete_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {//完成
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.OVER);
            }
        });
    }
}
