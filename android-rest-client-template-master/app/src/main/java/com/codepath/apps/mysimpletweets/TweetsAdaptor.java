package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by amundada on 3/7/15.
 */
public class TweetsAdaptor extends ArrayAdapter<Tweet> {


    public TweetsAdaptor(Context context, ArrayList<Tweet> objects) {
        super(context, R.layout.item_tweet, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        User user = tweet.getUser();
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfileImage);


        tvUserName.setText("@" + user.getUserName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        Picasso.with(getContext()).load(user.getProfileUrl()).into(ivProfile);
        return convertView;
    }


    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            String relativeDateStr = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            String [] tokens = relativeDateStr.split(" ");
            if (tokens.length >= 2) {
                relativeDate = tokens[0];
                switch (tokens[1]) {
                    case "minutes":
                    case "minute":
                        relativeDate += "m";
                        break;
                    case "days":
                    case "day":
                        relativeDate += "d";
                        break;
                    case "hour":
                    case "hours":
                        relativeDate += "h";
                        break;
                    case "second":
                    case "seconds":
                        relativeDate += "s";
                        break;
                    default:
                        relativeDate += tokens[1];
                }
            } else {
                relativeDate = relativeDateStr;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
