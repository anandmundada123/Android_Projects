package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewTweetActivity extends ActionBarActivity {

    private TwitterClient client;
    private User user;
    ImageView ivPicture;
    TextView tvName;
    EditText etNewTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);



        ivPicture = (ImageView)findViewById(R.id.ivPicture);
        tvName = (TextView)findViewById(R.id.tvName);
        etNewTweet = (EditText)findViewById(R.id.etNewTweet);

        client = TwitterApplication.getRestClient();
        client.getProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                loadUserInfo();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void loadUserInfo() {
        Picasso.with(this).load(user.getProfileUrl()).into(ivPicture);
        tvName.setText(user.getUserName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onPostClick(View view) {
        String postStr = etNewTweet.getText().toString();
        if (!postStr.isEmpty()) {
            client.postTweet(postStr, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Intent i = new Intent();
                    i.putExtra("code", 200);
                    setResult(RESULT_OK, i);
                    finish();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.d("Debug", responseString);
                }
            });
        }
        else {
            Intent i = new Intent();
            finish();
        }
    }

}
