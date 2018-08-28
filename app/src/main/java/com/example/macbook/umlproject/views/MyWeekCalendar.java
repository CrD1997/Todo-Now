package com.example.macbook.umlproject.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.loonggg.weekcalendar.view.WeekCalendar;

public class MyWeekCalendar extends WeekCalendar {
    private float mLastX = -1.0F;

    public MyWeekCalendar(Context context){
        super(context);
    }
    public MyWeekCalendar(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch(action) {
            case 0:
                this.mLastX = event.getRawX();
                break;
            case 2:
                float dx = this.mLastX - event.getRawX();
                if(dx > 80.0F) {
                    this.showNextView(true);
                    return true;
                }

                if(dx < -80.0F) {
                    this.showLastView(true);
                    return true;
                }
        }

        return super.onInterceptTouchEvent(event);
    }

}
