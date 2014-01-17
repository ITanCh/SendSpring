SendSpring
==========
   by Tianchi
----------
### replay warm wishes intelligently..

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
