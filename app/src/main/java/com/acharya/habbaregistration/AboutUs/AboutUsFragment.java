package com.acharya.habbaregistration.AboutUs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acharya.habbaregistration.R;


public class AboutUsFragment extends Fragment {

    View view;
    RelativeLayout fragmentContainer;
    TextView body;
    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.aboutus_fragment_layout, container, false);

        body = view.findViewById(R.id.body);
        if(position == 0) body.setText(R.string.About_Habba);
        if(position == 1) body.setText(R.string.About_Acharya);
        fragmentContainer = view.findViewById(R.id.container);

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