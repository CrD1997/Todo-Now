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
        //Thing表，记录备忘事项，Fragment1使用
        db.execSQL("create table if not exists "
                + Constants.TABLE_THING
                + "(id integer primary key,"
                + Constants.THING_DATE
                + " varchar,"
                + Constants.THING_NAME
                + " varchar,"
                + Constants.THING_IFDONE
                + " varchar)");

        //Clock表，记录完成与放弃时钟数，Fragment3、4使用
        db.execSQL("create table if not exists "
                + Constants.TABLE_CLOCK
                + "("
                + Constants.CLOCK_DATE
                + " varchar primary key,"
                + Constants.CLOCK_FINISH
                + " integer,"
                + Constants.CLOCK_GIVEPUP
                + " integer)");

    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //对Thing表的操作
    public void insertThing(Thing thing) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.THING_DATE, thing.getDate());
        cv.put(Constants.THING_NAME, thing.getName());
        cv.put(Constants.THING_IFDONE, thing.getIfDone());
        database.insert(Constants.TABLE_THING, null, cv);
    }

    public void updateThing(Thing thing){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.THING_DATE, thing.getDate());
        cv.put(Constants.THING_NAME, thing.getName());
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

    //对Clock表的操作
    public boolean searchClock(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_CLOCK, new String[]{Constants.CLOCK_DATE}, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.CLOCK_DATE)))){
                    return true;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public int getFinishClock(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_CLOCK, null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.CLOCK_DATE)))){
                    return cursor.getInt(cursor.getColumnIndex(Constants.CLOCK_FINISH));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    public int getGiveupClock(String date){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor=database.query(Constants.TABLE_CLOCK, null, null, null, null, null,null);
        if(cursor.moveToFirst()){
            do{
                if(date.equals(cursor.getString(cursor.getColumnIndex(Constants.CLOCK_DATE)))){
                    return cursor.getInt(cursor.getColumnIndex(Constants.CLOCK_GIVEPUP));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    public void insertClock(String date,int finishNum,int giveupNum) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.CLOCK_DATE, date);
        cv.put(Constants.CLOCK_FINISH,finishNum);
        cv.put(Constants.CLOCK_GIVEPUP,giveupNum);
        database.insert(Constants.TABLE_CLOCK, null, cv);
    }

    public void updateClock(String date,int finishNum,int giveupNum){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.CLOCK_DATE, date);
        cv.put(Constants.CLOCK_FINISH,finishNum);
        cv.put(Constants.CLOCK_GIVEPUP,giveupNum);
        database.update(Constants.TABLE_CLOCK,cv,"clock_date=?",new String[]{date});
    }

    public void deleteClock(String date,int finishNum,int giveupNum){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_CLOCK,"clock_date=?",new String[]{date});
    }

    public Cursor getAllClockData() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(Constants.TABLE_CLOCK, null, null, null, null, null, Constants.CLOCK_DATE + " ASC");
    }

    public void deleteAllClockData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_CLOCK, null, null);
    }
}
