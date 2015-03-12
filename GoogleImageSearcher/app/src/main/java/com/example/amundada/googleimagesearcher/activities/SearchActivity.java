package com.example.amundada.googleimagesearcher.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amundada.googleimagesearcher.R;
import com.example.amundada.googleimagesearcher.adapters.EndlessScrollListener;
import com.example.amundada.googleimagesearcher.adapters.ImageResultAdapter;
import com.example.amundada.googleimagesearcher.models.ImageResults;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    private EditText etSearch;
    private GridView gvImages;
    private ArrayList<ImageResults> imageResults;
    private ImageResultAdapter aImageResults;
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getComponents();

        imageResults = new ArrayList<ImageResults>();
        aImageResults = new ImageResultAdapter(this, imageResults);
        gvImages.setAdapter(aImageResults);

        gvImages.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                if(page <= 8)
                    customLoadMoreDataFromApi((page - 1) * 8 );
                //Toast.makeText(getApplicationContext(), page, Toast.LENGTH_SHORT);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        loadImages(offset);
    }

    private void getComponents() {
        etSearch = (EditText) findViewById(R.id.etSearch);
        gvImages = (GridView) findViewById(R.id.gvImages);
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageResults image = imageResults.get(position);
                Intent i = new Intent(SearchActivity.this, FullImage.class);
                i.putExtra("url", image.url);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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

    private void loadImages(int offset) {
        String url = "https://ajax.googleapis.com/ajax/services/search/images?q=" + query + "&v=1.0&rsz=8&start=" + offset;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("DEBUG", response.toString());
                ArrayList<ImageResults> images = ImageResults.getImageResultsFromJson(response);
                //imageResults.clear();
                imageResults.addAll(images);
                aImageResults.notifyDataSetChanged();
            }
        });
    }
    public void onSearchClick(View view) {
        query = etSearch.getText().toString();
        //Toast.makeText(this, "Search String:" + query, Toast.LENGTH_SHORT).show();
        //https://ajax.googleapis.com/ajax/services/search/images?q=android&v=1.0&rsz=8
        loadImages(0);


    }
}
