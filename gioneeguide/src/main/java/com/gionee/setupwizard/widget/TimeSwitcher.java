package com.gionee.setupwizard.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gionee.setupwizard.R;
import com.gionee.setupwizard.widget.data.AbstractWheelTextAdapter;
import com.gionee.setupwizard.widget.data.OnWheelChangedListener;
import com.gionee.setupwizard.widget.data.WheelView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 时间选择控件
 * 作者：xiaomao on 2017/7/15.
 */

public class TimeSwitcher extends LinearLayout implements OnWheelChangedListener{
    private Context context;
    private RefurbishTimeViewTime refurbishTimeViewTime;

    private TextView yearText;
    private ImageView yearSplit;
    private TextView hourText;
    private ImageView hourSplit;
    private ImageView timeSplit;
    private RelativeLayout yearTextLayout;
    private RelativeLayout hourTextLayout;
    private LinearLayout yearChooseLayout;
    private LinearLayout hourChooseLayout;
    private WheelView yearWheelView;
    private WheelView monthWheelView;
    private WheelView dayWheelView;
    private WheelView hourWheelView;
    private WheelView minuteWheelView;

    private ArrayList<String> arry_years = new ArrayList<String>();
    private ArrayList<String> arry_months = new ArrayList<String>();
    private ArrayList<String> arry_days = new ArrayList<String>();
    private ArrayList<String> arry_hour = new ArrayList<String>();
    private ArrayList<String> arry_minute = new ArrayList<String>();
    private CalendarTextAdapter mYearAdapter;
    private CalendarTextAdapter mMonthAdapter;
    private CalendarTextAdapter mDaydapter;
    private CalendarTextAdapter mHourdapter;
    private CalendarTextAdapter mMinutedapter;

    private boolean isEnable = false;
    public TimeSwitcher(Context context) {
        super(context);
        this.context = context;
        initView();
    }
    public TimeSwitcher(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }
    public TimeSwitcher(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView(){
        View mRoot = inflate(context, R.layout.gn_time_switcher_layout,null);
        addView(mRoot,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        yearText = (TextView) findViewById(R.id.switcher_year_text);
        yearSplit = (ImageView) findViewById(R.id.switcher_year_split);
        hourText = (TextView) findViewById(R.id.switcher_hour_text);
        hourSplit = (ImageView) findViewById(R.id.switcher_hour_split);
        timeSplit = (ImageView) findViewById(R.id.switcher_time_split);
        yearChooseLayout = (LinearLayout) findViewById(R.id.switcher_year_choose_layout);
        hourChooseLayout = (LinearLayout) findViewById(R.id.switcher_hour_choose_layout);
        yearWheelView = (WheelView) findViewById(R.id.switcher_year_choose_layout_year);
        monthWheelView = (WheelView) findViewById(R.id.switcher_year_choose_layout_month);
        dayWheelView = (WheelView) findViewById(R.id.switcher_year_choose_layout_day);
        hourWheelView = (WheelView) findViewById(R.id.switcher_year_choose_layout_hour);
        minuteWheelView = (WheelView) findViewById(R.id.switcher_year_choose_layout_minute);
        yearTextLayout = (RelativeLayout) findViewById(R.id.switcher_year_text_layout);
        hourTextLayout = (RelativeLayout) findViewById(R.id.switcher_hour_text_layout);
        //设置5个可见
        yearWheelView.setVisibleItems(5);
        monthWheelView.setVisibleItems(5);
        dayWheelView.setVisibleItems(5);
        hourWheelView.setVisibleItems(5);
        minuteWheelView.setVisibleItems(5);
        initData();
        //设置适配器
        Calendar c = Calendar.getInstance();
        mYearAdapter = new CalendarTextAdapter(context, arry_years, 2037 - c.get(Calendar.YEAR), 24, 14,yearWheelView);
        yearWheelView.setViewAdapter(mYearAdapter);
        yearWheelView.setCurrentItem(2037 - c.get(Calendar.YEAR));
        mMonthAdapter = new CalendarTextAdapter(context, arry_months, c.get(Calendar.MONTH), 24, 14,monthWheelView);
        monthWheelView.setViewAdapter(mMonthAdapter);
        monthWheelView.setCurrentItem(c.get(Calendar.MONTH));
        mDaydapter = new CalendarTextAdapter(context, arry_days, c.get(Calendar.DATE)-1, 24, 14,dayWheelView);
        dayWheelView.setViewAdapter(mDaydapter);
        dayWheelView.setCurrentItem(c.get(Calendar.DATE)-1);
        mHourdapter = new CalendarTextAdapter(context, arry_hour, c.get(Calendar.HOUR), 24, 14,hourWheelView);
        hourWheelView.setViewAdapter(mHourdapter);
        hourWheelView.setCurrentItem(c.get(Calendar.HOUR));
        mMinutedapter = new CalendarTextAdapter(context, arry_minute, c.get(Calendar.MINUTE), 24, 14,minuteWheelView);
        minuteWheelView.setViewAdapter(mMinutedapter);
        minuteWheelView.setCurrentItem(c.get(Calendar.MINUTE));
        initListener();
        setOnEnableColor(false);
    }

    private void initListener(){
        yearTextLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                yearChooseLayout.setVisibility(View.VISIBLE);
                hourChooseLayout.setVisibility(View.GONE);
                yearSplit.setVisibility(View.VISIBLE);
                hourSplit.setVisibility(View.GONE);
            }
        });
        hourTextLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                yearChooseLayout.setVisibility(View.GONE);
                hourChooseLayout.setVisibility(View.VISIBLE);
                yearSplit.setVisibility(View.GONE);
                hourSplit.setVisibility(View.VISIBLE);
            }
        });
        yearWheelView.addChangingListener(this);
        monthWheelView.addChangingListener(this);
        dayWheelView.addChangingListener(this);
        hourWheelView.addChangingListener(this);
        minuteWheelView.addChangingListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        for (int i = 2037; i >= 1970; i--) {
            arry_years.add(i + "");
        }
        for (int i = 1; i <= 12; i++) {
            arry_months.add(i + "");
        }
        for (int i = 0; i < 24; i++) {
            if (i<10) {
                arry_hour.add("0"+i + "");
            }else{
                arry_hour.add(i + "");
            }
        }
        for (int i = 0; i < 60; i++) {
            if (i<10) {
                arry_minute.add("0"+i + "");
            }else{
                arry_minute.add(i + "");
            }
        }
        initDays(calDays(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        yearText.setText(formatter.format(Calendar.getInstance().getTimeInMillis()));
        formatter = new SimpleDateFormat("HH:mm");
        hourText.setText(formatter.format(Calendar.getInstance().getTimeInMillis()));
    }

    /**
     * 天要单独计算，每月的天数是不一样的
     * @param days
     */
    public void initDays(int days) {
        arry_days.clear();
        for (int i = 1; i <= days; i++) {
            arry_days.add(i + "");
        }
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        refurbishColor(wheel);
    }

    private void refurbishColor(WheelView wheel){
        String year = (String) mYearAdapter.getItemText(yearWheelView.getCurrentItem());
        String month = (String) mMonthAdapter.getItemText(monthWheelView.getCurrentItem());
        String day = (String) mDaydapter.getItemText(dayWheelView.getCurrentItem());
        String hour = (String) mHourdapter.getItemText(hourWheelView.getCurrentItem());
        String minute = (String) mMinutedapter.getItemText(minuteWheelView.getCurrentItem());
        int i = wheel.getId();
        if (i == R.id.switcher_year_choose_layout_year) {
            setTextviewSize(year, mYearAdapter);
            initDays(calDays(Integer.parseInt(year), Integer.parseInt(month)));
            mDaydapter = new CalendarTextAdapter(context, arry_days, 0, 24, 14, dayWheelView);
            dayWheelView.setVisibleItems(5);
            dayWheelView.setViewAdapter(mDaydapter);
            dayWheelView.setCurrentItem(0);
        } else if (i == R.id.switcher_year_choose_layout_month) {
            setTextviewSize(month, mMonthAdapter);
            initDays(calDays(Integer.parseInt(year), Integer.parseInt(month)));
            mDaydapter = new CalendarTextAdapter(context, arry_days, 0, 24, 14, dayWheelView);
            dayWheelView.setVisibleItems(5);
            dayWheelView.setViewAdapter(mDaydapter);
            dayWheelView.setCurrentItem(0);
        } else if (i == R.id.switcher_year_choose_layout_day) {
            setTextviewSize(day, mDaydapter);
        } else if (i == R.id.switcher_year_choose_layout_hour) {
            setTextviewSize(hour, mHourdapter);
        } else if (i == R.id.switcher_year_choose_layout_minute) {
            setTextviewSize(minute, mMinutedapter);
        }
        //将读出来的时间布局修改所有时间界面
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(year));
        calendar.set(Calendar.MONTH,Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE,Integer.parseInt(minute));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        yearText.setText(formatter.format(calendar.getTimeInMillis()));
        formatter = new SimpleDateFormat("HH:mm");
        hourText.setText(formatter.format(calendar.getTimeInMillis()));
        if (refurbishTimeViewTime != null){
            refurbishTimeViewTime.refurbishTime(calendar);
        }
    }

    private class CalendarTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;
        WheelView wheelView;
        protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize, WheelView wheelView) {
            super(context, R.layout.gn_item_data_time_switcher, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            this.wheelView = wheelView;
            setItemTextResource(R.id.tempValue);
        }
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            if (wheelView.getCurrentItem() == index){
                ((TextView)view.findViewById(R.id.tempValue)).setTextSize(24);
                ((TextView)view.findViewById(R.id.tempValue)).setTextColor(Color.parseColor("#ffff9000"));
                if (!isEnable){
                    ((TextView)view.findViewById(R.id.tempValue)).setTextColor(Color.parseColor("#ffc8c8c8"));
                }
            }else{
                ((TextView)view.findViewById(R.id.tempValue)).setTextSize(12);
                ((TextView)view.findViewById(R.id.tempValue)).setTextColor(Color.parseColor("#ffc8c8c8"));
            }
            return view;
        }
        @Override
        public int getItemsCount() {
            return list.size();
        }
        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 计算每月多少天
     *
     * @param month
     */
    public int calDays(int year, int month) {
        boolean leayyear = false;
        if (year % 4 == 0 && year % 100 != 0) {
            leayyear = true;
        } else {
            leayyear = false;
        }
        int result = 30;
        for (int i = 1; i <= 12; i++) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    result = 31;
                    break;
                case 2:
                    if (leayyear) {
                        result = 29;
                    } else {
                        result = 28;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    result = 30;
                break;
            }
        }
        return result;
    }
    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
                textvew.setTextColor(Color.parseColor("#ffff9000"));
                if (!this.isEnable){
                    textvew.setTextColor(Color.parseColor("#ffc8c8c8"));
                }
            } else {
                textvew.setTextSize(14);
                textvew.setTextColor(Color.parseColor("#ffc8c8c8"));
            }
        }
    }

    /**
     * 刷新时间空间接口
     */
    public interface RefurbishTimeViewTime{
        public void refurbishTime(Calendar calendar);
    }
    public void setRefurbishTimeViewTime(RefurbishTimeViewTime refurbishTimeViewTime){
        this.refurbishTimeViewTime = refurbishTimeViewTime;
    }

    /**
     * 修改界面颜色
     */
    public void setOnEnableColor(boolean isEnable){
        int color = Color.parseColor("#ffff9000");
        if (!isEnable){
            color = Color.parseColor("#ffc8c8c8");
        }
        this.isEnable = isEnable;
        yearText.setTextColor(color);
        hourText.setTextColor(color);
        yearSplit.setBackgroundColor(color);
        hourSplit.setBackgroundColor(color);
        timeSplit.setBackgroundColor(color);
        yearWheelView.requestLayout();
        monthWheelView.requestLayout();
        dayWheelView.requestLayout();
        hourWheelView.requestLayout();
        minuteWheelView.requestLayout();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isEnable){
            return super.dispatchTouchEvent(ev);
        }else{
            return false;
        }
    }
}
