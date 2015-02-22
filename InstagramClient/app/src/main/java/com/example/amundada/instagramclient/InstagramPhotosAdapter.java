package com.example.amundada.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
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
    private  com.squareup.picasso.Transformation PhotoTransformation;
    private  com.squareup.picasso.Transformation ProfileTransformation;

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        setPhotoSize();

        PhotoTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(3)
                .oval(false)
                .build();

        ProfileTransformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(1)
                .cornerRadiusDp(20)
                .oval(true)
                .build();
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

        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvProfileName = (TextView) convertView.findViewById(R.id.tvProfileName);
        TextView tvPostDate = (TextView) convertView.findViewById(R.id.tvPostDate);

        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvComment1 = (TextView) convertView.findViewById(R.id.tvComment1);
        TextView tvComment2 = (TextView) convertView.findViewById(R.id.tvComment2);

        tvCaption.setText(photo.caption);
        ivPhoto.setImageResource(0);
        tvComment1.setText(photo.comment1);
        tvComment2.setText(photo.comment2);
        tvProfileName.setText(photo.userName);
        tvPostDate.setText("\u2764" + " " + photo.likeCount);

        //Picasso.with(getContext()).load(photo.imageUrl).resize(width, height).placeholder(R.drawable.ic_placeholder).into(ivPhoto);
        Picasso.with(getContext()).load(photo.imageUrl)
                .transform(PhotoTransformation)
                .resize(width,height)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivPhoto);

        Picasso.with(getContext()).load(photo.profileUrl)
                .transform(ProfileTransformation)
                .placeholder(R.drawable.ic_placeholder)
                .into(ivProfile);

        return convertView;
    }
}
