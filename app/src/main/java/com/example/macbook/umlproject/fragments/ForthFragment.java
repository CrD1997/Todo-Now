package com.example.macbook.umlproject.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.don.pieviewlibrary.LinePieView;
import com.don.pieviewlibrary.PercentPieView;
import com.example.macbook.umlproject.R;
import com.example.macbook.umlproject.views.LineGraphicView;

import static com.example.macbook.umlproject.activitys.MainActivity.mDatabaseHelper;

public class ForthFragment extends Fragment {

    ArrayList<Double> yList_finish;
    ArrayList<Double> yList_giveup;
    LineGraphicView finishView;
    LineGraphicView giveupView;
    PercentPieView pieView;
    public static TextView finishNum;
    public static TextView giveupNum;
    int nums=0;
    String today;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_forth,container,false);
        //initData();

        //完成/放弃折线图
        finishView = (LineGraphicView) view.findViewById(R.id.line_graphic_finish);
        giveupView = (LineGraphicView) view.findViewById(R.id.line_graphic_giveup);
        initLineView();

        //最近七天完成/放弃比重
        pieView = (PercentPieView) view.findViewById(R.id.pieView);
        initPieView();

        //今日完成/放弃时钟数
        Time t=new Time(); t.setToNow(); // 取得系统时间
        int year = t.year;int month = t.month+1;int day = t.monthDay;
        today=year+"-"+month+"-"+day;
        //System.out.println(today);
        finishNum=(TextView)view.findViewById(R.id.finish_num);
        giveupNum=(TextView)view.findViewById(R.id.giveup_num);
        finishNum.setText(String.valueOf(mDatabaseHelper.getFinishClock(today)));
        giveupNum.setText(String.valueOf(mDatabaseHelper.getGiveupClock(today)));
        //view.invalidate();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isGetData = false;
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            finishNum.setText(String.valueOf(mDatabaseHelper.getFinishClock(today)));
            giveupNum.setText(String.valueOf(mDatabaseHelper.getGiveupClock(today)));
            finishNum.invalidate();
            giveupNum.invalidate();
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            finishNum.setText(String.valueOf(mDatabaseHelper.getFinishClock(today)));
            giveupNum.setText(String.valueOf(mDatabaseHelper.getGiveupClock(today)));
            finishNum.invalidate();
            giveupNum.invalidate();
            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        // TODO Auto-generated method stub
//        super.onHiddenChanged(hidden);
//        if (hidden) {
//            finishNum.setText(String.valueOf(mDatabaseHelper.getFinishClock(today)));
//            giveupNum.setText(String.valueOf(mDatabaseHelper.getGiveupClock(today)));
//        }
//    }


    public void initLineView(){
        ArrayList<String> xRawDatas = new ArrayList<String>();
        yList_finish=new ArrayList<Double>();
        yList_giveup=new ArrayList<Double>();
        for(int i=-20;i<0;i++){
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, i);
            String date = new SimpleDateFormat("yyyy-M-d").format(now.getTime());
            String date2=new SimpleDateFormat("M-d").format(now.getTime());
            if(i==-1||i==-2){
                yList_finish.add(0.0);
                yList_giveup.add(0.0);
                xRawDatas.add(date2);
            }else{
                yList_finish.add(new Double(mDatabaseHelper.getFinishClock(date)));
                yList_giveup.add(new Double(mDatabaseHelper.getGiveupClock(date)));
                xRawDatas.add(date2);
            }
            System.out.println("Today is "+date2+":"+mDatabaseHelper.getFinishClock(date)+" "+mDatabaseHelper.getGiveupClock(date));
        }
        int max1 = Collections.max(yList_finish).intValue();
        int max2 = Collections.max(yList_giveup).intValue();
        finishView.setData(yList_finish, xRawDatas, max1, 1);
        giveupView.setData(yList_giveup,xRawDatas,max2,1);
    }

    public void initPieView(){
        int[] data = new int[]{0,0};
        for(int i=-7;i<0;i++){
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, i);
            String date = new SimpleDateFormat("yyyy-M-d").format(now.getTime());
            data[0]+=mDatabaseHelper.getFinishClock(date);
            data[1]+=mDatabaseHelper.getGiveupClock(date);
        }
        String[] name = new String[]{"完成","放弃"};
        int[] color = new int[]{
                getResources().getColor(R.color.colorPrimary),
                Color.parseColor("#CCCCCC")};
        pieView.setData(data, name, color);
    }


}


