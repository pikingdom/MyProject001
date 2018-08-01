package com.wifi.vivoguide.Business;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by Administrator on 2017/7/12.
 */

public class LanguageBusiness {

    private static final int LANGUAGE_CN = 0;
    private static final int LANGUAGE_EN = 1;
    private static final int LANGUAGE_TW = 2;
    private static final int LANGUAGE_HK = 3;


    public static Configuration getCurrentLanguage(Context context, int position) {
        Configuration config = context.getResources().getConfiguration();
        switch (position) {
            case LANGUAGE_CN:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                break;
            case LANGUAGE_EN:
                config.locale = Locale.US;
                break;
            case LANGUAGE_TW:
                config.locale = Locale.TAIWAN;
                break;
            case LANGUAGE_HK:
                config.locale = Locale.TAIWAN;
                break;
        }


        return config;
    }
}
