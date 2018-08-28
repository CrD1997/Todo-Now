package com.example.macbook.umlproject.classes;

import android.graphics.Color;

import static android.graphics.Color.parseColor;

/**
 * Created by MacBook on 2018/8/22.
 */

public class Tag {

    public String name;

    public int color;

    public boolean ifUse;

    public Tag(){
        this.name = "default";
        this.color= parseColor("#65E1C3");
        this.ifUse=false;
    }

    public Tag(String name,int color,boolean ifUse) {
        this.name = name;
        this.color=color;
        this.ifUse=ifUse;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public boolean getIfUse(){
        return ifUse;
    }
}
