package com.hissenseguide;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.hissenseguide.listener.ChangeViewInterface;
import com.hissenseguide.util.Utils;

/**数据连接
 * Created by xuqunxing on 2017/11/22.
 */
public class GuiDataSettingView extends RelativeLayout implements View.OnClickListener {

    private TextView nextTv;
    private TextView backTv;
    private ChangeViewInterface changeViewInterface;
    private Context context;
    private ToggleButton toggleButton;

    public GuiDataSettingView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public GuiDataSettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public GuiDataSettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    public GuiDataSettingView(Context context,ChangeViewInterface changeViewInterface1) {
        super(context);
        this.context = context;
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.guide_data_setting,this);
        toggleButton = (ToggleButton) findViewById(R.id.data_set_tg);
        nextTv = (TextView) findViewById(R.id.guide_activity_next_textview);
        backTv = (TextView) findViewById(R.id.guide_activity_back_textview);
        nextTv.setText(getContext().getString(R.string.guide_next));
        backTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);

        try {
            if(context !=null && context instanceof Activity){
                Activity activity = (Activity) context;
                boolean simCard = Utils.readSIMCard(activity);
                if(!simCard){
                    toggleButton.setChecked(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.LOGIN_HISENSE_ACCOUNT);
            }
        }else if(view == nextTv){
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.FEATURED);
            }
        }
    }


}
