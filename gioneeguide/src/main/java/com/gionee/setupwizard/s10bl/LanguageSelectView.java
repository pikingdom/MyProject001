package com.gionee.setupwizard.s10bl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/8/15.
 */
public class LanguageSelectView extends RelativeLayout implements View.OnClickListener {

    private ChangeViewInterface changeViewInterface;
    private ListView listView;
    private List<Locale> locales;
    private LanguageAdapter languageAdapter;
    private Button nextButton;

    public LanguageSelectView(Context context) {
        super(context);
        initView();
    }

    public LanguageSelectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public LanguageSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public LanguageSelectView(Context context, ChangeViewInterface changeViewInterface1) {
        super(context);
        this.changeViewInterface = changeViewInterface1;
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gn_sw_layout_language, this);
        nextButton = (Button) findViewById(R.id.gn_sw_layout_language_nextbutton);
        listView = (ListView) findViewById(R.id.gn_sw_layout_language_list);
        locales = new ArrayList<>();
        Locale locale1 = Locale.ENGLISH;
        Locale locale2 = Locale.CHINESE;
        locales.clear();
        locales.add(locale1);
        locales.add(locale2);
        languageAdapter = new LanguageAdapter(getContext(),locales);
        listView.setAdapter(languageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                languageAdapter.setDefaultPos(i);
            }
        });
        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == nextButton){
           // nextButton.setEnabled(false);
            if(changeViewInterface != null){
                changeViewInterface.setCurrentlyShowView(ChangeViewInterface.WIFI_VIEW);
            }
        }
    }

    private class LanguageAdapter extends BaseAdapter {
        private Context context;
        private List<Locale> locales;
        private int defaultCheckPos= 1;
        LanguageAdapter(Context context, List<Locale> locales1) {
            this.context = context;
            this.locales = locales1;
        }

        public int getCount() {
            return this.locales.size();
        }

        public Locale getItem(int position) {
            return this.locales.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public void setDefaultPos(int Pos){
            defaultCheckPos = Pos;
            notifyDataSetChanged();
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = newView();
            }
            final ViewHolder holder = (ViewHolder) view.getTag();
            Locale locale = getItem(position);
            if(Locale.ENGLISH == locale){
                holder.textView.setText("English(United States)");
                holder.radioButton.setBackgroundResource(R.drawable.gn_ridio_unselected);
            }else if(Locale.CHINESE == locale){
                holder.textView.setText("中文（中国）");
                holder.radioButton.setBackgroundResource(R.drawable.gn_ridio_selected);
            }
            if (defaultCheckPos == position) {
                holder.radioButton.setBackgroundResource(R.drawable.gn_ridio_selected);
            } else {
                holder.radioButton.setBackgroundResource(R.drawable.gn_ridio_unselected);
            }
            return view;
        }
    }
    private View newView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.gn_sw_layout_single_item, null);
        ViewHolder holder = new ViewHolder();
        holder.textView = (TextView) view.findViewById( R.id.gn_sw_layout_single_item_textView);
        holder.radioButton = (ImageView)view.findViewById( R.id.gn_sw_layout_single_item_RadioButton);
        view.setTag(holder);
        return view;
    }

    static class ViewHolder {
        public ImageView radioButton;
        public TextView textView;

        ViewHolder() {
        }
    }

}
