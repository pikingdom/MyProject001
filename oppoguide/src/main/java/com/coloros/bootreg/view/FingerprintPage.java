package com.coloros.bootreg.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.coloros.boot.guide.R;

/**
 * Created by Administrator on 2017/7/15.
 */

public class FingerprintPage extends GuidePage implements View.OnClickListener {
    public FingerprintPage(Context context) {
        super(context);
        init(context);
    }

    private TextView setup;
    private TextView skip;
    private View back;
    private View backTxt;


    public FingerprintPage(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && onOPListener != null) {
            onOPListener.OnNext(this);
        }
    }


    private String getString(int resid) {
        Resources resources = getContext().getResources();
        return resources.getString(resid);
    }

    private LayoutInflater mInflater;

    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_fingerprint_page, null);
        addView(v);
        setup = (TextView) findViewById(R.id.set_up);
        skip = (TextView) findViewById(R.id.tv_skip_to_nextstep);
        setup.setOnClickListener(this);
        skip.setOnClickListener(this);

        back = findViewById(R.id.action_bar_button);
        backTxt = findViewById(R.id.backTxt);
        back.setOnClickListener(this);
        backTxt.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view == setup) {
            try {
                welcomePage.callFinger();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        if (view == skip) {
            if (onOPListener != null) {
                onOPListener.OnNext(this);
            }
            return;
        }
        if (view == back || view == backTxt) {
            onOPListener.OnBack(this);
            return;
        }
    }
}
