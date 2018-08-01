package com.zteguidedemo.v0840;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/8/21.
 */
public class ZTE0840LanguageSelectView extends RelativeLayout{

    private ListView listView;
    private ZTE0840LanguageAdapter mLanguageAdapter;
    private List<Locale> locales;
    private int currentPosition = 0 ;
    private ChangeViewInterface changeViewInterface;
    private TextView nextTv;

    public ZTE0840LanguageSelectView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZTE0840LanguageSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public ZTE0840LanguageSelectView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.view_zte0840_language,this);
        RelativeLayout logonRl = (RelativeLayout) findViewById(R.id.logo_rl);
        nextTv = (TextView) findViewById(R.id.zte_next_step_btn);
        int currentScreenHeight = ScreenUtil.getCurrentScreenHeight(getContext());
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) logonRl.getLayoutParams();
        layoutParams.height = currentScreenHeight/2;
        listView = (ListView) findViewById(R.id.list_view);
        this.mLanguageAdapter = new ZTE0840LanguageAdapter(getContext());
        locales = new ArrayList<>();
        Locale locale1 = Locale.CHINESE;
        Locale locale2 = Locale.US;
        locales.clear();
        locales.add(locale1);
        locales.add(locale2);
        this.mLanguageAdapter.setSelectPostion(currentPosition);
        this.mLanguageAdapter.setDataSource(locales);
        this.listView.setAdapter(this.mLanguageAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                try {
                    currentPosition = position;
                    mLanguageAdapter.setSelectPostion(position);
                    Resources resources = getContext().getResources();
                    DisplayMetrics dm = resources.getDisplayMetrics();
                    Configuration config = resources.getConfiguration();
                    config.locale = locales.get(position);
                    resources.updateConfiguration(config, dm);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        nextTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changeViewInterface != null){
                    changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
                }
            }
        });
    }
}
