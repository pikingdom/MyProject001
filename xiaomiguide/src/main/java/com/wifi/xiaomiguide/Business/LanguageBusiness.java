package com.wifi.xiaomiguide.Business;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by llf on 2017/08/09.
 */
public class LanguageBusiness {

    private static final int LANGUAGE_CN = 0;
    private static final int LANGUAGE_TW = 1;
    private static final int LANGUAGE_EN = 2;


    public static Configuration getCurrentLanguage(Context context, int position) {
        Configuration config = context.getResources().getConfiguration();
        switch (position) {
            case LANGUAGE_CN:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                break;

            case LANGUAGE_TW:
                config.locale = Locale.TAIWAN;
                break;

            case LANGUAGE_EN:
                config.locale = Locale.SIMPLIFIED_CHINESE;
                break;
        }


        return config;
    }
}
