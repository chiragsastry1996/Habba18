package com.acharya.habba2k18.AboutUs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acharya.habba2k18.R;


public class AboutUsFragment extends Fragment {

    View view;
    RelativeLayout fragmentContainer;
    TextView title,body;
    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.aboutus_fragment_layout, container, false);


        title = view.findViewById(R.id.title);
        body = view.findViewById(R.id.body);
        fragmentContainer = view.findViewById(R.id.container);

        if(position == 0) title.setText("About Acharya");
        if(position == 1) title.setText("About Habba");

        setUpFragmentData();

        return view;
    }
    public void setUpFragmentData() {


    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}