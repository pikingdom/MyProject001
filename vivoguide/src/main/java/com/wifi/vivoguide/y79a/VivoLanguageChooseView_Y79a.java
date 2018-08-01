package com.wifi.vivoguide.y79a;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wifi.vivoguide.Business.LanguageBusiness;
import com.wifi.vivoguide.R;
import com.wifi.vivoguide.wifi.Adapter.LanguageAdapter;

/**
 * Created by xuqunxing on 2017/11/27.
 */
public class VivoLanguageChooseView_Y79a extends RelativeLayout implements LanguageAdapter.OnRecyclerViewListener {

    private ListView mRecycleView;

    public VivoLanguageChooseView_Y79a(Context context) {
        super(context);
        initView();
    }

    public VivoLanguageChooseView_Y79a(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VivoLanguageChooseView_Y79a(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public VivoLanguageChooseView_Y79a(Context context,ChangeViewInterface changeViewInterface1) {
        super(context);
        this.context = context;
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private ChangeViewInterface changeViewInterface;
    private Context context;
    private String[] mLanguageArr;
    private LanguageAdapter mLanguageAdapter;

    private void initView(){
        View.inflate(getContext(), R.layout.vivo_guide_entrance_view_y79a,this);
        mRecycleView = (ListView) findViewById(R.id.recyclerView);
        mLanguageArr = context.getResources().getStringArray(R.array.language_array);
        mLanguageAdapter = new LanguageAdapter(context, mLanguageArr);
        mRecycleView.setAdapter(mLanguageAdapter);
        mLanguageAdapter.setOnRecyclerViewListener(this);
    }

    @Override
    public void onItemClick(LanguageAdapter.LanguageHolder holder, int position) {
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
