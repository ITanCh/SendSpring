package com.sendspring;

import android.telephony.SmsManager;
import android.util.Log;

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
        String msg="天驰祝你:  "+app.getAText();
        Log.i("Send",msg);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(addr, null, msg ,null, null);
    }
}
