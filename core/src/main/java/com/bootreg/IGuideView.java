package com.bootreg;

import android.content.Intent;

/**
 * Created by Administrator on 2017/7/17.
 */

public interface IGuideView {
     /**为了避免和桌面requestCode冲突，现规定以下规则
      * onActivityResult 的 requestCode 规则
      * oppo机型requestCode 范围 1000001-1000099
      * 金立机型requestCode 范围 1000100-1000199
      * 魅族机型requestCode 范围 1000200-1000299
      * vivo机型requestCode 范围 1000300-1000399
      * 华为机型requestCode 范围 1000400-1000499
      * 其他的机型如上自定义下
      **/
     public static final int HW_REQUEST_WIFI_CODE = 1000400;

     void onBackPressed();
     void onActivityResult(int requestCode, int resultCode, Intent data);
}
