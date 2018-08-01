package com.coloros.bootreg.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/7/15.
 */

public interface IWelcomePage {
   void callFinger();
   void callSystemWifiPage();
   void callOneKeyProgressActivity();
   void callLoginActivity();
   void callRegPage(Context context);
   Activity getActivity();
}
