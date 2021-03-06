package com.zteguidedemo.v0840;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zteguidedemo.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/7/19.
 */

public class ZTE0840LanguageAdapter extends ZTE0840LocaleAdapter {

    private static class ViewHolder {
        public ImageView flagView;
        public TextView textView;

        public ViewHolder(TextView tv, ImageView flagView) {
            this.textView = tv;
            this.flagView = flagView;
        }
    }

    public ZTE0840LanguageAdapter(Context context) {
        super(context);
    }

    public void setDataSource(List<Locale> feeds) {
        this.mData.clear();
        this.mData.addAll(feeds);
        refresh();
    }

    public void setDefaultDataSource(Locale feed) {
        this.mData.clear();
        this.mData.add(feed);
        refresh();
    }

    public View newView(int position, ViewGroup parent) {
        View convertView = this.mInflater.inflate(R.layout.zte0840_language_list_item, parent, false);
        convertView.setTag(new ViewHolder((TextView) convertView.findViewById(R.id.language1), (ImageView) convertView.findViewById(R.id.flag_view)));
        return convertView;
    }

    public void bindView(int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Locale locale = getItem(position);
        if(Locale.US == locale){
            viewHolder.textView.setText("English");
        }else if(Locale.CHINESE == locale){
            viewHolder.textView.setText("中文");
        }
        if (position == selectPosition) {
            viewHolder.flagView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.flagView.setVisibility(View.GONE);
        }
    }

    public void setSelectPostion(int position) {
        this.selectPosition = position;
        refresh();
    }
}