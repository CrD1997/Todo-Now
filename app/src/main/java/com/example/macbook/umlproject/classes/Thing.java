package com.example.macbook.umlproject.classes;


public class Thing {

    public String name;

    public String date;

    public boolean ifDone;

    public Thing(){
        this.name = "default";
        this.date = "2018-01-01";
        this.ifDone=false;
    }

    public Thing(String name, String date,boolean ifDone) {
        this.name = name;
        this.date= date;
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

}
