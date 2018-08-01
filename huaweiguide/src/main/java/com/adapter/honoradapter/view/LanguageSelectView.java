package com.adapter.honoradapter.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.honoradapter.adapter.LanguageAdapter;
import com.adapter.honoradapter.util.UIUtil;
import com.huawei.hwstartupguide.ChangeViewInterface;
import com.huawei.hwstartupguide.R;
import com.huawei.hwstartupguide.ui.wifi.NavigationVisibilityChange;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public class LanguageSelectView extends RelativeLayout implements View.OnClickListener,NavigationVisibilityChange {

    private LanguageAdapter mLanguageAdapter;
    private TextView mPrevBtn;
    private TextView mNextBtn;
    private TextView mTitleView;
    private TextView mEmergencyView;
    private ListView mLanguageList;
    private ChangeViewInterface changeViewInterface;
    private TextView mVisionSettings;
    private RelativeLayout languageRl;
    private VisionSettingView visionSettingView;
    private List<Locale> locales;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int currentPosition = 0 ;
    private RelativeLayout buttonBar;
    //    public LanguageSelectView(Context context) {
//        this(context,null);
//    }

    public LanguageSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LanguageSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public LanguageSelectView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        sharedPreferences = context.getSharedPreferences("GuideSettings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.page_choose_language,this);
        setLogoViewHeight();
        initButtonBar();
        currentPosition = sharedPreferences.getInt("currentPosition",0);
        languageRl = (RelativeLayout) findViewById(R.id.choose_language_rl);
        mTitleView = (TextView) findViewById(R.id.title);
        mEmergencyView = (TextView) findViewById(R.id.emergency);
        mVisionSettings = (TextView) findViewById(R.id.vision_settings_format);
        buttonBar = (RelativeLayout) findViewById(R.id.button_bar);

        mEmergencyView.setOnClickListener(this);
        mVisionSettings.setOnClickListener(this);
        initLanguageListAndAdapter();
    }

    private void initLanguageListAndAdapter() {
        mLanguageList = (ListView) findViewById(R.id.locale_list);
        this.mLanguageAdapter = new LanguageAdapter(getContext());
        locales = new ArrayList<>();
        Locale locale1 = Locale.CHINESE;
        Locale locale2 = Locale.TAIWAN;
        Locale locale3 = Locale.SIMPLIFIED_CHINESE;
        Locale locale4 = Locale.US;
        locales.clear();
        locales.add(locale1);
        locales.add(locale2);
        locales.add(locale3);
        locales.add(locale4);

        this.mLanguageAdapter.setSelectPostion(currentPosition);
        this.mLanguageAdapter.setDataSource(locales);
        this.mLanguageList.setAdapter(this.mLanguageAdapter);
        this.mLanguageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                try {
                    currentPosition = position;
                    mLanguageAdapter.setSelectPostion(position);
                    Resources resources = getContext().getResources();
                    DisplayMetrics dm = resources.getDisplayMetrics();
                    Configuration config = resources.getConfiguration();
                    config.locale = locales.get(position);
                    resources.updateConfiguration(config, dm);

                    //   postInvalidate();
                    requestLayout();
                    mTitleView.setText(getContext().getResources().getString(R.string.choose_language));
                    mEmergencyView.setText(getContext().getResources().getString(R.string.emergency_call_format));
                    mVisionSettings.setText(getContext().getResources().getString(R.string.vision_settings_format));
                    mNextBtn.setText(getContext().getResources().getString(R.string.next_format));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setLogoViewHeight() {
        View logoView = findViewById(R.id.logo_view);
        View languageLogo = findViewById(R.id.language_logo);
        if (logoView != null && languageLogo != null) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            if (!UIUtil.isPadScreenDevice(getContext())) {
                logoView.getLayoutParams().height = (displayMetrics.widthPixels * 71) / 100;
                ((LinearLayout.LayoutParams) languageLogo.getLayoutParams()).topMargin = (((displayMetrics.widthPixels * 4) / 5) * 4) / 25;
            } else if (UIUtil.isLandScreen(getContext())) {
                logoView.getLayoutParams().height = (displayMetrics.widthPixels * 268) / 1000;
                ((LinearLayout.LayoutParams) languageLogo.getLayoutParams()).topMargin = (((displayMetrics.widthPixels * 268) / 1000) * 13) / 100;
            } else {
                logoView.getLayoutParams().height = (displayMetrics.widthPixels * 62) / 100;
                ((LinearLayout.LayoutParams) languageLogo.getLayoutParams()).topMargin = (((displayMetrics.widthPixels * 62) / 100) * 38) / 100;
            }
        }
    }

    protected void initButtonBar() {
        this.mPrevBtn = (TextView) findViewById(R.id.prev_btn);
        this.mPrevBtn.setVisibility(View.INVISIBLE);
        this.mNextBtn = (TextView) findViewById(R.id.next_btn);
        this.mNextBtn.setVisibility(View.VISIBLE);
        this.mNextBtn.setText(R.string.next_format);
        this.mNextBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view ==  mEmergencyView){
            Intent intent = new Intent("com.android.phone.EmergencyDialer.DIAL");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.phone");
            getContext().startActivity(intent);
        }else if(view ==  mVisionSettings){
            visionSettingView = new VisionSettingView(getContext(),new onClickListener(){
                @Override
                public void onClickBackListener() {
                    visionSettingView.setVisibility(GONE);
                }
            });
            visionSettingView.setVisibility(VISIBLE);
            languageRl.addView(visionSettingView);
        }else if(view == mNextBtn){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SIM_VIEW);
                editor.putInt("currentPosition",currentPosition).commit();
            }
        }
    }

    public void onBackPressed() {
        if(visionSettingView != null && visionSettingView.getVisibility() == View.VISIBLE){
            visionSettingView.setVisibility(GONE);
        }
    }

    private onClickListener onClickListener;

    @Override
    public void NavigationVisibilityChangeState(boolean isShow) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) buttonBar.getLayoutParams();
        float currentDensity = getContext().getResources().getDisplayMetrics().density;
        int margin = (int)(30 * currentDensity + 0.5F);
        if (isShow){
            lp.bottomMargin = margin;
        }else{
            lp.bottomMargin = 0;
        }
        buttonBar.setLayoutParams(lp);
        requestLayout();
    }

    public interface onClickListener{
        void onClickBackListener();
    }
}
