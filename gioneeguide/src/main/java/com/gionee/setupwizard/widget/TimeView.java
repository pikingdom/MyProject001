package com.gionee.setupwizard.widget;

/**
 * 作者：xiaomao on 2017/7/14.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeView extends LinearLayout implements IUpdateTime {
    private static final int ANIM_ALPHA_DURATION = 750;
    private Context mContext;
    private TextView mDataView;
    private SimpleDateFormat mDateFormat;
    private LinearLayout mTimeContiner;
    private TextView mTimeView1;
    private TextView mTimeView2;

    public TimeView(Context context) {
        this(context, null);
    }

    public TimeView(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        RelativeLayout layout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.gn_logo_time_view, null);
        this.mTimeContiner = (LinearLayout) layout.findViewById(R.id.gn_sw_layout_time_container);
        this.mDataView = (TextView) layout.findViewById(R.id.gn_sw_layout_wifi_date_view);
        this.mTimeView1 = (TextView) layout.findViewById(R.id.gn_sw_layout_wifi_time_viewH);
        this.mTimeView2 = (TextView) layout.findViewById(R.id.gn_sw_layout_wifi_time_viewM);
        this.mTimeView2.setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), "fonts/Roboto-Thin.ttf"));
        addView(layout);
    }

    public void updateTime(final long time, boolean anim) {
        if (this.mDateFormat == null) {
            this.mDateFormat = new SimpleDateFormat(this.mContext.getString(R.string.gn_sw_string_date_format));
        }
        final String timeText = this.mDateFormat.format(new Date(time));
        if (anim) {
            RotateAnimation rotateAnimation = new RotateAnimation(((float) this.mTimeContiner.getWidth()) / 2.0f, ((float) this.mTimeContiner.getHeight()) / 2.0f);
            rotateAnimation.setInterpolatedTimeListener(new com.gionee.setupwizard.widget.RotateAnimation.InterpolatedTimeListener() {
                public void interpolatedTime(float interpolatedTime) {
                    if (interpolatedTime > 0.5f) {
                        TimeView.this.mTimeView1.setText(timeText.substring(0, 3));
                        TimeView.this.mTimeView2.setText(timeText.substring(3, 5));
                    }
                    if (interpolatedTime >= 1.0f) {
                        TimeView.this.mTimeContiner.setAnimation(null);
                    }
                }
            });
            this.mTimeContiner.startAnimation(rotateAnimation);
        } else {
            this.mTimeView1.setText(timeText.substring(0, 3));
            this.mTimeView2.setText(timeText.substring(3, 5));
        }
        if (anim) {
            AnimatorSet set = new AnimatorSet();
            ObjectAnimator animatorOut = ObjectAnimator.ofFloat(this.mDataView, "alpha", new float[]{1.0f, 0.0f});
            animatorOut.setDuration(750);
            ObjectAnimator animatorIn = ObjectAnimator.ofFloat(this.mDataView, "alpha", new float[]{0.0f, 1.0f});
            animatorIn.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    TimeView.this.updateSystemTime(time);
                }
            });
            animatorIn.setDuration(750);
            set.play(animatorOut).before(animatorIn);
            set.start();
            animatorOut.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    TimeView.this.mDataView.setText(timeText.substring(5, timeText.length()));
                }
            });
            return;
        }
        this.mDataView.setText(timeText.substring(5, timeText.length()));
    }

    private void updateSystemTime(long time) {
        if (time / 1000 < 2147483647L) {
            SystemClock.setCurrentTimeMillis(time);
        }
    }
}
