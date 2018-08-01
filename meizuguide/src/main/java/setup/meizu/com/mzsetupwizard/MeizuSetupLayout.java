package setup.meizu.com.mzsetupwizard;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import com.bootreg.IGuideCallback;
import com.bootreg.IGuideView;

import setup.meizu.com.mzsetupwizard.agreement.SetupAgreementLayout;
import setup.meizu.com.mzsetupwizard.language.SetupLanguageLayout;
import setup.meizu.com.mzsetupwizard.service.SetupServiceLayout;

/**
 * Created by linliangbin on 2017/7/18 11:28.
 */

public class MeizuSetupLayout extends FrameLayout implements IGuideView, SetupFlowListenner{
    private SetupLanguageLayout recordLanguage;
    private IGuideCallback iGuideCallback;

    public MeizuSetupLayout(Context context, IGuideCallback iGuideCallback) {
        super(context);
        this.iGuideCallback = iGuideCallback;
    }

    @Override
    public void onBackPressed() {
        onBack(this.getChildAt(0).getClass().getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public static View build(Context ctx,IGuideCallback iGuideCallback) {
        MeizuSetupLayout r = new MeizuSetupLayout(ctx,iGuideCallback);
        return r.getGo(ctx);
    }

    private View getGo(Context ctx) {
        setBackgroundResource(R.color.window_bg);
        recordLanguage = new SetupLanguageLayout(ctx, this);
        this.addView(recordLanguage);
        return this;
    }

    public void onNext(String name) {
        if (SetupLanguageLayout.class.getName().equals(name)) {
            this.removeAllViews();
            this.addView(new SetupAgreementLayout(getContext(), this));
        } else if (SetupServiceLayout.class.getName().equals(name)) {
            iGuideCallback.onComplete();
        } else if (SetupAgreementLayout.class.getName().equals(name)) {
            this.removeAllViews();
            this.addView(new SetupServiceLayout(getContext(), this));
        }
    }

    public void onBack(String name) {
        if (SetupAgreementLayout.class.getName().equals(name)) {
            this.removeAllViews();
            this.addView(recordLanguage);
        } else if (SetupServiceLayout.class.getName().equals(name)) {
            this.removeAllViews();
            this.addView(new SetupAgreementLayout(getContext(), this));
        }
    }

}
