package com.zteguidedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.zteguidedemo.R;
import com.zteguidedemo.widget.AutoBgButton;

/**
 * Created by xuqunxing on 2017/7/18.
 */
public class ColorChoiceSettingsView extends BaseRelativilayout implements View.OnClickListener {

    private View mBackground = null;
    private AutoBgButton mAmethyst;
    private AutoBgButton mAquaGreen;
    private AutoBgButton mFreshPeach;
    private AutoBgButton mOceanBlue;
    private AutoBgButton mTigerlily;
    private TextView mlable1 = null;
    private TextView mlable2 = null;
    private TextView previousTv;
    private TextView nextTv;

    public ColorChoiceSettingsView(Context context) {
        this(context,null);
    }

    public ColorChoiceSettingsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorChoiceSettingsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.color_choice_settings_activity,this);
        this.mBackground = findViewById(R.id.Background);
        this.mlable1 = (TextView) findViewById(R.id.lable1);
        this.mlable2 = (TextView) findViewById(R.id.lable2);
        this.mAmethyst = (AutoBgButton) findViewById(R.id.Amethyst);
        this.mFreshPeach = (AutoBgButton) findViewById(R.id.Fresh_Peach);
        this.mAquaGreen = (AutoBgButton) findViewById(R.id.Aqua_Green);
        this.mTigerlily = (AutoBgButton) findViewById(R.id.Tiger_lily);
        this.mOceanBlue = (AutoBgButton) findViewById(R.id.Ocean_Blue);
        previousTv = (TextView) findViewById(R.id.view_previous);
        nextTv = (TextView) findViewById(R.id.view_next);
        nextTv.setText(getContext().getString(R.string.done));

        this.mAmethyst.setOnClickListener(this);
        this.mFreshPeach.setOnClickListener(this);
        this.mAquaGreen.setOnClickListener(this);
        this.mTigerlily.setOnClickListener(this);
        this.mOceanBlue.setOnClickListener(this);
        previousTv.setOnClickListener(this);
        nextTv.setOnClickListener(this);

        mAmethyst.setBackgroundColor(getContext().getResources().getColor(R.color.Amethyst));
        mFreshPeach.setBackgroundColor(getContext().getResources().getColor(R.color.Fresh_Peach));
        mAquaGreen.setBackgroundColor(getContext().getResources().getColor(R.color.Aqua_Green));
        mTigerlily.setBackgroundColor(getContext().getResources().getColor(R.color.Tiger_lily));
        mOceanBlue.setBackgroundColor(getContext().getResources().getColor(R.color.Ocean_Blue));
    }

    @Override
    public void onClick(View v) {
        if (this.mAmethyst == v) {
            this.mBackground.setBackgroundResource(R.color.Amethyst);
            this.mAmethyst.setChecked(true);
            this.mFreshPeach.setChecked(false);
            this.mAquaGreen.setChecked(false);
            this.mTigerlily.setChecked(false);
            this.mOceanBlue.setChecked(false);
        } else if (this.mFreshPeach == v) {
            this.mBackground.setBackgroundResource(R.color.Fresh_Peach);
            this.mFreshPeach.setChecked(true);
            this.mAmethyst.setChecked(false);
            this.mAquaGreen.setChecked(false);
            this.mTigerlily.setChecked(false);
            this.mOceanBlue.setChecked(false);
        } else if (this.mAquaGreen == v) {
            this.mBackground.setBackgroundResource(R.color.Aqua_Green);
            this.mAquaGreen.setChecked(true);
            this.mFreshPeach.setChecked(false);
            this.mAmethyst.setChecked(false);
            this.mTigerlily.setChecked(false);
            this.mOceanBlue.setChecked(false);
        } else if (this.mTigerlily == v) {
            this.mBackground.setBackgroundResource(R.color.Tiger_lily);
            this.mTigerlily.setChecked(true);
            this.mAquaGreen.setChecked(false);
            this.mFreshPeach.setChecked(false);
            this.mAmethyst.setChecked(false);
            this.mOceanBlue.setChecked(false);
        } else if (this.mOceanBlue == v) {
            this.mBackground.setBackgroundResource(R.color.Ocean_Blue);
            this.mOceanBlue.setChecked(true);
            this.mTigerlily.setChecked(false);
            this.mAquaGreen.setChecked(false);
            this.mFreshPeach.setChecked(false);
            this.mAmethyst.setChecked(false);
        } else if(nextTv == v){
            if(onZteClickListener != null){
                onZteClickListener.OnDone(this);
            }
        } else if(previousTv == v){
            if(onZteClickListener != null){
                onZteClickListener.OnPrevious(this);
            }
        }

    }


}
