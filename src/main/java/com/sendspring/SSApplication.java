package com.sendspring;

import android.app.Application;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TianChi on 14-1-18.
 *
 * global variable
 */

public class SSApplication extends Application {
    //执行容器
    private ExecutorService executorService;
    //数据库连接
    private MySMSHelper myHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        //线程池
        executorService = Executors.newCachedThreadPool();

        //connect to sms db
        myHelper = new MySMSHelper(this, MySMSHelper.DB_NAME, null, 1);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MySMSHelper getMyHelper(){
        return myHelper;
    }
}
