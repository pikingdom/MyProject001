package setupwizard;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.bootreg.IGuideCallback;
import com.coloros.bootreg.view.OppoR15WelcomePage.R15WelcomePage;
import com.coloros.bootreg.view.WelcomePage;
import com.gionee.setupwizard.s10bl.GioneeGuideMainView;
import com.gionee.setupwizard.view.GioneeSetupWizardView;
import com.hissenseguide.HisenseGuideMainView;
import com.huawei.hwstartupguide.HuaweiGuideMainView;
import com.wifi.vivoguide.y79.VivoY79GuideMainView;
import com.wifi.vivoguide.y79a.VivoY79AGuideMainView;
import com.wifi.vivoguide.y85a.VivoY85aGuideMainView;
import com.wifi.xiaomiguide.View.MIUIGuideMainView;
import com.wifi.xiaomiguide.miuiv10.MIUI_V10GuideMainView;
import com.wifi.xiaomiguide.miuiv9.MIUI_V9GuideMainView;
import com.wifi.xiaomiguide.miuiv9.PhonOSUtil;
import com.zteguidedemo.v0840.ZTEGuideMainView;
import com.zteguidedemo.view.MainView;

import setup.meizu.com.mzsetupwizard.MeizuSetupLayout;


/**
 * 选择根据机型选择设置向导
 * 作者：xiaomao on 2017/7/14.
 */

public class SetupWizardView {
    public static View getNowModelSetupWizardView(Activity activity, IGuideCallback iGuideCallback) {
        View result = null;
        String machineName = Build.MODEL.toLowerCase();//手机型号
        String carrier= android.os.Build.MANUFACTURER.toLowerCase();//手机厂商
        if (machineName.contains("gionee") || carrier.contains("gionee")) {//金立手机
            if(machineName.contains("gionee")&& machineName.contains("s10")){
                result =  new GioneeGuideMainView(activity,iGuideCallback);
            }else {
                result = new GioneeSetupWizardView(activity,iGuideCallback);
            }
        }else if (machineName.contains("oppo r11") &&  carrier.contains("oppo")) {
            WelcomePage welcomePage = new WelcomePage(activity, iGuideCallback);
            welcomePage.setActivity(activity);
            result = welcomePage;
        } else if(machineName.contains("pacm00") &&  carrier.contains("oppo")){
            //oppo r15
            R15WelcomePage welcomePage = new R15WelcomePage(activity, iGuideCallback);
            welcomePage.setActivity(activity);
            result = welcomePage;
        } else if(machineName.contains("oppo") || carrier.contains("oppo")) {
//            WelcomePage2 welcomePage2 = new WelcomePage2(activity, iGuideCallback);
//            welcomePage2.setActivity(activity);
//            result = welcomePage2;
            R15WelcomePage welcomePage = new R15WelcomePage(activity, iGuideCallback);
            welcomePage.setActivity(activity);
            result = welcomePage;
        } else if (machineName.contains("vivo") || carrier.contains("vivo")) {
            if((machineName.contains("y79a"))){
                result =  new VivoY79AGuideMainView(activity,iGuideCallback);
            }else if(machineName.contains("y79")|| machineName.contains("x20")){
                result =  new VivoY79GuideMainView(activity,iGuideCallback);
            }else{
                result =  new VivoY85aGuideMainView(activity, iGuideCallback);
            }
        } else if (machineName.contains("zte") || carrier.contains("zte")) {
            if(machineName.contains("zte")&& machineName.contains("v0840")){
                result =  new ZTEGuideMainView(activity,iGuideCallback);
            }else {
                result = new MainView(activity,iGuideCallback);
            }
        } else if (machineName.contains("huawei") || carrier.contains("huawei")){
            result = new HuaweiGuideMainView(activity,iGuideCallback);
        } else if (machineName.contains("meizu") || carrier.contains("meizu")){
            result = MeizuSetupLayout.build(activity,iGuideCallback);
        } else if (machineName.contains("redmi") || carrier.contains("xiaomi")){
            String systemPropertiesOS = PhonOSUtil.getPhoneOS(Build.BRAND);
//            Toast.makeText(activity.getApplicationContext(),""+systemPropertiesOS,Toast.LENGTH_SHORT).show();
            if(systemPropertiesOS.contains("V9")){
                result = new MIUI_V9GuideMainView(activity,iGuideCallback);
            }else if(systemPropertiesOS.contains("V10")){
                result = new MIUI_V10GuideMainView(activity,iGuideCallback);
            }else {
                result = new MIUIGuideMainView(activity,iGuideCallback);
            }
        }else if (machineName.contains("hisense e77") || carrier.contains("hisense")){
            result = new HisenseGuideMainView(activity,iGuideCallback);
        }
        return result;
    }
}
