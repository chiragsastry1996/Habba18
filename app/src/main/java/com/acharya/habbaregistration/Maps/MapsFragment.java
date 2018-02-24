package com.acharya.habbaregistration.Maps;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.Test;
import com.bumptech.glide.Glide;

public class MapsFragment extends Fragment {

    View view;
    ImageView carImage;
    RelativeLayout fragmentContainer;
    TextView carName;
    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.maps_fragment_layout, container, false);

        carImage = view.findViewById(R.id.background);
        carName = view.findViewById(R.id.car_name);
        fragmentContainer = view.findViewById(R.id.container);

        setUpFragmentData();
        return view;
    }

    public void setUpFragmentData() {

        Glide.with(getContext())
                .load(Test.eventList.get(position).get(2))
                .into(carImage);

        final String name = Test.eventList.get(position).get(1);
        carName.setText(name);

    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}
