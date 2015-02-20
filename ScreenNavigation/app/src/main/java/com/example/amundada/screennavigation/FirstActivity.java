package com.example.amundada.screennavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class FirstActivity extends Activity {

    public static final String TEXT_KEY = "textKey";
    public static final int RESPONSE_CODE = 20;

    private EditText etOrigText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        etOrigText = (EditText) findViewById(R.id.etEditedText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.first, menu);
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

    public void onEditButtonClick(View view) {
        if (etOrigText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter Text", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), etOrigText.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), SecondActivity.class);
            i.putExtra(TEXT_KEY, etOrigText.getText().toString());
            startActivityForResult(i, RESPONSE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESPONSE_CODE) {
            etOrigText.setText( data.getExtras().getString(TEXT_KEY));
        }
    }
}
