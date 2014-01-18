package com.sendspring;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TianChi on 14-1-18.
 * <p/>
 * global variable
 */

public class SSApplication extends Application {
    //执行容器
    private ExecutorService executorService;
    //数据库连接
    private MySMSHelper myHelper;
    private String[] words;

    @Override
    public void onCreate() {
        super.onCreate();
        //线程池
        executorService = Executors.newCachedThreadPool();

        //connect to sms db
        myHelper = new MySMSHelper(this, MySMSHelper.DB_NAME, null, 1);

        //检验db是否已经有数据，若没有，先初始化,暂时就想起来这么多
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Cursor c = null;
        if (db != null) {
            c = db.query(MySMSHelper.WORD_TABLE, new String[]{MySMSHelper.ID}, null, null, null, null, null);
            if (c.getCount() == 0) {
                String[] words = {"春节", "阖家", "欢乐", "幸福", "快乐", "祝", "愉快", "节日", "和睦", "安康", "健康"};
                for (String word : words) {
                    ContentValues cv = new ContentValues();
                    cv.put(MySMSHelper.WORD, word);
                    db.insert(MySMSHelper.WORD_TABLE, null, cv);
                }
            }
            c.close();
            c = db.query(MySMSHelper.WORD_TABLE, new String[]{MySMSHelper.ID, MySMSHelper.WORD}, null, null, null, null, null);
            int idIndex = c.getColumnIndex(MySMSHelper.ID);
            int wordIndex = c.getColumnIndex(MySMSHelper.WORD);
            words = new String[c.getCount()];
            int i = 0;
            while (c.moveToNext()) {
                int id = c.getInt(idIndex);
                words[i] = c.getString(wordIndex);
                Log.i("APP", id + " :" + words[i]);
                i++;
            }
            c.close();
            db.close();
        }

    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MySMSHelper getMyHelper() {
        return myHelper;
    }

    public String[] getWords() {
        return words;
    }
}
