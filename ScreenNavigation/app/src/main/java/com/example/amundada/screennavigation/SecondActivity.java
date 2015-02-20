package com.example.amundada.screennavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.amundada.screennavigation.R;

public class SecondActivity extends Activity {

    private EditText etEditedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        etEditedText = (EditText) findViewById(R.id.etEditedText);

        Intent i = getIntent();
        etEditedText.setText(i.getStringExtra(FirstActivity.TEXT_KEY));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
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

    public void onSubmitButtonClick(View view) {
        Intent i = new Intent();
        i.putExtra(FirstActivity.TEXT_KEY, etEditedText.getText().toString());
        setResult(FirstActivity.RESPONSE_CODE, i);
        finish();
    }
}
