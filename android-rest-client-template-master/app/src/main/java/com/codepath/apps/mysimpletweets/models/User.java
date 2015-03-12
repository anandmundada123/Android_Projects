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

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.userName = json.getString("name");
            user.id = json.getLong("id");
            user.screen_name = json.getString("screen_name");
            user.profileUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
