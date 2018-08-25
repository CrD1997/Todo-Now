package com.example.macbook.umlproject.classes;


public class Thing {

    public String name;
    public String date;
    public String finishDate;
    public String tag;
    public int color;
    public int finished;
    public int all;
    public boolean ifDone;

    public Thing(){
        this.name = "任务";
        this.date = "2018-1-01";
        this.finishDate="2000-1-01";
        this.tag="default";
        this.color=1;
        this.finished=0;
        this.all=5;
        this.ifDone=false;
    }

    public Thing(String name, String date,String finishDate,String tag,int color,int finished,int all,boolean ifDone) {
        this.name = name;
        this.date= date;
        this.finishDate=finishDate;
        this.tag=tag;
        this.color=color;
        this.finished=finished;
        this.all=all;
        this.ifDone=ifDone;
    }

    public Thing(Thing mThing){
        this.name = mThing.getName();
        this.date= mThing.getDate();
        this.finishDate=mThing.getFinishDate();
        this.tag=mThing.getTag();
        this.color=mThing.getColor();
        this.finished=mThing.getFinished();
        this.all=mThing.getAll();
        this.ifDone=mThing.getIfDone();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFinishDate(){
        return finishDate;
    }

    public boolean getIfDone(){
        return ifDone;
    }

    public String getTag() {
        return tag;
    }

    public int getColor() {
        return color;
    }

    public int getFinished() {
        return finished;
    }

    public int getAll() {
        return all;
    }

    public boolean isIfDone() {
        return ifDone;
    }


}
