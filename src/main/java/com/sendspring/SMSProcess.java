package com.sendspring;

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
            new SMSSend().send(addr);
        }
    }

    //判断短信是否是祝福短信
   private boolean handleSMS(){
        if(body.contains("春节"))
            return true;
        return false;
    }

}
