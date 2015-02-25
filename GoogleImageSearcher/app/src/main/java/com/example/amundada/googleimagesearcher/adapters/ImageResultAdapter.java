package com.example.amundada.googleimagesearcher.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amundada.googleimagesearcher.R;
import com.example.amundada.googleimagesearcher.models.ImageResults;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by amundada on 2/24/15.
 */
public class ImageResultAdapter extends ArrayAdapter<ImageResults> {

    public ImageResultAdapter(Context context, List<ImageResults> objects) {
        super(context, R.layout.item_image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageResults image = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);

        ivImage.setImageResource(0);
        tvTitle.setText(Html.fromHtml(image.title));
        Picasso.with(getContext()).load(image.tbUrl).into(ivImage);
        return convertView;
    }
}
