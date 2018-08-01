package com.wifi.vivoguide.y79;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wifi.vivoguide.Business.LanguageBusiness;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.y79a.ChangeViewInterface;

/**
 * Created by xuqunxing on 2017/11/27.
 */
public class VivoLanguageChooseView_Y79 extends RelativeLayout implements LanguageAdapter_y79.OnRecyclerViewListener {

    private ListView mRecycleView;
    private ChangeViewInterface changeViewInterface;
    private Context context;
    private String[] mLanguageArr;
    private LanguageAdapter_y79 mLanguageAdapter;

    public VivoLanguageChooseView_Y79(Context context) {
        super(context);
        initView();
    }

    public VivoLanguageChooseView_Y79(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VivoLanguageChooseView_Y79(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public VivoLanguageChooseView_Y79(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.context = context;
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.vivo_guide_entrance_view_y79,this);
        mRecycleView = (ListView) findViewById(R.id.recyclerView);
        mLanguageArr = context.getResources().getStringArray(R.array.language_array);
        mLanguageAdapter = new LanguageAdapter_y79(context, mLanguageArr);
        mRecycleView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onItemClick(LanguageAdapter_y79.LanguageHolder holder, int position) {
        setCurrentLanguageType(position);
        if(changeViewInterface != null){
            changeViewInterface.setCurrentlyShowView(ChangeViewInterface.SERVICE_DES);
        }
    }

    /***设置语言*/
    public void setCurrentLanguageType(int pos) {
        Configuration config = LanguageBusiness.getCurrentLanguage(context, pos);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        context.getResources().updateConfiguration(config, dm);
    }

}
