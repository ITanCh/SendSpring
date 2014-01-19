package com.sendspring;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;

/**
 * Created by TianChi on 14-1-16.
 * <p/>
 * thread to deal with message
 */
public class SMSProcess implements Runnable {
    private String body;
    private String addr;
    private SSApplication app;
    private SQLiteDatabase db;

    public SMSProcess() {
        this("", "", null);
    }

    public SMSProcess(String b, String a, SSApplication app) {
        body = b;
        addr = a;
        this.app = app;
        db=app.getDb();
    }

    @Override
    public void run() {
        if (handleSMS()) {
            Log.i("SMSProcess", "准备发送");
            new SMSSend(app).send(addr);
            ContentValues contentValues=new ContentValues();
            contentValues.put(MySMSHelper.REPLY_ADDR,addr);
            contentValues.put(MySMSHelper.REPLY_TIME,new Date().toString());
            db.insert(MySMSHelper.REPLY_TABLE,null,contentValues);
        }
    }

    //判断短信是否是祝福短信
    private boolean handleSMS() {
        String[] words = app.getWords();
        for (String word : words) {
            Log.i("PROCESS", word);
            if (body.contains(word))
                return true;
        }
        return false;
    }

}
