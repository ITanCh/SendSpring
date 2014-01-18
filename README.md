SendSpring
==========
by Tianchi
----------
### replay warm wishes intelligently..

####短信接收、发送

* 短信监听参考：http://www.blogjava.net/anymobile/articles/328396.html

* 监听的动态注册：在mainActivity的onCreate里注册

```java
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    IntentFilter filter = new IntentFilter(SMS_RECEIVED);
    BroadcastReceiver receiver = new MessageBroadcastReceiver();
    registerReceiver(receiver, filter);
```

* 监听的静态注册：在Manifest里

``` html
       <!-- Receiver -->
       <receiver android:name="com.sendspring.SMSReceiver">
         <intent-filter>
             <action android:name="android.provider.Telephony.SMS_RECEIVED" />
         </intent-filter>
       </receiver>
```


* 遇到问题：

 >message.getDisplayMessageBody()虚拟机中文乱码，真机中没有乱码。

####application

才发现application这个好东西，真不知道以前写的那些代码叫什么玩意，参数传递的这么蛋疼，罪过罪过。

具体参考：http://my.eoe.cn/cainiao1/archive/2355.html