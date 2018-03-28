package com.ctse.automatedbirthdaywisher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madupoorna on 3/28/2018.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "autobdaywisher.db";

    private static final int DATABASE_VERSION = 1;

    private static final String BDAY_TABLE = "bday_table";

    private static final String ID_COL = "id";
    private static final String NUMBER_COL = "number";
    private static final String TIME_COL = "time";
    private static final String DATE_COL = "date";
    private static final String MSG_COL = "msg";
    private static final String FLAG_COL = "flag";
    private static final String NAME_COL = "name";
    private static final String IMAGE_COL = "photo";

    private static final String CREATE_TABLE_BDAY = "CREATE TABLE " + BDAY_TABLE + "("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME_COL + " TEXT,"
            + NUMBER_COL + " TEXT,"
            + TIME_COL + " TEXT,"
            + DATE_COL + " TEXT,"
            + MSG_COL + " TEXT,"
            + IMAGE_COL + " BLOB,"
            + FLAG_COL + " TEXT" + ")";

    public MyDBHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BDAY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BDAY_TABLE);
        onCreate(db);
    }

    public void insertWish(String number, String time, String date, String msg, String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, name);
        values.put(NUMBER_COL, number);
        values.put(TIME_COL, time);
        values.put(DATE_COL, date);
        values.put(MSG_COL, msg);
        values.put(IMAGE_COL, image);

        db.insert(BDAY_TABLE, null, values);
        db.close();
    }

    public List<DbData> getAllWishes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + BDAY_TABLE + ";", null);
        cur.moveToNext();

        int idIdx = cur.getColumnIndex(ID_COL);
        int numberIdx = cur.getColumnIndex(NUMBER_COL);
        int timeIdx = cur.getColumnIndex(TIME_COL);
        int dateIdx = cur.getColumnIndex(DATE_COL);
        int msgIdx = cur.getColumnIndex(DATE_COL);
        int nameIdx = cur.getColumnIndex(NAME_COL);
        int imgIdx = cur.getColumnIndex(IMAGE_COL);

        List<DbData> data = new ArrayList<>();
        while (!cur.isAfterLast()) {
            int id = cur.getInt(idIdx);
            String number = cur.getString(numberIdx);
            String date = cur.getString(timeIdx);
            String time = cur.getString(dateIdx);
            String msg = cur.getString(msgIdx);
            String name = cur.getString(nameIdx);
            byte[] photo = cur.getBlob(imgIdx);

            data.add(new DbData(id, photo, number, date, time, msg, name));
            cur.moveToNext();
        }
        db.close();
        return data;
    }

    public DbData getDetailsById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + BDAY_TABLE + " WHERE " + ID_COL + " = " + id + ";", null);
        cur.moveToNext();
        DbData data = null;

        int numberIdx = cur.getColumnIndex(NUMBER_COL);
        int timeIdx = cur.getColumnIndex(TIME_COL);
        int dateIdx = cur.getColumnIndex(DATE_COL);
        int msgIdx = cur.getColumnIndex(MSG_COL);
        int nameIdx = cur.getColumnIndex(NAME_COL);
        int imgIdx = cur.getColumnIndex(IMAGE_COL);

        String number = cur.getString(numberIdx);
        String date = cur.getString(timeIdx);
        String time = cur.getString(dateIdx);
        String msg = cur.getString(msgIdx);
        String name = cur.getString(nameIdx);
        byte[] photo = cur.getBlob(imgIdx);

        data = new DbData(id, photo, number, date, time, msg, name);

        db.close();
        return data;
    }

    public boolean deleteWish(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BDAY_TABLE, ID_COL + "=" + id, null) > 0;
    }

}
