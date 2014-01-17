package com.sendspring;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TianChi on 14-1-17.
 *
 *  1.warm word db
 *  2.reply db
 *  3.warm message db
 *  */
public class MySMSHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="sms_db";
    public static final String ID="_id";

    public static final String WORD_TABLE="word_table";
    public static final String WORD="word";

    public static final String REPLY_TABLE="reply_table";
    public static final String REPLY_ADDR="reply_addr";
    public static final String REPLY_TIME="reply_time";

    public static final String SMS_TABLE="sms_table";
    public static final String SMS_TEXT="sms_text";

    public MySMSHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create word table
        sqLiteDatabase.execSQL("create table if not exists "+WORD_TABLE+"("
                +ID + " integer primary key,"
                +WORD+" varchar)");

        //create reply table
        sqLiteDatabase.execSQL("create table if not exists "+REPLY_TABLE+"("
                +ID + " integer primary key,"
                +REPLY_ADDR+" varchar,"
                +REPLY_TIME+" varchar)");

        //create sms table
        sqLiteDatabase.execSQL("create table if not exists "+SMS_TABLE+"("
                +ID + " integer primary key,"
                +SMS_TEXT+"  text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
