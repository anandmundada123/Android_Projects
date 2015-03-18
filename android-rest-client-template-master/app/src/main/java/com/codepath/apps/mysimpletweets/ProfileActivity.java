package com.codepath.apps.mysimpletweets;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class ProfileActivity extends ActionBarActivity {

    TwitterClient client;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        client = TwitterApplication.getRestClient();
        client.getProfile(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                // My current User account info
                getSupportActionBar().setTitle("@" + user.getScreen_name());
                populateHeader();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        // get Screen Name

        if (savedInstanceState == null) {
            String screenName = getIntent().getStringExtra("screen_name");
            UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flUserTimeline, userTimelineFragment);
            ft.commit();
        }
    }

    private void populateHeader() {
        ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);

        tvUserName.setText(user.getUserName());
        tvFollowers.setText(user.getFollowerCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");
        tvTagLine.setText(user.getTagLine());
        Picasso.with(getApplicationContext()).load(user.getProfileUrl()).into(ivProfileImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
}
