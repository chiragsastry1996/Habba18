package com.acharya.habbaregistration.Events.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acharya.habbaregistration.CardView.CardView;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.Test;
import com.bumptech.glide.Glide;


public class CarFragment extends Fragment{

    View view;
    ImageView carImage,logo;
    RelativeLayout fragmentContainer;
    TextView carName,eventNumber;
    Button button;
    int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.car_fragment_layout, container, false);

        carImage = view.findViewById(R.id.car_image);
        carName = view.findViewById(R.id.car_name);
        eventNumber = view.findViewById(R.id.number_events);
        logo = view.findViewById(R.id.logo);
        fragmentContainer = view.findViewById(R.id.container);

        button = view.findViewById(R.id.button3);
        setUpFragmentData();
        return view;
    }

    public void setUpFragmentData() {

        Glide.with(getContext())
                .load(Test.eventList.get(position).get(2))
                .into(carImage);

        Glide.with(getContext())
                .load(Test.eventList.get(position).get(3))
                .into(logo);
        final String name = Test.eventList.get(position).get(1);
                carName.setText(name);

        String numberOfEvents = String.valueOf(Test.subcatList.get(name).size());
        if(Integer.parseInt(numberOfEvents)<10)
                eventNumber.setText("0"+numberOfEvents + " Events");
        else eventNumber.setText(numberOfEvents + " Events");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.equals("Dance"))
                {
                    String url = "http://dancetranceindia.com/";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else {
                    Intent intent1 = new Intent(getActivity(), CardView.class);
                    intent1.putExtra("main_category", Test.eventList.get(position).get(1));
                    intent1.putExtra("image_url", Test.eventList.get(position).get(2));
                    startActivity(intent1);
                }


            }
        });
    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}