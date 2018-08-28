package com.example.macbook.umlproject.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.umlproject.classes.Clock;
import com.example.macbook.umlproject.views.MyWeekCalendar;
import com.loonggg.weekcalendar.view.WeekCalendar;

import java.util.ArrayList;
import java.util.Calendar;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.macbook.umlproject.R;

import static com.example.macbook.umlproject.activitys.MainActivity.mDatabaseHelper;

public class SecondFragment extends Fragment {

    View view;
    MyWeekCalendar weekCalendar;//自定义日历控件
    // 时间线的高度
    private float mLeftHeight;
    // 时间线的宽度
    private float mLeftWidth;
    private LinearLayout mLeftNo;
    //以下为完成任务列表
    public static ArrayList<Clock> mClock;
    //先默认为从7点显示到24点
    private int MinHour=7;
    private int MaxHour=24;
    private CoordinatorLayout Monday;
    //private List<String> list = new ArrayList<>();
    int selectYear;
    int selectMonth;
    int selectDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view=inflater.inflate(R.layout.fragment_second,container,false);
        initCtrl();
        //得到数据
        initClocks();
        // 绘制左边的时间线
        drawLeftNo();
        // 绘制当前周
        //drawAllJob();
        //drawJob(jobMonday,"2018-8-24");
        Calendar calendar = Calendar.getInstance();
        selectYear= calendar.get(Calendar.YEAR);
        selectMonth= calendar.get(Calendar.MONTH)+1;
        selectDate= calendar.get(Calendar.DAY_OF_MONTH);
        //初始化时绘制当天任务
        System.out.println(selectYear+"-"+selectMonth+"-"+selectDate);
        drawClock(Monday,selectYear+"-"+selectMonth+"-"+selectDate);
        weekCalendar = (MyWeekCalendar) view.findViewById(R.id.week_calendar);
        //传入已经预约或者曾经要展示选中的时间列表
        //weekCalendar.setSelectDates(list);
        //设置日历点击事件
        weekCalendar.setOnDateClickListener(new WeekCalendar.OnDateClickListener() {
            @Override
            public void onDateClick(String time) {
                Toast.makeText(view.getContext(), time, Toast.LENGTH_SHORT).show();
                drawClock(Monday,time);
            }
        });
        //设置年月时间的回调
        weekCalendar.setOnCurrentMonthDateListener(new WeekCalendar.OnCurrentMonthDateListener() {
            @Override
            public void onCallbackMonthDate(String year, String month) {
                Toast.makeText(view.getContext(), year + "-" + month, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initCtrl() {
        mLeftNo = (LinearLayout) view.findViewById(R.id.leftNo);
        Monday = (CoordinatorLayout) view.findViewById(R.id.monday);
    }

    private void InitData(){
        //测试用数据
        Clock A=new Clock("学习","2018-8-25","16:30","90",Color.parseColor("#008080"));
        Clock B=new Clock("运动","2018-8-24","13:30","42",Color.parseColor("#008080"));
        Clock C=new Clock("娱乐","2018-8-26","7:29","90",Color.parseColor("#008080"));
        Clock E=new Clock("娱乐","2018-8-26","9:29","90",Color.parseColor("#008080"));
        Clock G=new Clock("娱乐","2018-8-26","16:29","90",Color.parseColor("#008080"));
        Clock F=new Clock("娱乐","2018-8-25","16:29","90",Color.parseColor("#008080"));
        mClock=new ArrayList<Clock>();
        mClock.add(A);
        mClock.add(B);
        mClock.add(C);
        mClock.add(E);
        mClock.add(G);
    }

    private void initClocks(){
        //mDatabaseHelper.deleteAllData();
        mClock=new ArrayList<Clock>();
        Cursor cursor=mDatabaseHelper.getAllClockData();
        if(cursor!=null){
            while(cursor.moveToNext()){
                Clock clock= new Clock();
                clock.date= cursor.getString(cursor.getColumnIndex("clock_date"));
                clock.name = cursor.getString(cursor.getColumnIndex("clock_name"));
                clock.totalTime=cursor.getString(cursor.getColumnIndex("clock_totaltime"));
                clock.startTime=cursor.getString(cursor.getColumnIndex("clock_begintime"));
                clock.color=cursor.getInt(cursor.getColumnIndex("clock_color"));
                mClock.add(clock);
            }
            cursor.close();
        }
    }

    /**
     * 绘制左边的时间条
     */
    private void drawLeftNo() {
        mLeftHeight = getResources().getDimension(R.dimen.left_height);
        mLeftWidth = getResources().getDimension(R.dimen.left_width);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                (int) mLeftWidth, (int) mLeftHeight);
        for (int i = MinHour; i <= MaxHour; i++) {
            TextView tv = new TextView(view.getContext());
            if(i<10)
            {tv.setText(" 0"+i + ":00");
            }else{
                tv.setText(" "+i + ":00");
            }
            tv.setGravity(Gravity.TOP|Gravity.CENTER);
            tv.setTextSize(13);
            tv.setPadding(0,0,0,1);
            tv.setBackgroundColor(Color.parseColor("#F9F6EF"));//背景颜色
            mLeftNo.addView(tv, lp);
        }
    }

    /**
     * 绘制任务列表
     */
    @SuppressLint("NewApi")
    private void drawClock(CoordinatorLayout ll, String iselectDate) {
        // 删除所有子View
        ll.removeAllViews();
        selectYear=getSelectYear(iselectDate);
        selectMonth=getSelectMonth(iselectDate);
        selectDate=getSelectDate(iselectDate);
        System.out.println(selectYear+"-"+selectMonth+"-"+selectDate);

        for (Clock clock:mClock) {
            // 判断是否显示这个任务,即这任务是不是这一天的
            if (clock.getDate()!=selectDate||clock.getMonth()!=selectMonth||clock.getYear()!=selectYear)
                continue;
            // 设置TextView的属性样式
            TextView tv = new TextView(view.getContext());
            tv.setText(clock.getName());
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(clock.getColor());
            tv.setTextColor(getResources().getColor(R.color.course_font_color));
            // 将数据绑定到TextView上，则点击可查看详情
            tv.setTag(clock);
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    JobBean tag = (JobBean) v.getTag();
//                    showJobDetails(tag);
//                }
//            });
            // 设置TextView的位置
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (clock.getTotalTime() * mLeftHeight/60));
            lp.setMargins(1, (int) ((clock.getStartHour()+(float)clock.getStartMin()/60-MinHour) * mLeftHeight), 1, 0);
            ll.addView(tv, lp);
        }
    }



    public int getSelectYear(String date){//获取年
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(date);
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
    public int getSelectMonth(String date){//获取月
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(date);
        matcher.find();
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
    public int getSelectDate(String date){//获取日
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(date);
        matcher.find();
        matcher.find();
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
}



