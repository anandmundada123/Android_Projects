package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TweetsAdaptor;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;

/**
 * Created by amundada on 3/17/15.
 */
public abstract class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private ListView lvTweets;
    private TweetsAdaptor aTweetsAdaptor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<Tweet>();
        aTweetsAdaptor = new TweetsAdaptor(getActivity(), tweets);

    }

    protected abstract void populateTimeLine();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweet_list, parent, false);

        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweetsAdaptor);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeLine();
            }
        });
        return v;
    }

    public void addAll(ArrayList<Tweet> tweets) {
        aTweetsAdaptor.addAll(tweets);
    }

    public  long getLastUid() {
        if (tweets.size() >= 1)
            return tweets.get(tweets.size() - 1).getUid();
        else
            return 0;
    }
}
