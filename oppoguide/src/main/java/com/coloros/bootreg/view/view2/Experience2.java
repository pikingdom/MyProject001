package com.coloros.bootreg.view.view2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.coloros.boot.guide.R;
import com.coloros.bootreg.view.GuidePage;

/**
 * Created by Administrator on 2017/7/15.
 */

public class Experience2 extends GuidePage implements View.OnClickListener {
    public Experience2(Context context, int flag) {
        super(context);
        init(context);
    }

    private View back;
    private TextView goOn;
    private View protocol;
    private View secret;
    private CheckBox box;
    private TextView addCheck;
    private LayoutInflater mInflater;
    private boolean defaultValue = true;

    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_experience_page2, null);
        addView(v);
        initView();
    }

    private void initView() {
        back = findViewById(R.id.action_bar_button);
        back.setOnClickListener(this);
        goOn = (TextView) findViewById(R.id.go_on);
        goOn.setOnClickListener(this);

        protocol = findViewById(R.id.protocol);
        protocol.setOnClickListener(this);
        secret = findViewById(R.id.secret);
        secret.setOnClickListener(this);

        box = (CheckBox) findViewById(R.id.agree);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeGoOn(b);
            }
        });
        addCheck = (TextView) findViewById(R.id.add_check);
        addCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultValue){
                    addCheck.setBackgroundResource(R.drawable.switch_bg_off_normal);
                }else{
                    addCheck.setBackgroundResource(R.drawable.switch_bg_on_normal);
                }
                defaultValue = !defaultValue;
            }
        });
        changeGoOn(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View view) {
        if (onOPListener == null) return;
        if (back == view) {
            onOPListener.OnBack(this);
            return;
        }
        if (goOn == view) {
            jumpPos = 2;
            onOPListener.OnNext(this);
            return;
        }
        if (protocol == view) {
            jumpPos = 0;
            onOPListener.OnNext(this);
            return;
        }
        if (secret == view) {
            jumpPos = 1;
            onOPListener.OnNext(this);
            return;
        }

    }

    int jumpPos = 0;

    public int jumpPos() {
        return jumpPos;
    }

    private void changeGoOn(boolean enable) {
        goOn.setEnabled(enable);

        if (enable) {
            goOn.setTextColor(0xff2bc896);
        } else {
            goOn.setTextColor(0xffd6d6d6);
        }

    }
}
