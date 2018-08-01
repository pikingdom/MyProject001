package com.wifi.xiaomiguide.miuiv9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**
 * Created by xuqunxing on 2017/8/8.
 */

public class SimCarView_v9 extends RelativeLayout implements View.OnClickListener {

    private TextView backTv;
    private TextView centerTv;
    private TextView nextTv;
    private ChangeViewInterface mChangeViewInterface;

    public SimCarView_v9(Context context, ChangeViewInterface changeViewInterface) {
        super(context);
        mChangeViewInterface = changeViewInterface;
        initView();
    }

    public SimCarView_v9(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public SimCarView_v9(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initView() {
        View.inflate(getContext(), R.layout.sim_card_view_v9, this);

        backTv = (TextView) findViewById(R.id.bottom_back);
        centerTv = (TextView) findViewById(R.id.bottom_center);
        nextTv = (TextView) findViewById(R.id.bottom_next);

        centerTv.setVisibility(View.VISIBLE);
        backTv.setOnClickListener(this);
        centerTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.USAGE_PRIVACY_VIEW, null);

        }else if(view == centerTv){

        }else if(view == nextTv){
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.OTHER_SETTING_VIEW, null);

        }
    }
}
