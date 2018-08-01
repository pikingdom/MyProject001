package com.adapter.honoradapter.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.huawei.hwstartupguide.R;

/**
 * Created by xuqunxing on 2017/7/20.
 */
public class VisionSettingView extends RelativeLayout implements View.OnClickListener {

    private LanguageSelectView.onClickListener onClickListener;

    public VisionSettingView(Context context,LanguageSelectView.onClickListener onClickListener1) {
        super(context);
        this.onClickListener = onClickListener1;
        initView();
    }

    public VisionSettingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VisionSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.page_vision_setting,this);
        findViewById(R.id.gestures_layout).setOnClickListener(this);
        findViewById(R.id.font_size_layout).setOnClickListener(this);
        findViewById(R.id.view_mode_layout).setOnClickListener(this);
        findViewById(R.id.title_back_view).setOnClickListener(this);
    }

    private void intentToSettingsByAction(String action) {
        try {
            Intent intent = new Intent(action);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        } catch (Exception e) {
            Log.e("HwStartupGuide", "intentToSettingsByAction : " + e.getMessage());
        }
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.title_back_view) {
            if (onClickListener != null) {
                onClickListener.onClickBackListener();
            }
            return;
        } else if (i == R.id.gestures_layout) {
            intentToSettingsByAction("huawei.settings.intent.action.SCREEN_MAGNIFICATION_FOR_SUW");
            return;
        } else if (i == R.id.font_size_layout) {
            intentToSettingsByAction("huawei.settings.intent.action.FONT_SIZE_FOR_SUW");
            return;
        } else if (i == R.id.view_mode_layout) {
            intentToSettingsByAction("huawei.settings.intent.action.SCREEN_ZOOM_FOR_SUW");
            return;
        } else {
            return;
        }
    }

}
