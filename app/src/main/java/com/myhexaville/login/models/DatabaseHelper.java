package com.myhexaville.login.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "ChatApp.db";
    public static final String TABLE_NAME = "verification_table";
    public static final String COL_1 = "id";
    public static final String COL_2 = "verifyCode";
    public static final String COL_3 = "status";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE "+ TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, verifyCode TEXT, status BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+TABLE_NAME);
        onCreate(db);
    }
}
