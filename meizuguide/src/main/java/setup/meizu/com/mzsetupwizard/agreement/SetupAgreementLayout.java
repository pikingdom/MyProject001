package setup.meizu.com.mzsetupwizard.agreement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import setup.meizu.com.mzsetupwizard.R;
import setup.meizu.com.mzsetupwizard.SetupFlowListenner;

/**
 * Created by linliangbin on 2017/7/18 11:27.
 */

public class SetupAgreementLayout extends RelativeLayout {
    private Button back, next;
    private SetupFlowListenner lis;

    public SetupAgreementLayout(Context context, SetupFlowListenner lis) {
        super(context);
        populate(context);
        this.lis = lis;
    }

    private void populate(Context ctx) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.setup_warning, null);

        back = (Button)v.findViewById(R.id.BtnBack);
        back.setText(R.string.disagree);
        next = (Button)v.findViewById(R.id.BtnNext);
        next.setText(R.string.agree);

        back.setOnClickListener(mOnClickListenner);
        next.setOnClickListener(mOnClickListenner);

//        next.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    Context thirdContext = context.createPackageContext("com.meizu.setup", CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
//                    Intent sys = new Intent();
//                    sys.setPackage("com.meizu.setup");
//                    sys.setComponent(new ComponentName("com.meizu.setup", "com.meizu.setup.activity.SetupLanguageActivity"));
//                    sys.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    thirdContext.startActivity(sys);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        this.addView(v);
    }

    private View.OnClickListener mOnClickListenner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.BtnBack) {
                lis.onBack(SetupAgreementLayout.class.getName());
            } else {
                lis.onNext(SetupAgreementLayout.class.getName());
            }
        }
    };
}
