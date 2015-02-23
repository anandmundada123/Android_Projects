package com.example.amundada.instagramclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PhotosActivity extends Activity {

    public static final String CLIENT_ID = "c44ad2d71da04a35b487552b6620c837";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        photos = new ArrayList<InstagramPhoto>();

        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Find List View
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // Bind list view to adapter
        lvPhotos.setAdapter(aPhotos);

        lvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(photos.get(position).videoUrl != null) {
                    showRemoteVideo(photos.get(position).videoUrl);
                }
            }
        });
        fetchPopularPhotos();
        // SEND OUT API REQUEST and get popular photos
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchPopularPhotos();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void showRemoteVideo(String url) {
        Intent i = new Intent(getApplicationContext(), VideoActivity.class);

        i.putExtra("url", url);
        startActivity(i);
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

                // Clear out all previous contents If you want to keep previous contents then don't add this.
                //photos.clear();
                JSONArray photosJson = null;

                try {
                    photosJson = response.getJSONArray("data");
                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();

                        //AUTHOR: {"data" => [x] => "user" => "username"}
                        photo.userName = photoJson.getJSONObject("user").getString("username");

                        // Profile Image

                        photo.profileUrl = photoJson.getJSONObject("user").getString("profile_picture");

                        // CAPTION: {"data" => [x] => "caption" => "text"}
                        photo.caption = photoJson.getJSONObject("caption").getString("text");


                        // URL: {"data" => [x] => "images" => "standard_resolution" => "url"}
                        photo.imageUrl = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

                        // image Height
                        photo.imageHeight = photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");

                        // like Count
                        photo.likeCount = photoJson.getJSONObject("likes").getString("count");

                        JSONArray commentsArray = photoJson.getJSONObject("comments").getJSONArray("data");

                        if (commentsArray.length() == 0) {
                            photo.comment1 = "No Comments present";
                            photo.comment2 = "No Comments Present";
                        } else if (commentsArray.length() >= 2){
                            photo.comment1 = commentsArray.getJSONObject(0).getString("text");
                            photo.comment2 = commentsArray.getJSONObject(1).getString("text");
                        } else {
                            photo.comment1 = commentsArray.getJSONObject(0).getString("text");
                            photo.comment2 = "No Comments Present";
                        }

                        if (photoJson.has("videos")) {
                            photo.videoUrl = photoJson.getJSONObject("videos").getJSONObject("low_bandwidth").getString("url");
                        } else {
                            photo.videoUrl = null;
                        }

                        if(photoJson.has("location") && !photoJson.isNull("location")) {
                            double lat = photoJson.getJSONObject("location").getDouble("latitude");
                            double lng = photoJson.getJSONObject("location").getDouble("longitude");
                            getLocation(lat, lng, getApplicationContext());
                        } else {
                            photo.location = "";
                        }
                        //
                        // If you want to keep previous photos as well. Add always at the index 0
                        photos.add(0,photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // DO Something
            }
        });
    }

    public void getLocation(double lat, double lng, Context context) {

        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0)
            System.out.println(addresses.get(0).getLocality());
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
