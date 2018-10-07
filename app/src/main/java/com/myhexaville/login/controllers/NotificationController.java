package com.myhexaville.login.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myhexaville.login.models.DatabaseHelper;

public class NotificationController extends DatabaseHelper {
    public NotificationController(Context context) {
        super(context);
    }
    public boolean insertNotification(String notification){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("notification", notification);
        Cursor res = db.rawQuery("SELECT * FROM "+NOTIFICATIONS_TABLE_NAME+" where notification='"+notification+"'", null);
        if(res.getCount()>0){
            return false;
        }else{
            long result = db.insert(NOTIFICATIONS_TABLE_NAME, null, contentValues);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
    }

    public void deleteNotifications(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(NOTIFICATIONS_TABLE_NAME, null, null);
    }

    public Cursor getNotifications() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT notification FROM "+NOTIFICATIONS_TABLE_NAME, null);
        return res;
    }
}
