package setup.meizu.com.mzsetupwizard.service;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import setup.meizu.com.mzsetupwizard.R;
import setup.meizu.com.mzsetupwizard.SetupFlowListenner;
import setup.meizu.com.mzsetupwizard.agreement.SetupAgreementLayout;

/**
 * Created by linliangbin on 2017/7/18 11:26.
 */

public class SetupServiceLayout extends RelativeLayout {
    Button back, next;
    private SetupFlowListenner lis;

    public SetupServiceLayout(Context context, SetupFlowListenner lis) {
        super(context);
        populate(context);
        this.lis = lis;
    }

    private void populate(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.setup_choose_service, null);

        back = (Button)v.findViewById(R.id.BtnBack);
        next = (Button)v.findViewById(R.id.BtnNext);
        next.setText(R.string.Complete);

        back.setOnClickListener(mOnClickListenner);
        next.setOnClickListener(mOnClickListenner);

        this.addView(v);
    }

    private View.OnClickListener mOnClickListenner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.BtnBack) {
                lis.onBack(SetupServiceLayout.class.getName());
            } else {
                lis.onNext(SetupServiceLayout.class.getName());
            }
        }
    };
}
