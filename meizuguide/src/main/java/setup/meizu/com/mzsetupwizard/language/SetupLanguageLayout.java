package setup.meizu.com.mzsetupwizard.language;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import setup.meizu.com.mzsetupwizard.R;
import setup.meizu.com.mzsetupwizard.SetupFlowListenner;

/**
 * 设置语言界面
 * Created by linliangbin on 2017/7/18 11:25.
 */

public class SetupLanguageLayout extends RelativeLayout {
    private Button back, next;
    private CheckedTextView lastChecked;
    private SetupFlowListenner lis;

    public SetupLanguageLayout(Context context, SetupFlowListenner lis) {
        super(context);
        populate(context);
        this.lis = lis;
    }

    private void populate(Context ctx) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.setup_choose_language, null);

        back = (Button)v.findViewById(R.id.BtnBack);
        back.setText("");
        back.setClickable(false);
        next = (Button)v.findViewById(R.id.BtnNext);
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lis.onNext(SetupLanguageLayout.class.getName());
            }
        });

        ListView lv = (ListView)v.findViewById(R.id.mLanguageListView);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView now = (CheckedTextView) view;
                if (lastChecked != null) {
                    lastChecked.setChecked(false);
                }

                lastChecked = now;
                now.setChecked(true);
            }
        });
        lv.setAdapter(getAdapter(ctx));
        lv.setItemChecked(0, true);

        this.addView(v);
    }

    private ArrayAdapter<String> getAdapter(Context ctx) {
        List<String> list = new ArrayList<String>();
        list.add("简体中文");
        list.add("繁體中文");
        list.add("繁體中文（香港）");
        list.add("English");

        ArrayAdapter<String> aa = new ArrayAdapter<String>(ctx, R.layout.item_language, R.id.itemLanguage, list);
        return aa;
    }
}
