package com.example.macbook.umlproject.classes;

import java.util.Date;

public class CalendarCell {
    private int id;
    private int time;
    private Date date;
    private int tag;// 标签 0:上个月 ， 1:这个月 2:下个月
    private boolean isSelect;// 是否选中   今天
    private int count;// 这一天有几个任务

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}