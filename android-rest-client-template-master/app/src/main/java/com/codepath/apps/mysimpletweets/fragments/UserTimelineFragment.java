package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by amundada on 3/17/15.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long lastSeenTweetNo = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeLine();
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment fragmentDemo = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }

    protected void populateTimeLine() {
        client.getUserTimeline(getArguments().getString("screen_name"), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                addAll(Tweet.fromJSONArray(json));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
