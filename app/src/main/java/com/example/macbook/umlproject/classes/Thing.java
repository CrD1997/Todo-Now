package com.example.macbook.umlproject.classes;


public class Thing {

    public String name;
    public String date;
    public String tag;
    public int color;
    public int finished;
    public int all;
    public boolean ifDone;

    public Thing(){
        this.name = "任务";
        this.date = "2018-1-01";
        this.tag="default";
        this.color=1;
        this.finished=0;
        this.all=5;
        this.ifDone=false;
    }

    public Thing(String name, String date,String tag,int color,int finished,int remaining,boolean ifDone) {
        this.name = name;
        this.date= date;
        this.tag=tag;
        this.color=color;
        this.finished=finished;
        this.all=remaining;
        this.ifDone=ifDone;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
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
