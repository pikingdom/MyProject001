package com.zteguidedemo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.zteguidedemo.listener.OnZteClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuqunxing on 2017/7/19.
 */

public class MainView extends RelativeLayout implements OnZteClickListener,IGuideView{

    private ZTEGuideWelcomView zteGuideWelcomView;
    private ZTEInsertSimView zteInsertSimView;
    private MyPhoneView myPhoneView;
    private List<View> viewList = new ArrayList<>();
    private AccountSettingsView accountSettingsView;
    private ColorChoiceSettingsView colorChoiceSettingsView;
    private IGuideCallback iGuideCallback;

    public MainView(Context context) {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MainView(Context context, IGuideCallback iGuideCallback) {
        super(context);
        this.iGuideCallback = iGuideCallback;
        initView();
    }

    private void initView(){
        zteGuideWelcomView = new ZTEGuideWelcomView(getContext());
        zteGuideWelcomView.setOnZteClickListener(this);

        zteInsertSimView = new ZTEInsertSimView(getContext());
        zteInsertSimView.setOnZteClickListener(this);
        zteInsertSimView.setVisibility(View.GONE);

        myPhoneView = new MyPhoneView(getContext());
        myPhoneView.setOnZteClickListener(this);
        myPhoneView.setVisibility(View.GONE);

        accountSettingsView = new AccountSettingsView(getContext());
        accountSettingsView.setOnZteClickListener(this);
        accountSettingsView.setVisibility(View.GONE);

        colorChoiceSettingsView = new ColorChoiceSettingsView(getContext());
        colorChoiceSettingsView.setOnZteClickListener(this);
        colorChoiceSettingsView.setVisibility(View.GONE);

        addView(zteGuideWelcomView);
        addView(zteInsertSimView);
        addView(myPhoneView);
        addView(accountSettingsView);
        addView(colorChoiceSettingsView);

        viewList.add(zteGuideWelcomView);
        viewList.add(zteInsertSimView);
        viewList.add(myPhoneView);
        viewList.add(accountSettingsView);
        viewList.add(colorChoiceSettingsView);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(accountSettingsView != null){
            accountSettingsView.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void OnNext(View view) {
        if(view == zteGuideWelcomView){
            setViewVisible(0);
        }else if(view == zteInsertSimView){
            setViewVisible(1);
        }else if(view == myPhoneView){
            setViewVisible(2);
        }else if(view == accountSettingsView){
            setViewVisible(3);
        }else if(view == colorChoiceSettingsView){
            setViewVisible(4);
        }
    }

    @Override
    public void OnSkip(View view) {
        if(view == zteGuideWelcomView){
            setViewVisible(0);
        }else if(view == zteInsertSimView){
            setViewVisible(1);
        }else if(view == myPhoneView){
            setViewVisible(2);
        }else if(view == accountSettingsView){
            setViewVisible(3);
        }else if(view == colorChoiceSettingsView){
            setViewVisible(4);
        }
    }

    @Override
    public void OnPrevious(View view) {
        if(view == zteGuideWelcomView){
            setViewVisiblePrevious(0);
        }else if(view == zteInsertSimView){
            setViewVisiblePrevious(1);
        }else if(view == myPhoneView){
            setViewVisiblePrevious(2);
        }else if(view == accountSettingsView){
            setViewVisiblePrevious(3);
        }else if(view == colorChoiceSettingsView){
            setViewVisiblePrevious(4);
        }
    }

    @Override
    public void OnDone(View view) {
         if(iGuideCallback != null){
             iGuideCallback.onComplete();
         }
    }

    private void setViewVisible(int Pos){
        if(viewList.size() > 0){
            for(int i = 0;i<viewList.size();i++){
                View view = viewList.get(i);
                if(i == Pos + 1){
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }
    private void setViewVisiblePrevious(int Pos){
        if(viewList.size() > 0){
            for(int i = 0;i<viewList.size();i++){
                View view = viewList.get(i);
                if(i == Pos - 1){
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            viewList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
