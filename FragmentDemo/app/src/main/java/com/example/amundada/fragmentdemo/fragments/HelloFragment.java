package com.example.amundada.fragmentdemo.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.amundada.fragmentdemo.R;

public class HelloFragment extends Fragment {

    private FragmentListener listener;
    private EditText etMessage;
    private Button btSubmit;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof FragmentListener) {
            listener = (FragmentListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hello, container, false);
        etMessage = (EditText)v.findViewById(R.id.etMessage);
        btSubmit = (Button)v.findViewById(R.id.btSubmit);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonclick(etMessage.getText().toString());
            }
        });
        return v;
    }

    public interface FragmentListener {
        public void onButtonclick(String text);
    }
}
