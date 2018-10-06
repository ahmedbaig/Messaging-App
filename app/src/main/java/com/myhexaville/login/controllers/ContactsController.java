package com.myhexaville.login.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myhexaville.login.models.DatabaseHelper;

public class ContactsController extends DatabaseHelper {

    public ContactsController(Context context) {
        super(context);
    }

    public boolean insertContact(String code, String number){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId", code);
        contentValues.put("number", number);
        Cursor res = db.rawQuery("SELECT * FROM "+CONTACTS_TABLE_NAME+" where userId='"+code+"'", null);
        if(res.getCount()>0){
            return false;
        }else{
            long result = db.insert(CONTACTS_TABLE_NAME, null, contentValues);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
    }

    public void deleteContacts(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(CONTACTS_TABLE_NAME, null, null);
    }

    public Cursor getContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT number FROM "+CONTACTS_TABLE_NAME, null);
        return res;
    }
}
