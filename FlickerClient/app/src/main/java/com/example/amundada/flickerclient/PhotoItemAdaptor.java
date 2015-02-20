package com.example.amundada.flickerclient;

import android.content.Context;
import android.graphics.Point;
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
 * Created by amundada on 2/20/15.
 */
public class PhotoItemAdaptor extends ArrayAdapter<PhotoItem> {

    private int width;
    private int height;
    public PhotoItemAdaptor(Context context, List<PhotoItem> objects) {
        super(context, android.R.layout.simple_list_item_1 , objects);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.x;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhotoItem photo = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.phot_item, parent, false);
        }

        // get references to all components
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOwner = (TextView) convertView.findViewById(R.id.tvOwner);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);

        // clear previous image
        ivPhoto.setImageResource(0);
        Picasso.with(getContext()).load(photo.url).resize(width, height).into(ivPhoto);
        tvTitle.setText(photo.title);
        tvOwner.setText(photo.ownerName);
        //tvDescription.setText(photo.description);
        return convertView;
    }
}
