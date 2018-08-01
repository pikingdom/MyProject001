package com.coloros.bootreg.util;
import java.util.regex.Pattern;
public class Constants {

    public static final Pattern ACCOUNTS_PATTERN = Pattern.compile("^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,12}$");
    public static final String ACTION_ACTIVATE_STATISTICS = "android.intent.action.ACTIVATE_STATISTICS";
    public static final String ACTION_FRAGMENT_PAGE_GUIDE = "com.coloros.bootreg.activity.setfragmentpage";
    public static final String ACTION_FRAGMENT_PAGE_OCLOUD = "coloros.intent.action.bootreg.OcloudPage";
    public static final String BROADCAST_APP_CLOSE = "com.oppo.bootreg.intent.close";
    public static final int COMPLETE_PAGE_LOGIN_COMPLETE = 0;
    public static final int COMPLETE_PAGE_LOGIN_INCOMPLETE = 1;
    public static final boolean DEBUG = false;
    public static final int DIALOG_AUTHENTICATE_WAIT = 5;
    public static final int DIALOG_FINISH_REGISTER_WAIT = 6;
    public static final int DIALOG_GET_CODE_WAIT = 4;
    public static final int DIALOG_LOGIN_WAIT = 3;
    public static final int DIALOG_MODIFY_CONFIRM_WAIT = 7;
    public static final int DIALOG_SERVER_ERROR = 1;
    public static final int DIALOG_WAIT = 2;
    public static final String EXTRA_ACTION_APPINFO_KEY = "extra_action_appinfo_key";
    public static final String EXTRA_ACTIVATE_STATISTICS = "isActivateStatistics";
    public static final String Key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDpgSW5VkZ6/xvh+wMXezrOokNdiupuvuMj4RVJy44byWDupl4H37z907A26RVdFzMeyLUQB4rsDIaXdxCODlljWW+/K96uF5MsDtOFUBw7VlOclIjcYTv/YDQEul8JoXoOuy1Yf3b5sbTpTuVTcl97tAuLJ8PoGe2K7N3B1eUQqQIDAQAB";
    public static final Pattern LOGIN_PWD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\_]{6,16}$");
    public static final Pattern MOBILEPHONE_PATTERN = Pattern.compile("^[1]{1}[3|4|5|8]{1}[0-9]{9}$");
    public static final Pattern PURE_NUMBER_PATTERN = Pattern.compile("^[0-9]{11}$");
    public static final Pattern PWD_PATTERN = Pattern.compile("^[a-zA-Z0-9\\_]{6,16}$");
    public static final int RESULT_CANCEL = 102;
    public static final int RESULT_FAIL = 101;
    public static final int RESULT_SUCCESS = 100;
    public static final int SC_SERVER_ERROR = 1;
    public static final int SC_TIMEOUT_ERROR = 2;
    public static final int SC_UNKNOWN = -1;
    private static final int SERVER_DECISION = 0;
    private static final int SERVER_DEV = 2;
    private static final int SERVER_GAMMA = 3;
    private static final int SERVER_NORMAL = 0;
    private static final int SERVER_TEST = 1;
    public static final Pattern SMSCODE_PATTERN = Pattern.compile("^[0-9]{4,6}$");
    public static final String TAG = "BootReg";
    public static final Pattern UID_PATTERN = Pattern.compile("^[0-9]{1,32}$");
    public static Boolean isExisted = Boolean.valueOf(DEBUG);
    public static Boolean isFinished = Boolean.valueOf(DEBUG);

    public static String getUserCenterEnv() {
        switch (SERVER_DECISION) {
            case SERVER_NORMAL /*0*/:
                return "http://i.uc.nearme.com.cn/";
            case SERVER_TEST /*1*/:
                return "http://uc1.wanyol.com:12346/";
            case SERVER_DEV /*2*/:
                return "http://172.16.100.117:12346/";
            case SERVER_GAMMA /*3*/:
                return "http://uc2.wanyol.com:12346/";
            default:
                return "http://uc.nearme.com.cn/usercenter/";
        }
    }

    public static String getUserCenterDoc() {
        switch (SERVER_DECISION) {
            case SERVER_NORMAL /*0*/:
                return "http://uc.nearme.com.cn/usercenter/";
            case SERVER_TEST /*1*/:
                return "http://uc1.wanyol.com:8087/newuser/";
            case SERVER_DEV /*2*/:
                return "http://ucenterdev1.wanyol.com:8087/usercenter/";
            case SERVER_GAMMA /*3*/:
                return "http://uc2.wanyol.com/usercenter/";
            default:
                return "http://uc.nearme.com.cn/usercenter/";
        }
    }
}