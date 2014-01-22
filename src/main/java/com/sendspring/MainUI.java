
package com.sendspring;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class MainUI extends Activity implements View.OnClickListener {
    final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private  SSApplication app;
    private BroadcastReceiver receiver;
    private ListView wordsList;
    private Button wordsButton;
    private Button replyButton;
    private Button smsButton;
    private Button addWordsButton;
    private Button addSmsButton;
    private Button addTitleButton;
    private EditText addText;
    private TextView titleText;
    private Cursor wordc;
    private Cursor replyc;
    private Cursor smsc;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        app=(SSApplication) getApplication();
        //注册短信监听
        Log.i("MAIN", "注册监听");
        IntentFilter filter = new IntentFilter(SMS_RECEIVED);
        receiver = new SMSReceiver(app);
        registerReceiver(receiver, filter);

        //初始化列表
        MySMSHelper myHelper = ((SSApplication) getApplication()).getMyHelper();
        db = myHelper.getWritableDatabase();
        assert db != null;
        wordc = db.query(MySMSHelper.WORD_TABLE, null, null, null, null, null, null);
        Log.i("MAIN", "初始化List");
        wordsList = (ListView) this.findViewById(R.id.wordsList);
        wordsList.setAdapter(new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, wordc,
                new String[]{MySMSHelper.WORD},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        Log.i("MAIN", "结束初始化List");
        wordsButton = (Button) this.findViewById(R.id.wordsButton);
        wordsButton.setOnClickListener(this);
        replyButton = (Button) this.findViewById(R.id.replyButton);
        replyButton.setOnClickListener(this);
        smsButton = (Button) this.findViewById(R.id.smsButton);
        smsButton.setOnClickListener(this);
        addWordsButton = (Button) this.findViewById(R.id.addWordsButton);
        addWordsButton.setOnClickListener(this);
        addSmsButton = (Button) this.findViewById(R.id.addSmsButton);
        addSmsButton.setOnClickListener(this);
        addTitleButton=(Button)this.findViewById(R.id.addTitleButton);
        addTitleButton.setOnClickListener(this);
        titleText=(TextView)this.findViewById(R.id.titleText);
        addText = (EditText) this.findViewById(R.id.wordsText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_ui, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //切换列表内容
        if (view == wordsButton) {
            wordc = db.query(MySMSHelper.WORD_TABLE, null, null, null, null, null, null);
            wordsList.setAdapter(new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, wordc,
                    new String[]{MySMSHelper.WORD},
                    new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        } else if (view == replyButton) {
            replyc = db.query(MySMSHelper.REPLY_TABLE, null, null, null, null, null, null);
            wordsList.setAdapter(new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_2, replyc,
                    new String[]{MySMSHelper.REPLY_ADDR, MySMSHelper.REPLY_TIME},
                    new int[]{android.R.id.text1, android.R.id.text2}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        } else if (view == smsButton) {
            smsc = db.query(MySMSHelper.SMS_TABLE, null, null, null, null, null, null);
            wordsList.setAdapter(new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, smsc,
                    new String[]{MySMSHelper.SMS_TEXT},
                    new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER));
        } else if (view == addWordsButton) {
            if (addText != null) {
                String word = addText.getText().toString();
                if (!word.equals("")) {
                    Cursor c = db.query(MySMSHelper.WORD_TABLE, null, MySMSHelper.WORD + "=?", new String[]{word}, null, null, null);
                    if (c.getCount() > 0) {
                        c.close();
                        Toast.makeText(this, "请不要重复添加！", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(MySMSHelper.WORD, word);
                    db.insert(MySMSHelper.WORD_TABLE, null, cv);
                    c.close();
                    //更新词汇内容
                    wordc = db.query(MySMSHelper.WORD_TABLE, null, null, null, null, null, null);
                    app.updateWords();
                    Toast.makeText(this, "添加成功！", Toast.LENGTH_LONG).show();
                }
            }
        } else if (view == addSmsButton) {
            if (addText != null) {
                String sms = addText.getText().toString();
                if (!sms.equals("")) {
                    ContentValues cv = new ContentValues();
                    cv.put(MySMSHelper.SMS_TEXT, sms);
                    db.insert(MySMSHelper.SMS_TABLE, null, cv);
                    app.updateSMS();
                    Toast.makeText(this, "添加成功！", Toast.LENGTH_LONG).show();
                }
            }
        }else if(view==addTitleButton){
            if (addText != null) {
                String title = addText.getText().toString();
                if (!title.equals("")) {
                    titleText.setText(title);
                    app.setTitle(title);
                    Toast.makeText(this, "添加成功！", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_main, container, false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (wordc != null) wordc.close();
        if (replyc != null) replyc.close();
        if (smsc != null) smsc.close();
        Log.i("MAIN", "取消监听");
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            Log.i("MAIN", "监听已经取消，不用再次取消");
        }
    }
}
