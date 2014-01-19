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
    private String[] texts;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        //线程池
        executorService = Executors.newCachedThreadPool();

        //connect to sms db
        myHelper = new MySMSHelper(this, MySMSHelper.DB_NAME, null, 1);

        //检验db是否已经有数据，若没有，先初始化,暂时就想起来这么多
        db = myHelper.getWritableDatabase();
        Cursor c = null;
        if (db != null) {
            c = db.query(MySMSHelper.WORD_TABLE, new String[]{MySMSHelper.ID}, null, null, null, null, null);
            if (c.getCount() == 0) {
                //初始化词
                String[] words = {"春节", "阖家", "欢乐", "幸福", "快乐", "祝", "愉快", "节日", "和睦", "安康", "健康"};
                for (String word : words) {
                    ContentValues cv = new ContentValues();
                    cv.put(MySMSHelper.WORD, word);
                    db.insert(MySMSHelper.WORD_TABLE, null, cv);
                }
                //初始化短信
                String[] sms={"新年快乐，龙马精神，万事如意，百尺竿头，恭喜发财，年年有余。",
                        "新年的钟声里举起杯，任酒的醇香在空气中荡漾，任我对你的感激在杯里慢慢沉淀，深深祝福我的朋友，祝你新年幸福美满，健康快乐！",
                        "愿你在新的一年里：抱着平安，拥着健康，揣着幸福，携着快乐，搂着温馨，带着甜蜜，牵着财运，拽着吉祥，迈入新年，快乐度过每一天！",
                        "又是一年，时间又一次停靠新年驿站，跟过去的一年告别，向未来的一年祈愿，感受岁月无情流逝，心中又升腾起新的希望。让我们拥抱新年，共赴未来吧！"};
                for(String s:sms){
                    ContentValues cv = new ContentValues();
                    cv.put(MySMSHelper.SMS_TEXT, s);
                    db.insert(MySMSHelper.SMS_TABLE, null, cv);
                }
            }
            c.close();
            updateWords();
            updateSMS();
        }

    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MySMSHelper getMyHelper() {
        return myHelper;
    }

    public SQLiteDatabase getDb() { return db; }

    public String[] getWords() {
        return words;
    }


    //更新内存中的单词量
    public void updateWords(){
        Cursor c = db.query(MySMSHelper.WORD_TABLE, new String[]{MySMSHelper.ID, MySMSHelper.WORD}, null, null, null, null, null);
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
    }

    //更新内存中的单词量
    public void updateSMS(){
        Cursor c = db.query(MySMSHelper.SMS_TABLE, new String[]{MySMSHelper.ID, MySMSHelper.SMS_TEXT}, null, null, null, null, null);
        int idIndex = c.getColumnIndex(MySMSHelper.ID);
        int textIndex = c.getColumnIndex(MySMSHelper.SMS_TEXT);
        texts = new String[c.getCount()];
        int i = 0;
        while (c.moveToNext()) {
            int id = c.getInt(idIndex);
            texts[i] = c.getString(textIndex);
            Log.i("APP", id + " :" + texts[i]);
            i++;
        }
    }

    public String getAText(){
        int i=(int)(Math.random()*100)%texts.length;
        Log.i("APP",String.valueOf(i));
        return texts[i];
    }
}
