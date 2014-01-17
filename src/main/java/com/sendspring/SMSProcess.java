package com.sendspring;

import android.util.Log;

/**
 * Created by TianChi on 14-1-16.
 *
 * thread to deal with message
 */
public class SMSProcess  implements Runnable{
    String body;
    String addr;
    public SMSProcess(){
        this("","");
    }
    public SMSProcess(String b,String a){
        body=b;
        addr=a;
    }
    @Override
    public void run() {
        if(handleSMS()){
            Log.i("SMSProcess", "准备发送");
            new SMSSend().send(addr);
        }
    }

    //判断短信是否是祝福短信
   private boolean handleSMS(){
        return body.contains("春节");
    }

}
