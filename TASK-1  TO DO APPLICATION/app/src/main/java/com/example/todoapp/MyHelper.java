package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {

    //variables
    private static final String dbname = "User_registration";
    private static final int version = 1;
    public MyHelper(@Nullable Context context) {
        super(context, dbname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the student_record table when the database is created
        String query = "create table student_record (id integer primary key autoincrement , Username text , Email text " + ", Password text) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing student_record table and recreate it when the database is upgraded
        db.execSQL("drop table if exists student_record");
        onCreate(db);

    }

    // Check if the data insertion was successful
    public boolean insertdata(String username, String email, String password) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Username", username);
        values.put("Email", email);
        values.put("Password", password);
        long r = db.insert("student_record", null, values);
        if (r == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Return true if the username exists, false otherwise
    public boolean checkusername(String username) {

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from student_record where Username=?", new String[]{username});
        if (cursor.getCount() > 0) {
            return true;

        } else {
            return false;
        }

    }

    // Return true if the username and password combination exists, false otherwise
    public boolean checkusernameandpassword(String username , String password){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from student_record where Username=? and Password=? "
                , new String[]{username , password});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}