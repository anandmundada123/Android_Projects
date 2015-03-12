package com.example.amundada.simplechat1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.amundada.simplechat1.R;

import java.util.List;

/**
 * Created by amundada on 3/4/15.
 */
public class ChatListAdapter extends ArrayAdapter<Message>{

    public ChatListAdapter(Context context, List<Message> objects) {
        super(context, R.layout.chat_item, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message m = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_item, parent, false);
        }
        TextView tvchatMessage = (TextView)convertView.findViewById(R.id.tvchatMessage);
        tvchatMessage.setText(m.message);
        return convertView;
    }
}
