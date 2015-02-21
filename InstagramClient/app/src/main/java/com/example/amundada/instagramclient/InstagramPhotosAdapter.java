package com.example.amundada.instagramclient;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private int width;
    private int height;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        setPhotoSize();
    }

    private void setPhotoSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.x;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photos, parent, false);
        }

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);
        tvComment1.setText(photo.comment1);
        tvComment2.setText(photo.comment2);

        Picasso.with(getContext()).load(photo.imageUrl).resize(width, height).placeholder(R.drawable.ic_placeholder).into(ivPhoto);
        return convertView;
    }
}
