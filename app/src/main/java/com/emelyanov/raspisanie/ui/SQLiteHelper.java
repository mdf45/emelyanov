package com.emelyanov.raspisanie.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "MyDatabase";
    public static final String TABLE_NAME = "MyTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "date";
    public static final String KEY_NAMES = "names";
    public static final String KEY_TIMES = "times";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " " +
                        "INTEGER primary key autoincrement, " + KEY_DATE + " TEXT NOT NULL, " + KEY_NAMES + " TEXT NOT NULL, " + KEY_TIMES + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDay(String date, String names, String times) {
        if (readAllDataWhere(date).getCount() > 0) return;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_DATE, date);
        cv.put(KEY_NAMES, names);
        cv.put(KEY_TIMES, times);

        db.insert(TABLE_NAME, null, cv);
    }

    public void updateData(String date, String names, String times) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMES, names);
        cv.put(KEY_TIMES, times);

        db.update(TABLE_NAME, cv, KEY_DATE + "=?", new String[]{date});
    }

    public void deleteData(String date) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_NAME, KEY_DATE + "=?", new String[]{date});
    }

    public Cursor readAllData() {
        String query = String.format("SELECT * FROM %s;", TABLE_NAME);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public Cursor readAllDataWhere(String date) {
        String query = String.format("SELECT * FROM %s WHERE %s = '%s';", TABLE_NAME, KEY_DATE, date);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
}
