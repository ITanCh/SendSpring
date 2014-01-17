package com.sendspring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import org.apache.http.conn.routing.BasicRouteDirector;

/**
 * Created by TianChi on 14-1-16.
 *
 * get sms when receive a new sms
 */

public class SMSReceiver  extends BroadcastReceiver {
    public static final String TAG = "SMSReceiver!!!!!!!";
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private SSApplication app;

    SMSReceiver(){};
    SMSReceiver(SSApplication app){
        this.app=app;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(SMS_RECEIVED_ACTION)) {
                SmsMessage[] messages = getMessagesFromIntent(intent);
                for (SmsMessage message : messages) {
                    String body = new String(message.getDisplayMessageBody());
                    String addr = new String(message.getDisplayOriginatingAddress());
                    Log.i(TAG, addr + "  :" + body);
                    app.getExecutorService().execute(new SMSProcess(body, addr));
                }
            }
        }
    }

    //get message from intent
    public final SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] obj = (Object[]) intent.getSerializableExtra("pdus");
        SmsMessage[] messages = new SmsMessage[obj.length];
        Log.i(TAG,"START");
        for (int i = 0; i < obj.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
        }
        return messages;
    }

}
