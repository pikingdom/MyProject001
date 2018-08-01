package com.wifi.xiaomiguide.miuiv9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wifi.xiaomiguide.ChangeViewInterface;
import com.wifi.xiaomiguide.R;

/**个性化风格
 * Created by xuqunxing on 2017/11/28.
 */
public class PersonalStyle_V9_View extends RelativeLayout implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView backTv;
    private TextView nextTv;
    private ChangeViewInterface mChangeViewInterface;
    private CheckBox checkBoxNoBorder;
    private CheckBox checkBoxNormal;

    public PersonalStyle_V9_View(Context context) {
        super(context);
    }

    public PersonalStyle_V9_View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PersonalStyle_V9_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PersonalStyle_V9_View(Context context, ChangeViewInterface changeViewInterface) {
        super(context, null);
        mChangeViewInterface = changeViewInterface;
        initView();
    }

    private void initView(){
        View.inflate(getContext(), R.layout.person_style_v9,this);
        checkBoxNoBorder = (CheckBox) findViewById(R.id.checkbox_miui_v9_no_border);
        checkBoxNormal = (CheckBox) findViewById(R.id.checkbox_miui_v9_normal);

        backTv = (TextView) findViewById(R.id.bottom_back);
        nextTv = (TextView) findViewById(R.id.bottom_next);
        nextTv.setText(getContext().getString(R.string.go_on));

        backTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);
        checkBoxNoBorder.setOnCheckedChangeListener(this);
        checkBoxNormal.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == backTv){
            if(mChangeViewInterface != null){
                mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.OTHER_SETTING_VIEW,null);
            }
        }else if(view == nextTv){
            if(mChangeViewInterface != null){
                mChangeViewInterface.setCurrentlyShowView(ChangeViewInterface.FINISH_VIEW,null);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(compoundButton == checkBoxNoBorder){
            checkBoxNoBorder.setChecked(b);
            checkBoxNormal.setChecked(!b);
        }else if(compoundButton == checkBoxNormal){
            checkBoxNormal.setChecked(b);
            checkBoxNoBorder.setChecked(!b);
        }
    }
}
