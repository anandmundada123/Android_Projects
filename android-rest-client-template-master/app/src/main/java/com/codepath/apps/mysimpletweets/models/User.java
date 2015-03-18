package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by amundada on 3/7/15.
 */
public class User {

    private String userName;
    private long id;
    private String screen_name;
    private String profileUrl;
    private String tagLine;
    private int followerCount;
    private int followingCount;

    public String getUserName() {
        return userName;
    }

    public long getId() {
        return id;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getTagLine() {
        return tagLine;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {

            user.userName = json.getString("name");
            user.id = json.getLong("id");
            user.screen_name = json.getString("screen_name");
            user.profileUrl = json.getString("profile_image_url");
            user.followerCount = json.getInt("followers_count");
            user.followingCount = json.getInt("friends_count");
            user.tagLine = json.getString("description");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
