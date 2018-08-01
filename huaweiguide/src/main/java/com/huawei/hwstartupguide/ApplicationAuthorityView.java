package com.huawei.hwstartupguide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

/**
 * 应用权限开关说明
 * Created by llf on 2017/7/20.
 */

public class ApplicationAuthorityView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange{

    private ChangeViewInterface mChangeViewInterface;
    private Context mContent;

    private TextView mNextStepBtn;

    public ApplicationAuthorityView(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        mContent = context;
        mChangeViewInterface = changeViewInterface;
        init();
    }

    public ApplicationAuthorityView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        LayoutInflater.from(mContent).inflate(R.layout.hauwei_app_authority_view, this);
        findViewsById();
        setListeners();
    }

    private void findViewsById() {
        mNextStepBtn = (TextView) findViewById(R.id.nextstep_btn);
    }

    private void setListeners(){
        mNextStepBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.nextstep_btn) {
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.SERVICE_ACCESS_EXPLAIN_VIEW);
        }
    }

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mNextStepBtn.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
        }
        mNextStepBtn.setLayoutParams(lp);
        requestLayout();
    }
}
