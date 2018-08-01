package com.coloros.bootreg.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.coloros.boot.guide.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Administrator on 2017/7/14.
 */

public class SelectLanguagePage extends GuidePage implements View.OnClickListener {
    private int mPosition = 0;
    private LayoutInflater mInflater;
    private ListView mListView;
    private TextView mTopText;
    private LanguageAdapter mLanguageAdapter;
    //private TextView mGoOn;
    private boolean isFromWifi = false;

    public SelectLanguagePage(Context context) {
        super(context);
        init(context);
    }

    public SelectLanguagePage(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            if (onOPListener != null) {
                isFromWifi = true;
                onOPListener.OnNext(this);
            }
        }
    }

    public boolean isFromWifi() {
        return isFromWifi;
    }

    private String getString(int resid) {
        Resources resources = getContext().getResources();
        return resources.getString(resid);
    }

    private void init(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = mInflater.inflate(R.layout.activity_language_page, null);
        addView(v);
        this.mListView = (ListView) findViewById(R.id.wheel_view);
        this.mListView.addHeaderView(View.inflate(context, R.layout.empty, null));
        this.mListView.setHeaderDividersEnabled(true);
        this.mTopText = (TextView) findViewById(R.id.language_welcome);
        ArrayList<String> list = new ArrayList();
        list.add(getString(R.string.language_tag_zh_rcn));
        list.add(getString(R.string.language_tag_zh_rcw));
        list.add(getString(R.string.language_tag_en));
        this.mLanguageAdapter = new LanguageAdapter(context, list);
        this.mListView.setAdapter(this.mLanguageAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View paramView, int paramInt, long paramLong) {
                SelectLanguagePage.this.select((int) paramLong);
                SelectLanguagePage.this.onClick(SelectLanguagePage.this.mListView);
                SelectLanguagePage.this.mLanguageAdapter.notifyDataSetChanged();
            }
        });
//        this.mGoOn = (TextView) findViewById(R.id.go_on);
//        this.mGoOn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        //setConfig(this.mPosition);
        try {
            welcomePage.callSystemWifiPage();
        } catch (Exception e) {
            e.printStackTrace();
            onOPListener.OnNext(this);
        }

        return;
    }

    private class LanguageAdapter extends BaseAdapter {
        private ArrayList<String> list;
        private LayoutInflater mInflater;

        protected LanguageAdapter(Context context, ArrayList<String> list) {
            this.list = list;
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return 3;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = this.mInflater.inflate(R.layout.tempitem, parent, false);
            TextView tempValue = (TextView) convertView.findViewById(R.id.tempValue);
            RadioButton radiobutton = (RadioButton) convertView.findViewById(R.id.radiobutton);
            if (SelectLanguagePage.this.mPosition == position) {
                radiobutton.setChecked(true);
            } else {
                radiobutton.setChecked(false);
            }
            if (position == 0) {
                tempValue.setText((CharSequence) this.list.get(0));
            } else if (position == 1) {
                tempValue.setText((CharSequence) this.list.get(1));
            } else if (position == 2) {
                tempValue.setText((CharSequence) this.list.get(2));
            }
            return convertView;
        }
    }

    private void select(int position) {
        this.mPosition = position;
//        switch (position) {
//            case 1 /*1*/:
//                this.mTopText.setText(R.string.language_welcome_rtw);
//                //this.mGoOn.setText(R.string.language_next_rtw);
//                return;
//            case 2 /*2*/:
//                this.mTopText.setText(R.string.language_welcome_en);
//                //this.mGoOn.setText(R.string.language_next_en);
//                return;
//            default:
//                this.mTopText.setText(R.string.language_welcome);
//                //this.mGoOn.setText(R.string.language_next_rzh);
//                return;
 //       }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == VISIBLE) {
            getConfig();
            select(this.mPosition);
            this.mLanguageAdapter.notifyDataSetChanged();
            this.mListView.setSelection(this.mPosition);

        }

        super.onWindowVisibilityChanged(visibility);
    }

    private void setConfig(int language) {
        try {
            //IActivityManager am = ActivityManagerNative.getDefault();
            //Configuration config = am.getConfiguration();
            switch (language) {
                case 0 /*0*/:
                    //config.locale = Locale.SIMPLIFIED_CHINESE;
                    updateLanguage(Locale.SIMPLIFIED_CHINESE);
                    break;
                case 1 /*1*/:
                    //config.locale = Locale.TRADITIONAL_CHINESE;
                    updateLanguage(Locale.TRADITIONAL_CHINESE);
                    break;
                case 2 /*2*/:
                    updateLanguage(Locale.US);
                    //config.locale = Locale.US;
                    break;
                default:
                    updateLanguage(Locale.US);
                    //config.locale = Locale.US;
                    break;
            }
            //config.userSetLocale = true;
            // am.updateConfiguration(config);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getConfig() {
        Locale locale = getResources().getConfiguration().locale;
        this.mPosition = 0;
        if (Locale.SIMPLIFIED_CHINESE.equals(locale)) {
            this.mPosition = 0;
        } else if (Locale.TRADITIONAL_CHINESE.equals(locale)) {
            this.mPosition = 1;
        } else {
            this.mPosition = 2;
        }
    }


    private void updateLanguage(Locale locale) {
//        Resources resources=getContext().getResources();
//        Configuration config=resources.getConfiguration();
//        config.locale=locale;
//        DisplayMetrics dm=resources.getDisplayMetrics();
//        resources.updateConfiguration(config,dm);
    }


}
