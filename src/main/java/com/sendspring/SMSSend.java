package com.sendspring;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by TianChi on 14-1-16.
 *
 * if it is a warm wishes,replay a warm wishes as well
 */
public class SMSSend{
    public void send(String addr){
        String msg="你也新年好！";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(addr, null, msg ,null, null);
    }
}
