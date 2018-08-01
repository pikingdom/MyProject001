package com.wifi.vivoguide.wifi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.event.SystemEvent;
import com.wifi.vivoguide.event.SystemEventConst;
import com.wifi.vivoguide.util.LogUtil;
import com.wifi.vivoguide.wifi.Adapter.LanguageAdapter;

/**
 * vivo引导主入口
 * Created by llf on 2017/7/12.
 */

public class VivoGuidEntranceView extends RelativeLayout implements IGuideView, LanguageAdapter.OnRecyclerViewListener
        , WifiSetView.IWifiListViewOnReturn {

    private Activity mContent;
    private ListView mRecycleView;
    private LanguageAdapter mLanguageAdapter;
    private String[] mLanguageArr;
    private WifiSetView mWifiSetView;
    private RelativeLayout mWifiSetLayout;
    private RelativeLayout mVivoGuideLayout;
    private IGuideCallback mListener;


    public VivoGuidEntranceView(Activity context, IGuideCallback listener) {
        super(context);
        mListener = listener;
        init(context);
    }

    public VivoGuidEntranceView(Activity context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    private void init(Activity context) {
        mContent = context;
        View view = LayoutInflater.from(mContent).inflate(R.layout.vivo_guide_entrance_view, null);
        findViewsById(view);
        setViews(view);
        //setListeners();
    }

    private void findViewsById(View v) {

        mVivoGuideLayout = (RelativeLayout)v.findViewById(R.id.language_layout);
        mRecycleView = (ListView) v.findViewById(R.id.recyclerView);
        mWifiSetLayout = (RelativeLayout)v.findViewById(R.id.wifi_view);
        mWifiSetLayout.setVisibility(View.GONE);
    }

    private void setViews(View view) {
        mLanguageArr = mContent.getResources().getStringArray(R.array.language_array);
        mLanguageAdapter = new LanguageAdapter(mContent, mLanguageArr);
        mRecycleView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setOnRecyclerViewListener(this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(view, lp);
    }

//    private void setListeners(){
//        SystemEvent.addListener(SystemEventConst.EVENT_COMPLETE_GUIDE, this);
//    }

    public void hideWifiSetView() {
        showMainView();

        onDestroyEnent();
    }

    public void onDestroyEnent() {
        //SystemEvent.removeListener(SystemEventConst.EVENT_COMPLETE_GUIDE, this);

        if (mWifiSetView != null) {
            //隐藏view时，关闭定时器
            mWifiSetView.onPause();
            //注销广播
            mWifiSetView.onDestroyBordCast();
        }
    }

    private void showWifiSetView() {
        mWifiSetLayout.setVisibility(View.VISIBLE);
        mVivoGuideLayout.setVisibility(View.GONE);
    }

    private void showMainView() {
        if (mWifiSetLayout == null || mVivoGuideLayout == null)
            return;

        mWifiSetLayout.setVisibility(View.GONE);
        mVivoGuideLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(LanguageAdapter.LanguageHolder holder, int position) {
        showWifiSetView();
        mWifiSetLayout.removeAllViews();
        mWifiSetView = new WifiSetView(mContent, position, mListener);
        mWifiSetView.onResume();
        mWifiSetView.setOnBackListeners(this);
        mWifiSetLayout.addView(mWifiSetView.getmWifiSetView());
    }

    @Override
    public void onBackClick() {
        hideWifiSetView();
    }

    @Override
    public void onBackPressed() {
        if (WifiSetView.mIsLongPress) {
            //长按弹出底部选择框时， 按返回键， 关闭当前长按页面
            SystemEvent.fireEvent(SystemEventConst.EVENT_HIDE_WIFI_CONNECT_CONTAINER_VIEW);
            return;
        }
        showMainView();
        onDestroyEnent();;
        LogUtil.i("onBackPressed", "=======");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtil.i("onActivityResult", "=======");
    }

//    @Override
//    public void onEvent(SystemEventConst eventType, Intent data) {
//        if (eventType.equals(SystemEventConst.EVENT_COMPLETE_GUIDE)) {
//            mListener.onComplete();
//        }
//    }
}
