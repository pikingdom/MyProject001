package com.coloros.bootreg.view.view2;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;
import com.coloros.bootreg.util.AppInfo;
import com.coloros.bootreg.util.Constants;
import com.coloros.bootreg.view.GuidePage;
import com.coloros.bootreg.view.IWelcomePage;
import com.coloros.bootreg.view.OnOpListener;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by Administrator on 2017/7/15.
 * OPPOA57使用的引导页
 */


public class WelcomePage2 extends LinearLayout implements OnOpListener, IWelcomePage, IGuideView {
    private Activity activity;
    private State state = State.lang;

    @Override
    public void callFinger() {
        Intent intent = new Intent("oppo.intent.action.EnrollFingerprint");
        intent.putExtra(EXTRA_KEY_OPEN_FINGERPRINT_UNLOCK, true);
        activity.startActivityForResult(intent, REQUEST_CODE_FINGER);
    }

    enum State {
        lang,
        wifi, finger
    }

    public WelcomePage2(Context context, IGuideCallback listener) {
        super(context);
        this.listener = listener;
        init(context);
    }

    public WelcomePage2(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public void setState(State state) {
        this.state = state;
    }

    GuidePage currGuidePage;
    SelectLanguagePage2 languagePage;
    FingerprintPage2 fingerPage;
    AccountPage2 accountPage;
    StatementPage2 protocolPage;
    StatementPage2 secretPage;
    CompletePage2 completePage;
    Experience2 experiencePage;

    private void init(Context context) {
        languagePage = new SelectLanguagePage2(context);
        addPageView(languagePage);
        setState(State.lang);
    }


    public static boolean supportFingerprint(Context context) {
        return context.getPackageManager().hasSystemFeature("oppo.fingerprint.support");
    }

    @Override
    public void OnNext(View view) {
        if (view == languagePage) {
            if (supportFingerprint(getContext())) {
                jumpFinger();
            } else {
                jumpAccountPage();
            }
            return;
        }
        //在指纹界面，按跳过
        if (view == fingerPage) {
            jumpAccountPage();
            return;
        }
        if (view == accountPage) {
            jumpExperience(getContext());
            return;
        }
//        if (view == experiencePage) {
//            jumpUserSecret(getContext());
//            return;
//        }
        if (view == experiencePage) {
            if (experiencePage.jumpPos() == 0) {
                jumpUserProtocol(getContext());
            } else if (experiencePage.jumpPos() == 1) {
                jumpUserSecret(getContext());
            } else {
                jumpCompletePage(getContext());
            }
            return;
        }
        if (view == completePage) {
            listener.onComplete();
            return;
        }
    }

    @Override
    public void OnBack(View view) {
        jumpLastPage();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    IGuideCallback listener;


    public static final String WIFI_SETTINGS_PACKAGE = "com.coloros.wirelesssettings";
    public static final String WIFI_SETTINGS_TAG = "oppoWizardRun";
    private static final int REQUEST_CODE_WIFI = 1021;
    private static final int REQUEST_CODE_FINGER = 1022;
    private static final int REQUEST_CODE_REGISTER = 1023;
    //登录界面
    private static final int REQUEST_CODE_LOGIN = 26;

    public static final String EXTRA_KEY_OPEN_FINGERPRINT_UNLOCK = "open_fingerprint_unlock";

    public static final String EXTRA_ACTIVITY_FROM_GUIDE_KEY = "extra_activity_from_guide_key";
    public static final String PLUGIN_HOST_PKG_NAME = "com.coloros.bootreg";


    public void callSystemWifiPage() {
        Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        intent.setPackage(WIFI_SETTINGS_PACKAGE);
        intent.putExtra(WIFI_SETTINGS_TAG, true);
        activity.startActivityForResult(intent, REQUEST_CODE_WIFI);
    }

    public static final String EXTRA_ACTIVITY_FROM_OUTAPP_KEY = "extra_activity_from_outapp_key";

    public void callLoginActivity() {
        try {
            Intent intent = new Intent("oppo.usecenter.intent.action.open.guide.login");
            intent.putExtra(EXTRA_ACTIVITY_FROM_OUTAPP_KEY, true);
            intent.addFlags(67108864);
            activity.startActivityForResult(intent, REQUEST_CODE_LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callRegPage(Context context) {
        try {
            Intent intent = new Intent("oppo.intent.action.regcheckmobile");
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //JumpToHelper.jumpSetFragmentPage(this, TYPE_RESTORE);
        }
    }

    @Override
    public void onBackPressed() {
        jumpLastPage();
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    public void callOneKeyProgressActivity() {
        try {
            Intent intent = new Intent("oppo.usecenter.intent.action.onekeyreg");
            intent.putExtra("extra_action_bootguide_next_page_key", Constants.ACTION_FRAGMENT_PAGE_GUIDE);
            intent.putExtra(EXTRA_ACTIVITY_FROM_GUIDE_KEY, true);
            intent.putExtra(Constants.EXTRA_ACTION_APPINFO_KEY, getBootGuidePkgInfo());
            activity.startActivityForResult(intent, REQUEST_CODE_REGISTER);
        } catch (ActivityNotFoundException e) {
            //JumpToHelper.jumpSetFragmentPage(this, TYPE_RESTORE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_WIFI == requestCode && languagePage != null && languagePage == currGuidePage) {
            languagePage.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (REQUEST_CODE_FINGER == requestCode && fingerPage != null && fingerPage == currGuidePage) {
            fingerPage.onActivityResult(requestCode, resultCode, data);
            return;
        }
        if (REQUEST_CODE_REGISTER == requestCode && accountPage != null && accountPage == currGuidePage) {
            accountPage.onActivityResult(requestCode, resultCode, data);
            return;
        }

        if (REQUEST_CODE_LOGIN == requestCode && accountPage != null && accountPage == currGuidePage) {
            accountPage.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }


    private void jumpFinger() {
        fingerPage = new FingerprintPage2(getContext());
        addPageView(fingerPage);
    }


    //用户协议
    void jumpExperience(Context context) {
        experiencePage = new Experience2(context, StatementPage2.FLAG_PROTOCOL);
        addPageView(experiencePage);
    }

    //用户隐私
    void jumpUserSecret(Context context) {
        secretPage = new StatementPage2(context, StatementPage2.FLAG_SECRET);
        addPageView(secretPage);
    }

    //用户协议
    void jumpUserProtocol(Context context) {
        protocolPage = new StatementPage2(context, StatementPage2.FLAG_PROTOCOL);
        addPageView(protocolPage);
    }

    //完成
    void jumpCompletePage(Context context) {
        completePage = new CompletePage2(context);
        addPageView(completePage);
    }


    void jumpAccountPage() {
        accountPage = new AccountPage2(getContext());
        addPageView(accountPage);
    }

    private void addPageView(GuidePage page) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        page.setWelcomePage(this);
        page.setOnOPListener(this);
        if (currGuidePage != null) {
            removeView(currGuidePage);
            page.setLastPage(currGuidePage);
        }
        currGuidePage = page;
        addView(page, lp);
    }

    private void jumpLastPage() {
        if (currGuidePage != null && currGuidePage.getLastPage() != null) {
            removeView(currGuidePage);
            addView(currGuidePage.getLastPage());
            currGuidePage = currGuidePage.getLastPage();
        }
    }


    private String getBootGuidePkgInfo() {
        return AppInfo.toJson(new AppInfo("", "", PLUGIN_HOST_PKG_NAME, getVersionCode(getContext(), PLUGIN_HOST_PKG_NAME)));
    }

    public int getVersionCode(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        int versionCode = 0;
        try {
            return manager.getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
            return versionCode;
        }
    }

}