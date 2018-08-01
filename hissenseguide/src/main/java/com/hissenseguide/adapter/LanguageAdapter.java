package com.hissenseguide.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hissenseguide.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by xuqunxing on 2017/7/19.
 */
public class LanguageAdapter extends LocaleAdapter {

    private static class ViewHolder {
        public ImageView flagView;
        public TextView textView;

        public ViewHolder(TextView tv, ImageView flagView) {
            this.textView = tv;
            this.flagView = flagView;
        }
    }

    public LanguageAdapter(Context context) {
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
        View convertView = this.mInflater.inflate(R.layout.language_list_item_hisense, parent, false);
        convertView.setTag(new ViewHolder((TextView) convertView.findViewById(R.id.language1), (ImageView) convertView.findViewById(R.id.flag_view)));
        return convertView;
    }

    public void bindView(int position, View convertView) {
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Locale locale = getItem(position);
        if(Locale.US == locale){
            viewHolder.textView.setText(mContext.getResources().getString(R.string.simple_english));
        }else if(Locale.SIMPLIFIED_CHINESE == locale){
            viewHolder.textView.setText(mContext.getResources().getString(R.string.simple_chinese));
        }
//        viewHolder.flagView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.btn_radio_on_holo_light));
        viewHolder.flagView.setBackgroundResource(R.drawable.btn_radio_on_holo_light);
        if (position == selectPosition) {
            viewHolder.textView.setTextColor(this.mContext.getResources().getColor(R.color.light_blue));
            viewHolder.flagView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.textView.setTextColor(this.mContext.getResources().getColor(R.color.black_100));
            viewHolder.flagView.setVisibility(View.INVISIBLE);
        }
    }

    public void setSelectPostion(int position) {
        this.selectPosition = position;
        refresh();
    }
}