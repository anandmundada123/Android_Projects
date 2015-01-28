package com.example.amundada.mytodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ToDoActivity extends Activity {

    private ListView lvItems; // Initially it should be NULL
    private EditText etItem;
    private ArrayList<String> items; // All items
    private ArrayAdapter<String> aToDoAdapter;
    public static final String ITEM_TEXT_KEY = "ItemText";
    public static final String ITEM_INDEX_KEY = "ItemIndex";
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        readItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(aToDoAdapter);
        etItem = (EditText)findViewById(R.id.etItem);
        supportRemoveItems();
        supportEditItems();

    }

    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todoItems.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todoItems.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supportEditItems() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplication(), "Edit now", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplication(), EditItemActivity.class);
                i.putExtra(ITEM_TEXT_KEY, items.get(position));
                i.putExtra(ITEM_INDEX_KEY, position);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    private void supportRemoveItems() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemTxt = data.getExtras().getString(ITEM_TEXT_KEY);
            int itemIndex = data.getExtras().getInt(ITEM_INDEX_KEY);
            items.set(itemIndex, itemTxt);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void onAddItemClick(View view) {
        String item = etItem.getText().toString();
        if(!item.isEmpty()) {
            aToDoAdapter.add(item);
            etItem.setText("");
            writeItems();
        } else {
            Toast.makeText(this, "Enter Item", Toast.LENGTH_SHORT).show();
        }
    }
}
