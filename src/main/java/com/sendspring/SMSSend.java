package com.sendspring;

import android.telephony.SmsManager;
import android.util.Log;

import java.util.List;

/**
 * Created by TianChi on 14-1-16.
 *
 * if it is a warm wishes,replay a warm wishes as well
 */
public class SMSSend{
    SSApplication app;
    SMSSend(){}
    SMSSend(SSApplication app){
        this.app=app;
    }
    public void send(String addr){
        String msg=app.getTitle()+app.getAText();
        Log.i("Send",msg);
        SmsManager sms = SmsManager.getDefault();
        List<String> divideContents = sms.divideMessage(msg);
        for (String text : divideContents) {
            sms.sendTextMessage(addr, null, text, null, null);
        }
    }
}
