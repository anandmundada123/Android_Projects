package com.example.amundada.simplechat1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.amundada.simplechat1.R;
import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ChatDisplayActivity extends Activity {

    private ParseObject parseUser;
    private ListView lvChat;
    private EditText etMessage;
    private ChatListAdapter aChatListAdapter;
    private ArrayList<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_display);
        Intent i = getIntent();
        String email = i.getStringExtra("email");
        createUser(email);

        lvChat = (ListView)findViewById(R.id.lvChat);
        etMessage = (EditText)findViewById(R.id.etMessage);
        messages = new ArrayList<Message>();
        aChatListAdapter = new ChatListAdapter(getApplicationContext(), messages);
        lvChat.setAdapter(aChatListAdapter);
    }

    public void createUser(String email) {
        parseUser = new ParseObject("ChatObject");
        parseUser.put("emailId", email);
        parseUser.saveInBackground();

        ParseAnonymousUtils.logIn(new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.d("MyApp", "Anonymous login failed.");
                } else {
                    Log.d("MyApp", "Anonymous user logged in.");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendClicked(View view) {
        String msg = etMessage.getText().toString();
        Message m = new Message();
        m.message = msg;
        messages.add(m);
        aChatListAdapter.notifyDataSetChanged();
    }
}
