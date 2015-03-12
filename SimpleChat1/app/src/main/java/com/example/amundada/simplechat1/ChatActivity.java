package com.example.amundada.simplechat1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;


public class ChatActivity extends Activity {

    public static final String YOUR_APPLICATION_ID = "fRGZRJSOSE91mK4ejT9Hz32LIwBHP6q2nR1EbSLB";
    public static final String YOUR_CLIENT_KEY = "Pi88VvDe6KP3rfrPATtWwMqZueHRuadHnJLPVqUX";

    private EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, YOUR_APPLICATION_ID, YOUR_CLIENT_KEY);
        etEmail = (EditText)findViewById(R.id.etEmail);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
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

    public void onLoginClicked(View view) throws ParseException {

        String email = etEmail.getText().toString();
        Intent i = new Intent(this, ChatDisplayActivity.class);
        i.putExtra("email", email);
        startActivity(i);

    }
}
