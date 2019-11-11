package com.example.asnaui.iemmis.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asnaui.iemmis.Model.User;

public class DBContext extends SQLiteOpenHelper {
    static String DB_NAME = "db_iemmis";
    static String TBL_USER = "tbl_user";
    public DBContext(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TBL_USER + " (" +
                "name" + " TEXT, " +
                "userid" + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
    }

    public void insertUser(User object) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", object.getName());
        cv.put("userid", object.getUserid());
        sqLiteDatabase.insert(TBL_USER, null, cv);
        sqLiteDatabase.close();
    }

    public User getUser() {
        User user = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TBL_USER, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String userid = cursor.getString(cursor.getColumnIndex("userid"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                user = new User(name,userid);
                cursor.moveToNext();
            }
            cursor.close();
        }
        sqLiteDatabase.close();
        return user;
    }
}
