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

####layout的管理

* ListView
    >SimpleCursorAdapter适用于数据库内容的陈列

    >adapter的样式：http://blog.csdn.net/ma12an/article/details/7762961

    >自动旋转屏幕比较坑爹，如果没有在横屏上布局的话，横屏后导致有些部件找不到，出现NullPointerException

####应用说明
    >节日短信的接收一般集中在某一个不长的时间段里，所以只需要在这个时间段将应用打开，命中率会很高的，所以没有
    >使用复杂的算法来提高命中率，这一点可在以后丰富。
