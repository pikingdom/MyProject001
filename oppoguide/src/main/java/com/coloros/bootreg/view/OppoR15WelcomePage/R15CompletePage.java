package com.coloros.bootreg.view.OppoR15WelcomePage;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.coloros.boot.guide.R;
import com.coloros.bootreg.view.GuidePage;

/**
 * Created by Administrator on 2017/7/17.
 */

public class R15CompletePage extends GuidePage implements View.OnClickListener {
    public R15CompletePage(Context context) {
        super(context);
        init(context);
    }

    public R15CompletePage(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }
    View start;

    private LayoutInflater mInflater;

    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_r15_complete_page, null);
        addView(v);
        start = findViewById(R.id.start_using);
        start.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View view) {
//        if (back == view || backTxt == view) {
//            onOPListener.OnBack(this);
//            return;
//        }
        if (start == view) {
            onOPListener.OnNext(this);
            return;
        }

    }
}
