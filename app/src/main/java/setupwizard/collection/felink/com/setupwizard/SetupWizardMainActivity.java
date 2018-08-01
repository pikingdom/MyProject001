package setupwizard.collection.felink.com.setupwizard;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;

import setupwizard.SetupWizardView;
import setupwizard.collection.felink.com.setupwizard.app.R;

/**
 * 设置向导测试Activity
 * 作者：xiaomao on 2017/7/14.
 */

public class SetupWizardMainActivity extends Activity {
    private View contentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationBarStatusBar(this,true);
        setContentView(R.layout.setup_wizard_main_activity);
        initView();
    }

    private void initView(){
        LinearLayout mRoot = (LinearLayout)findViewById(R.id.setup_wizard_main_layout);
        contentView = SetupWizardView.getNowModelSetupWizardView(this, new IGuideCallback() {
            @Override
            public void onComplete() {
                finish();
            }
        });
        mRoot.addView(contentView,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (contentView instanceof IGuideView) {
            IGuideView view = (IGuideView) contentView;
            view.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (contentView instanceof IGuideView) {
            IGuideView view = (IGuideView) contentView;
            view.onBackPressed();
            return;
        }
        super.onBackPressed();
    }

    public static void NavigationBarStatusBar(Activity activity,boolean hasFocus){
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(0x00000100 | 0x00000200 | 0x00000400 | 0x00000002 | 0x00000004 | 0x00001000);
        }
    }
}
