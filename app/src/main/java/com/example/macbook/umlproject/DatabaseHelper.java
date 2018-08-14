package com.example.macbook.umlproject;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_THING, null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "
                + Constants.TABLE_THING
                + "(id integer primary key,"
                + Constants.THING_DATE
                + " varchar,"
                + Constants.THING_NAME
                + " varchar,"
                + Constants.THING_IFDONE
                + " varchar)");
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertThing(Thing thing) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.THING_DATE, thing.getDate());
        cv.put(Constants.THING_NAME, thing.getName());
        cv.put(Constants.THING_IFDONE, thing.getIfDone());
        database.insert(Constants.TABLE_THING, null, cv);
    }

    public Cursor getAllThingData() {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(Constants.TABLE_THING, null, null, null, null, null, Constants.THING_DATE + " ASC");
    }

    public void deleteAllData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_THING, null, null);
    }
}
