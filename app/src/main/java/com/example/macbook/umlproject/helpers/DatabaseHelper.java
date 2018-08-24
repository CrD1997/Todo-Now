package com.example.macbook.umlproject.helpers;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import com.example.macbook.umlproject.classes.Constants;
        import com.example.macbook.umlproject.classes.Thing;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_LV, null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        //Thing表
        db.execSQL("create table if not exists "
                + Constants.TABLE_THING
                + "(id integer primary key,"
                + Constants.THING_DATE
                + " varchar,"
                + Constants.THING_NAME
                + " varchar,"
                + Constants.THING_TAG
                + " varchar,"
                + Constants.THING_COLOR
                + " integer,"
                + Constants.THING_CLOCK_FINISHED
                + " integer,"
                + Constants.THING_CLOCK_REMAINING
                + " integer,"
                + Constants.THING_IFDONE
                + " varchar)");

        //Clock表
        db.execSQL("create table if not exists "
                + Constants.TABLE_CLOCK
                + "(id integer primary key,"
                + Constants.CLOCK_DATE
                + " varchar,"
                + Constants.CLOCK_NAME
                + " varchar,"
                + Constants.CLOCK_BEGINTIME
                + " varchar,"
                + Constants.CLOCK_FINISHTIME
                + " varchar)");

        //Tag表
        db.execSQL("create table if not exists "
                + Constants.TABLE_TAGS
                + "(id integer primary key,"
                + Constants.TAG_NAME
                + " varchar,"
                + Constants.TAG_COLOR
                + " integer,"
                + Constants.TAG_IFUSE
                + " varchar)");

        //Day表
        db.execSQL("create table if not exists "
                + Constants.TABLE_DAY
                + "(id integer primary key,"
                + Constants.DAY_DATE
                + " varchar,"
                + Constants.DAY_FINISH
                + " integer,"
                + Constants.DAY_GIVEPUP
                + " integer,"
                + Constants.DAY_THINGNUM
                + " integer)");

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ///////////////////////////////对Thing表的操作//////////////////////////////////////////////////
    public void insertThing(Thing thing) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.THING_DATE, thing.getDate());
        cv.put(Constants.THING_NAME, thing.getName());
        cv.put(Constants.THING_TAG,thing.getTag());
        cv.put(Constants.THING_COLOR,thing.getColor());
        cv.put(Constants.THING_CLOCK_FINISHED,thing.getFinished());
        cv.put(Constants.THING_CLOCK_REMAINING,thing.getAll());
        cv.put(Constants.THING_IFDONE, thing.getIfDone());
        database.insert(Constants.TABLE_THING, null, cv);
    }
    public void updateThing(Thing thing){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.THING_DATE, thing.getDate());
        cv.put(Constants.THING_NAME, thing.getName());
        cv.put(Constants.THING_TAG,thing.getTag());
        cv.put(Constants.THING_COLOR,thing.getColor());
        cv.put(Constants.THING_CLOCK_FINISHED,thing.getFinished());
        cv.put(Constants.THING_CLOCK_REMAINING,thing.getAll());
        cv.put(Constants.THING_IFDONE, thing.getIfDone());
        database.update(Constants.TABLE_THING,cv,"thing_name=?",new String[]{thing.getName()});
    }
    public void deleteThing(Thing thing){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_THING,"thing_name=?",new String[]{thing.getName()});
    }
    public Cursor getAllThingData() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(Constants.TABLE_THING, null, null, null, null, null, Constants.THING_DATE + " ASC");
    }
    public void deleteAllThingData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_THING, null, null);
    }

    ///////////////////////////////对Clock表的操作//////////////////////////////////////////////////
    public boolean searchDay(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_DAY, new String[]{Constants.DAY_DATE}, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.DAY_DATE)))){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return false;
    }
    public int getFinishClock(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_DAY, null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.DAY_DATE)))){
                    return cursor.getInt(cursor.getColumnIndex(Constants.DAY_FINISH));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }
    public int getGiveupClock(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_DAY, null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.DAY_DATE)))){
                    return cursor.getInt(cursor.getColumnIndex(Constants.DAY_GIVEPUP));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }
    public int getThingNum(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_DAY, null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.DAY_DATE)))){
                    return cursor.getInt(cursor.getColumnIndex(Constants.DAY_THINGNUM));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }
    public void insertDay(String date,int finishNum,int giveupNum,int thingNum) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.DAY_DATE, date);
        cv.put(Constants.DAY_FINISH,finishNum);
        cv.put(Constants.DAY_GIVEPUP,giveupNum);
        cv.put(Constants.DAY_THINGNUM,thingNum);
        database.insert(Constants.TABLE_DAY, null, cv);
        System.out.println("Insert into Table Day"+date+" "+finishNum+" "+giveupNum+""+thingNum);
    }
    public void updateDay(String date,int finishNum,int giveupNum,int thingNum){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.DAY_DATE, date);
        cv.put(Constants.DAY_FINISH,finishNum);
        cv.put(Constants.DAY_GIVEPUP,giveupNum);
        cv.put(Constants.DAY_THINGNUM,thingNum);
        database.update(Constants.TABLE_DAY,cv,"day_date=?",new String[]{date});
        System.out.println("Update Table Day"+date+" "+finishNum+" "+giveupNum);
    }
//    public void deleteClock(String date,int finishNum,int giveupNum){
//        SQLiteDatabase database = getWritableDatabase();
//        database.delete(Constants.TABLE_CLOCKS,"clocks_date=?",new String[]{date});
//    }
//
//    public Cursor getAllClockData() {
//        SQLiteDatabase database = getWritableDatabase();
//        return database.query(Constants.TABLE_CLOCKS, null, null, null, null, null, Constants.CLOCKS_DATE + " ASC");
//    }
//
//    public void deleteAllClockData() {
//        SQLiteDatabase database = getWritableDatabase();
//        database.delete(Constants.TABLE_CLOCKS, null, null);
//    }


}
