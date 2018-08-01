package com.wifi.xiaomiguide.View;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.Adapter.LanguageAdapter;
import com.wifi.xiaomiguide.Business.AnimationTranslateBusiness;
import com.wifi.xiaomiguide.Business.LanguageBusiness;
import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**
 * Created by llf on 2017/8/7.
 */

public class LanguageSelectView extends RelativeLayout implements LanguageAdapter.OnRecyclerViewListener, View.OnClickListener{
    private ChangeViewInterface mChangeViewInterface;
    private Context mContext;
    private TextView mMiuiTitle;
    private ListView mListView;
    private TextView mGoOnBtn, mJumpBtn, mBackBtn;
    private LanguageAdapter mLanguageAdapter;
    private String[] mLanguageArr;

    public LanguageSelectView(Context context, ChangeViewInterface changeViewInterface1, int pos) {
        super(context);
        this.mChangeViewInterface = changeViewInterface1;
        mContext = context;

        //更新当前选择的语言
        setCurrentLanguageType(pos);
        findViewsById();
        setViews(pos);
        setListeners();
    }

    public void setCurrentLanguageType(int pos) {
        Configuration config = LanguageBusiness.getCurrentLanguage(mContext, pos);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        mContext.getResources().updateConfiguration(config, dm);
    }


    private void findViewsById() {
       View.inflate(getContext(), R.layout.language_select_view,this);

        mListView = (ListView) findViewById(R.id.listview);
        mGoOnBtn = (TextView) findViewById(R.id.go_on);
        mJumpBtn = (TextView) findViewById(R.id.jump);
        mBackBtn = (TextView) findViewById(R.id.back);
    }

    private void setViews(int position) {
        mJumpBtn.setVisibility(View.GONE);
       // mMiuiTitle.setText(Html.fromHtml(mContext.getString(R.string.mi_title, 8)));
        mLanguageArr = mContext.getResources().getStringArray(R.array.language_item_array);
        mLanguageAdapter = new LanguageAdapter(mContext, mLanguageArr, position);
        mListView.setAdapter(mLanguageAdapter);

    }

    private void setListeners() {
        mLanguageAdapter.setOnRecyclerViewListener(this);

        mGoOnBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
    }

    @Override
    public void onItemClick(LanguageAdapter.LanguageHolder holder, int position) {
        //选择语言切换后，頁面重新加载
        removeView(this);
        mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.LANGUAGE_VIEW, position);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.go_on) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_RIGHT_TO_LEFT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW, null);

        } else if (id == R.id.back) {
            mChangeViewInterface.saveReturnDirection(AnimationTranslateBusiness.FROM_LEFT_TO_RIGHT);
            mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.MIUI_VIEW, null);

        }
    }
}
