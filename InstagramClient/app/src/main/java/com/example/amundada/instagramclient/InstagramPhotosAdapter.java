package com.example.amundada.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by amundada on 2/18/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    // Needs context ==> Activity
    // Needs Source
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);

        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        return convertView;
    }
}
