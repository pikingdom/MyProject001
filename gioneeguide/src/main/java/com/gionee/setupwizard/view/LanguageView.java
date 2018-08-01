package com.gionee.setupwizard.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gionee.setupwizard.R;

import java.util.Arrays;
import java.util.Locale;

/**
 * 语言选择界面
 * 作者：xiaomao on 2017/7/14.
 */
public class LanguageView extends LinearLayout {
    private Context context;
    private GioneeSetupWizardView gioneeSetupWizardView;

    private ListView languageListView;
    private LanguageAdapter languageAdapter;

    private TextView languageWelcome;
    private Button languageNextbutton;

    public LanguageView(Context context,GioneeSetupWizardView gioneeSetupWizardView) {
        super(context);
        this.context = context;
        this.gioneeSetupWizardView = gioneeSetupWizardView;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_layout_language,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

        languageWelcome = (TextView)findViewById(R.id.gn_sw_layout_language_welcome);
        languageNextbutton = (Button) findViewById(R.id.gn_sw_layout_language_nextbutton);
        languageNextbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                gioneeSetupWizardView.setCurrentlyShowView(GioneeSetupWizardView.WIFI_VIEW);
            }
        });
        languageListView = (ListView)findViewById(R.id.gn_sw_layout_language_list);
        languageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                updateLanguage(languageAdapter.getItem(i).getLocale());
                languageAdapter.notifyDataSetChanged();
            }
        });
        //初始化语言适配器
        String[] locales = Resources.getSystem().getAssets().getLocales();
        Arrays.sort(locales);
        LocaleInfo[] preprocess = new LocaleInfo[locales.length];
        int i = 0;
        int finalSize = 0;
        while (i < locales.length) {
            int finalSize2;
            String s = locales[i];
            if (s.length() == 5) {
                String language = s.substring(0, 2);
                Locale l = new Locale(language, s.substring(3, 5));
                if (finalSize == 0) {
                    finalSize2 = finalSize + 1;
                    preprocess[finalSize] = new LocaleInfo(toTitleCase(l.getDisplayLanguage(l)), l);
                } else if (preprocess[finalSize - 1].locale.getLanguage().equals(language)) {
                    preprocess[finalSize - 1].label = toTitleCase(l.getDisplayName());
                    finalSize2 = finalSize + 1;
                    preprocess[finalSize] = new LocaleInfo(toTitleCase(l.getDisplayName()), l);
                } else {
                    String displayName;
                    if (s.equals("zz_ZZ")) {
                        displayName = "Pseudo...";
                    } else {
                        displayName = toTitleCase(l.getDisplayLanguage(l));
                    }
                    finalSize2 = finalSize + 1;
                    preprocess[finalSize] = new LocaleInfo(displayName, l);
                }
            } else {
                finalSize2 = finalSize;
            }
            i++;
            finalSize = finalSize2;
        }
        LocaleInfo[] localeInfos = new LocaleInfo[finalSize];
        for (i = 0; i < finalSize; i++) {
            localeInfos[i] = preprocess[i];
        }
        languageAdapter = new LanguageAdapter(context,localeInfos);
        languageListView.setAdapter(languageAdapter);
    }
    private String toTitleCase(String s) {
        if (s.length() == 0) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private class LanguageAdapter extends BaseAdapter{
        private Context context;
        private LocaleInfo[] localeInfos;

        LanguageAdapter(Context context, LocaleInfo[] localeInfos) {
            this.context = context;
            this.localeInfos = localeInfos;
        }

        public int getCount() {
            return this.localeInfos.length;
        }

        public LocaleInfo getItem(int position) {
            return this.localeInfos[position];
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = newView();
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            Locale locale = getItem(position).getLocale();
            holder.textView.setText(locale.getDisplayName(locale));
            if (isDefaultLocale(locale)) {
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
            }
            return view;
        }
        private View newView() {
            View view = LayoutInflater.from(this.context).inflate(R.layout.gn_item_layout_language, null, false);
            ViewHolder holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.gn_sw_layout_single_item_textView);
            holder.radioButton = (RadioButton) view.findViewById(R.id.gn_sw_layout_single_item_RadioButton);
            view.setTag(holder);
            return view;
        }

        private boolean isDefaultLocale(Locale locale) {
            return getResources().getConfiguration().locale.equals(locale);
        }

    }

    /**
     * 语言设置需要系统权限啊
     * @param locale
     */
    private void updateLanguage(Locale locale) {
//        try {
//            Class clzIActivityManager = Class.forName("android.app.IActivityManager");
//            Class clzActivityManagerNative = Class.forName("android.app.ActivityManagerNative");
//            Method getDefault = clzActivityManagerNative.getDeclaredMethod("getDefault", new Class[0]);
//            getDefault.setAccessible(true);
//            Object objIActMag = getDefault.invoke(clzActivityManagerNative, new Object[0]);
//            Method getConfiguration = clzIActivityManager.getDeclaredMethod("getConfiguration", new Class[0]);
//            getConfiguration.setAccessible(true);
//            Configuration config = (Configuration) getConfiguration.invoke(objIActMag, new Object[0]);
//            config.setLocale(locale);
////            config.userSetLocale = true;
//            Method updateConfiguration = clzIActivityManager.getDeclaredMethod("updateConfiguration", new Class[]{Configuration.class});
//            updateConfiguration.setAccessible(true);
//            updateConfiguration.invoke(objIActMag, new Object[]{config});
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
        languageWelcome.setText(context.getString(R.string.gn_sw_string_welcome));
        languageNextbutton.setText(context.getString(R.string.gn_sw_string_next));
    }

    private class LocaleInfo {
        private String label;
        private Locale locale;
        public LocaleInfo(String label, Locale locale) {
            this.label = label;
            this.locale = locale;
        }
        public Locale getLocale() {
            return this.locale;
        }
        public String getLabel() {
            return this.label;
        }
        public String toString() {
            return this.label;
        }
    }
    private class ViewHolder {
        public RadioButton radioButton;
        public TextView textView;

        ViewHolder() {
        }
    }

}
