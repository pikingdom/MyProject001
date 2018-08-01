package com.zteguidedemo.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.util.ScreenUtil;

/**
 * Created by xuqunxing on 2017/7/17.
 */

public class ZTEGuideWelcomView extends BaseRelativilayout implements View.OnClickListener{

    private TextView nextTv;
    private TextView usageTv;
    private ImageView welcomBg;
    private TextView languagePopTv;
    private PopupWindow popView;
    private RelativeLayout languagePopRl;
    private int popWidth = 0;

    public ZTEGuideWelcomView(Context context) {
        this(context,null);
    }

    public ZTEGuideWelcomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZTEGuideWelcomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_welcom, this);
        TextView previousTv = (TextView) findViewById(R.id.view_previous);
        View bottomLine = (View) findViewById(R.id.bottom_line);
        previousTv.setVisibility(GONE);
        bottomLine.setVisibility(GONE);

        nextTv = (TextView) findViewById(R.id.view_next);
        usageTv = (TextView) findViewById(R.id.welcom_usage);
        welcomBg = (ImageView) findViewById(R.id.welcom_bg);
        languagePopTv = (TextView) findViewById(R.id.view_welcom_language_tv);
        languagePopRl = (RelativeLayout) findViewById(R.id.view_welcom_language_pop_rl);

        welcomBg.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                welcomBg.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = welcomBg.getHeight();
                RelativeLayout.LayoutParams layoutParams = (LayoutParams) usageTv.getLayoutParams();
                layoutParams.topMargin = height *  1/2 + ScreenUtil.dip2px(getContext(),20) ;

                int languagePopRlWidth = languagePopRl.getWidth();
                if(languagePopRlWidth > 0){
                    popWidth = languagePopRlWidth - ScreenUtil.dip2px(getContext(),20);
                }
            }
        });
        nextTv.setOnClickListener(this);
        languagePopRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextTv){
            if(onZteClickListener != null){
                onZteClickListener.OnNext(this);
            }
        }else if(view == languagePopRl){
            View view2 = View.inflate(getContext(),R.layout.view_welcom_language_pop, null);
            final TextView chineseTv = (TextView) view2.findViewById(R.id.popwindow_chinese);
            final TextView englishTv = (TextView) view2.findViewById(R.id.popwindow_english);

            popView = new PopupWindow(view2,popWidth, ScreenUtil.dip2px(getContext(),101));
            popView.setFocusable(true);
            popView.setOutsideTouchable(true);
            popView.setBackgroundDrawable(new BitmapDrawable());
            int[] location = new int[2];
            languagePopRl.getLocationOnScreen(location);
            Log.e("====","====111:"+(location[1] - popView.getHeight())+"====222:"+(location[1] + popView.getHeight()));
         //   popView.showAtLocation(languagePopRl,Gravity.NO_GRAVITY, location[0], (location[1] - popView.getHeight()));
            popView.showAtLocation(languagePopRl, Gravity.NO_GRAVITY, (location[0] + ScreenUtil.dip2px(getContext(),10)), (location[1] - popView.getHeight()));
            chineseTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    languagePopTv.setText(getContext().getResources().getString(R.string.select_language_chinese));
                    popView.dismiss();
                }
            });
            englishTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    languagePopTv.setText(getContext().getResources().getString(R.string.select_language_englise));
                    popView.dismiss();
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(popView != null){
            if(popView.isShowing()){
                popView.dismiss();
            }
        }
    }


}
