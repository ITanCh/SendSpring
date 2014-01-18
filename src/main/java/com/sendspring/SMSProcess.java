package com.sendspring;

import android.util.Log;

/**
 * Created by TianChi on 14-1-16.
 * <p/>
 * thread to deal with message
 */
public class SMSProcess implements Runnable {
    private String body;
    private String addr;
    private SSApplication app;

    public SMSProcess() {
        this("", "", null);
    }

    public SMSProcess(String b, String a, SSApplication app) {
        body = b;
        addr = a;
        this.app = app;
    }

    @Override
    public void run() {
        if (handleSMS()) {
            Log.i("SMSProcess", "准备发送");
            new SMSSend().send(addr);
        }
    }

    //判断短信是否是祝福短信
    private boolean handleSMS() {
        String[] words = app.getWords();
        int l=words.length;
        for (int i=0;i<l;i++) {
            Log.i("PROCESS",words[i]);
            if (body.contains(words[i]))
                return true;
        }
        return false;
    }

}
