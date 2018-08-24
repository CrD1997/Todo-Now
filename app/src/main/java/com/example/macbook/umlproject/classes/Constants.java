package com.example.macbook.umlproject.classes;


public class Constants {
    public static final String TITLE_ADD_THING = "编辑事项";

    public static final String STATUS_OK = "OK";
    public static final String STATUS_CANCEL = "Cancel";

    public static final String DATABASE_LV = "database_lv";

    //表things，主要用于碎片1/3
    public static final String TABLE_THING = "things";
    public static final String THING_NAME = "thing_name";
    public static final String THING_DATE = "thing_date";
    public static final String THING_COLOR="thing_color";
    public static final String THING_TAG="thing_tag";
    public static final String THING_CLOCK_FINISHED="thing_clock_finished";
    public static final String THING_CLOCK_REMAINING="thing_clock_remaining";
    public static final String THING_IFDONE = "thing_ifdone";

    //表clock，主要用于碎片2
    public static final String TABLE_CLOCK="clock";
    public static final String CLOCK_DATE="clock_date";
    public static final String CLOCK_NAME="clock_name";
    public static final String CLOCK_BEGINTIME="clock_begintime";
    public static final String CLOCK_FINISHTIME="clock_finishtime";

    //表tags，主要用于碎片5
    public static final String TABLE_TAGS="tags";
    public static final String TAG_NAME="tag_name";
    public static final String TAG_IFUSE="tag_ifuse";
    public static final String TAG_COLOR="tag_color";

    //表day，主要用于碎片3/4
    public static final String TABLE_DAY="day";
    public static final String DAY_DATE="day_date";
    public static final String DAY_FINISH="day_finish_clock";
    public static final String DAY_GIVEPUP="day_giveup_clock";
    public static final String DAY_THINGNUM="day_thingnum";

    public static final String TABLE_SET="settings";
    public static final String SET_CLOCKTIME="set_clockTime";
    public static final String SET_NAME="set_name";


}

