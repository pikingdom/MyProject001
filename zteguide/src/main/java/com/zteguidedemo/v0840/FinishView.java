package com.zteguidedemo.v0840;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.zteguidedemo.R;

/**
 * 完成视图
 * 作者：xiaomao on 2017/8/21.
 */

public class FinishView extends LinearLayout {
    private ZTEGuideMainView zteGuideMainView;
    public FinishView(Context context,ZTEGuideMainView zteGuideMainView) {
        super(context);
        this.zteGuideMainView = zteGuideMainView;
        initView();
    }

    private void initView(){
        addView(LayoutInflater.from(getContext()).inflate(R.layout.zte_finish_view,null),
                new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        findViewById(R.id.zte_finish_btn_layout).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//点击完成
                zteGuideMainView.setCurrentlyShowView(ChangeViewInterface.GUIDE_OVER);
            }
        });
    }
}
