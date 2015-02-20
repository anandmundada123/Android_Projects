package com.example.amundada.flickerclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


public class PhotoActivity extends Activity {

    private static final String API_KEY = "161999e22169b11b43f651e12c4953ce";
    private ArrayList<PhotoItem> photos;
    private PhotoItemAdaptor aPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        photos = new ArrayList<PhotoItem>();
        aPhoto = new PhotoItemAdaptor(this, photos);

        ListView lvPhotos = (ListView)findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(aPhoto);
        fetchPhotos();
    }

    private void fetchPhotos() {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=" + API_KEY +"&extras=url_l,owner_name,description";
        client.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_LONG).show();
                Log.i("debug", new String(responseBody));
                String xmlResponse = new String(responseBody);
                System.out.println(xmlResponse);
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = factory.newPullParser();
                    parser.setInput(new StringReader(xmlResponse));
                    int eventType = parser.next();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if(eventType == XmlPullParser.START_TAG && "photo".equals(parser.getName())) {
                            String title = parser.getAttributeValue(null, "title");
                            String url = parser.getAttributeValue(null, "url_l");
                            String ownerName = parser.getAttributeValue(null, "ownername");
                            //String description = parser.getAttributeValue(null, "description");

                            PhotoItem photo = new PhotoItem();
                            photo.title = title;
                            photo.url = url;
                            photo.ownerName = ownerName;
                            //photo.description = description;
                            photos.add(photo);
                        }
                        eventType = parser.next();
                    }

                    aPhoto.notifyDataSetChanged();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), new String(responseBody), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
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
