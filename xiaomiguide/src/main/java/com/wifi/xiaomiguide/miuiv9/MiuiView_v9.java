package com.wifi.xiaomiguide.miuiv9;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**
 * Created by llf on 2017/8/7.
 */

public class MiuiView_v9 extends RelativeLayout implements View.OnClickListener{
    private ChangeViewInterface mChangeViewInterface;
    private Context mContext;
    private ImageView mGoNext;
    private ImageView mLogoImg;
    private Animation mAnimation;
    private RelativeLayout mLayout;

    public MiuiView_v9(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.mChangeViewInterface = changeViewInterface1;
        mContext = context;

        findViewsById();
        setViews();
        setListeners();
    }

    private void findViewsById() {
        View.inflate(getContext(), R.layout.miui_view_v9,this);
        mGoNext = (ImageView) findViewById(R.id.go_on);
        mLogoImg = (ImageView) findViewById(R.id.img);
        mLayout = (RelativeLayout) findViewById(R.id.layout);

    }

    private void setViews() {
        Animation hideAnim = AnimationUtils.loadAnimation(mContext, R.anim.miui_animation_view);
        mLayout.startAnimation(hideAnim);
    }

    private void setListeners() {
        mGoNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_on) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW, 0);
        }
    }
}
