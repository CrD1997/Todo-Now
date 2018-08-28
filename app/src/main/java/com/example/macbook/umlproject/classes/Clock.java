package com.example.macbook.umlproject.classes;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MacBook on 2018/8/26.
 */

public class Clock implements Serializable {

    public String name;//事件名称
    public String date;//事件发生的日期，格式XX-XX-XX,如2018-06-01
    public String startTime; //事件开始时间   格式为XX:XX 如 23：00，
    public String totalTime; //事件用的总时间 注意单位是min
    public int color;//事件颜色

    public Clock(){
    }

    //Color先暂定
    public Clock(String name, String date, String startTime, String totalTime, int color){
        this.name=name;
        this.startTime=startTime;
        this.totalTime=totalTime;
        this.color=color;
        this.date=date;
    }

    @Override
    public String toString(){
        return "详细信息";
    }

    /* @return 返回值为一二三四五六日
    */

    //因为正则表达式写的很水，故请求输入一定要以xx：xx的格式，不然会错
    public int getStartHour(){//获取任务开始时间的整小时部分，如23：11，返回23
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(getStartTime());
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    public int getStartMin(){//获取任务开始时的分钟部分，如23：11，返回11
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(getStartTime());
        matcher.find();
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    public int getYear(){//获取事件发生的年
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(getClockDate());
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
    public int getMonth(){//获取月
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(getClockDate());
        matcher.find();
        matcher.find();
        return Integer.parseInt(matcher.group());
    }

    public int getDate(){//获取日期
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(getClockDate());
        matcher.find();
        matcher.find();
        matcher.find();
        return Integer.parseInt(matcher.group());
    }
    //以下为标准get，set函数
    public String getName() {
        return name;
    }

    public void setJobName(String jobName) {
        this.name = jobName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String jobStartTime) {
        this.startTime = jobStartTime;
    }

    public int getTotalTime() {
        return Integer.parseInt(totalTime);
    }

    public void setTotalTime(String jobTotalTime) {
        this.totalTime = jobTotalTime;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int jobColor) {
        this.color = jobColor;
    }

    public String getClockDate() {
        return date;
    }

    public void setClockDate(String jobDate) {
        this.date = jobDate;
    }
}
