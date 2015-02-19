package com.example.amundada.instagramclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhotosActivity extends Activity {

    public static final String CLIENT_ID = "c44ad2d71da04a35b487552b6620c837";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<InstagramPhoto>();

        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Find List View
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // Bind list view to adapter
        lvPhotos.setAdapter(aPhotos);
        // SEND OUT API REQUEST and get popular photos
        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {

        /*
        https://api.instagram.com/v1/media/popular?client_id=CLIENT-ID
        CLIENT IDc44ad2d71da04a35b487552b6620c837
         */
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Expecting Data
                // TYPE: {"data" => [x] => "type"} ("image", "video")
                // URL: {"data" => [x] => "images" => "standard_resolution" => "url"}
                // CAPTION: {"data" => [x] => "caption" => "text"}
                // AUTHOR: {"data" => [x] => "user" => "username"}
               // Log.i("DEBUG", response.toString());

                // iterate through all json Array and get info and store into java object

                JSONArray photosJson = null;

                try {
                    photosJson = response.getJSONArray("data");
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();

                        //AUTHOR: {"data" => [x] => "user" => "username"}
                        photo.userName = photoJson.getJSONObject("user").getString("username");

                        // CAPTION: {"data" => [x] => "caption" => "text"}
                        photo.caption = photoJson.getJSONObject("caption").getString("text");

                        // URL: {"data" => [x] => "images" => "standard_resolution" => "url"}
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                        // image Height
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

                        // like Count
                        photo.likeCount = photoJson.getJSONObject("likes").getInt("count");

                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO Something
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photos, menu);
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
}
