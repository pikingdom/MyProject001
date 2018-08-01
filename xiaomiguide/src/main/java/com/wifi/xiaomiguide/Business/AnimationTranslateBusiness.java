package com.wifi.xiaomiguide.Business;

import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

/**
 * Created by llf on 2017/8/16.
 */

public class AnimationTranslateBusiness {

    private static Animation mAnimationTranslate;
    public static final float FROM_RIGHT_TO_LEFT = 1.0f;
    public static final float FROM_LEFT_TO_RIGHT = -1.0f;

    public static void setAnimationTranslate(Context context, View view, float direction) {

        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, direction,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF,0.0f);

        translateAnimation.setDuration(300);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(translateAnimation);
        view.startAnimation(animationSet);
    }

//    private static AlphaAnimation disappearAnimation;
//    public static void setGoneAnimation(Context context, View view) {
//       // disappearAnimation = new AlphaAnimation(1, 0);
//       // disappearAnimation.setDuration(500);
//       // view.startAnimation(disappearAnimation);
//    }
}
