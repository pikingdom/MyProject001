package com.coloros.bootreg.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coloros.boot.guide.R;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/15.
 */

public class StatementPage extends GuidePage implements View.OnClickListener {
    public StatementPage(Context context, int flag) {
        super(context);
        mFlag = flag;
        init(context);
    }

    public static final int FLAG_DEFAULT = 0;
    public static final int FLAG_PROTOCOL = 1;
    public static final int FLAG_SECRET = 2;
    private int mFlag;
    private TextView mTextView;
    private TextView title;
    private View back;
    private View backTxt;
    private View agree;
    private View disagree;

    private LayoutInflater mInflater;

    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_statement_page, null);
        addView(v);
        initView();
    }

    private void initView() {
        this.mTextView = (TextView) findViewById(R.id.statement_text);
        title = (TextView) findViewById(R.id.title);
//        ActionBar actionBar = getActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
        if (this.mFlag == FLAG_PROTOCOL) {
            title.setText(R.string.protocol_title);
        } else if (this.mFlag == FLAG_SECRET) {
            title.setText(R.string.secret_title);
        }
        loadView();

        back = findViewById(R.id.action_bar_button);
        back.setOnClickListener(this);
        backTxt = findViewById(R.id.backTxt);
        backTxt.setOnClickListener(this);
        agree = findViewById(R.id.agree);
        agree.setOnClickListener(this);
        disagree = findViewById(R.id.disagree);
        disagree.setOnClickListener(this);

    }

    private void loadView() {
        String language ="CN";
        Configuration config=getResources().getConfiguration();
        language=config.locale.getCountry();
        if (TextUtils.isEmpty(language)) {
            language = "CN";
        }
        language=language.toUpperCase();
        String file = "";
        if (this.mFlag == FLAG_PROTOCOL) {
            if (language.equals("CN")) {
                file = "protocol_cn.html";
            } else if (language.equals("TW")) {
                file = "protocol_tw.html";
            } else if (language.equals("US")) {
                file = "protocol_en.html";
            } else {
                file = "protocol_cn.html";
            }
        } else if (this.mFlag == FLAG_SECRET) {
            if (language.equals("CN")) {
                file = "secret_cn.html";
            } else if (language.equals("TW")) {
                file = "secret_tw.html";
            } else if (language.equals("US")){
                file = "secret_en.html";
            }else {
                file = "secret_cn.html";
            }
        }
        this.mTextView.setText(Html.fromHtml(getStrFromFile(file)));
    }


    private String getStrFromFile(String fileName) {
        String res = "";
        try {
            InputStream in = getResources().getAssets().open(fileName);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();

            res = new String(buffer, "gb2312");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String gb2312ToUtf8(String str) {
        String urlEncode = "";
        try {

            urlEncode = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlEncode;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onClick(View view) {
        if (onOPListener == null) return;
        if (back == view || backTxt == view || disagree == view) {
            onOPListener.OnBack(this);
            return;
        }
        if (agree == view) {
            onOPListener.OnNext(this);
            return;
        }
    }
}
