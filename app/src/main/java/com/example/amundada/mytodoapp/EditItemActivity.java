package com.example.amundada.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.amundada.mytodoapp.R;

public class EditItemActivity extends Activity {

    private EditText eItemText;
    private Integer iItemIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        eItemText = (EditText) findViewById(R.id.etItem);
        eItemText.setText(getIntent().getStringExtra(ToDoActivity.ITEM_TEXT_KEY));
        iItemIndex = getIntent().getIntExtra(ToDoActivity.ITEM_INDEX_KEY, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
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

    public void onSaveButtonClick(View view) {

        Intent data = new Intent();
        data.putExtra(ToDoActivity.ITEM_TEXT_KEY, eItemText.getText().toString());
        data.putExtra(ToDoActivity.ITEM_INDEX_KEY, iItemIndex);
        setResult(RESULT_OK, data);
        finish();
    }
}
