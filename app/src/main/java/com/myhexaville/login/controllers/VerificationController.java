package com.myhexaville.login.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myhexaville.login.models.DatabaseHelper;

public class VerificationController extends DatabaseHelper {

    public VerificationController(Context context) {
        super(context);
    }

    public boolean insertVerificationCode(String code){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, code);
        contentValues.put(COL_3, true);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getVerifiedUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT verifyCode FROM "+TABLE_NAME+" WHERE status=1", null);
        return res;
    }

    public Integer deleteVerifiedUser(String code){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "verifyCode = ?", new String[]{code});
    }
}
