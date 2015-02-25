package com.example.amundada.googleimagesearcher.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by amundada on 2/24/15.
 */
public class ImageResults {
    public String url;
    public String tbUrl;
    public String title;

    private static ArrayList<ImageResults> getImages(JSONArray results) {
        ArrayList<ImageResults> images = new ArrayList<ImageResults>();
        for (int i = 0; i < results.length(); i++) {
            try {
                ImageResults image = new ImageResults();
                JSONObject result = results.getJSONObject(i);
                image.url = result.getString("url");
                image.tbUrl = result.getString("tbUrl");
                image.title = result.getString("title");
                images.add(image);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return images;
    }
    public static ArrayList<ImageResults> getImageResultsFromJson(JSONObject response) {

        try {
            JSONArray resultsJson = response.getJSONObject("responseData").getJSONArray("results");
            return getImages(resultsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
