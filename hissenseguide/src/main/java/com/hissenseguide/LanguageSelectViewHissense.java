package com.hissenseguide;

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

import com.hissenseguide.adapter.LanguageAdapter;
import com.hissenseguide.listener.ChangeViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/11/21.
 */
public class LanguageSelectViewHissense extends RelativeLayout implements View.OnClickListener {


    private TextView nextTv;
    private TextView backTv;

    public LanguageSelectViewHissense(Context context) {
        super(context);
        initView();
    }

    public LanguageSelectViewHissense(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    public LanguageSelectViewHissense(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LanguageSelectViewHissense(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private ChangeViewInterface changeViewInterface;
    private ListView mLanguageList;
    private LanguageAdapter mLanguageAdapter;
    private List<Locale> locales;
    private int currentPosition = 0 ;

    private void initView(){
        View.inflate(getContext(), R.layout.guide_language_choose,this);

        backTv = (TextView) findViewById(R.id.guide_activity_back_textview);
        nextTv = (TextView) findViewById(R.id.guide_activity_next_textview);
        nextTv.setText(getContext().getString(R.string.guide_next));
        nextTv.setOnClickListener(this);
        backTv.setVisibility(GONE);
        mLanguageList = (ListView) findViewById(R.id.locale_list);
        this.mLanguageAdapter = new LanguageAdapter(getContext());
        locales = new ArrayList<>();
        Locale locale3 = Locale.SIMPLIFIED_CHINESE;
        Locale locale4 = Locale.US;
        locales.clear();
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view == nextTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.AGREE_TERMS);
            }
        }
    }
}
