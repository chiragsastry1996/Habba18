package com.acharya.habbaregistration.IntroSlider;


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

import java.io.IOException;


public class IntroFragment extends Fragment{

    View view;
    ImageView image_intro;
    RelativeLayout fragmentContainer;
    TextView title_intro,title_body;
    int position;

    private int[] layouts = new int[]{
        R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3,
                R.layout.welcome_slide4};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(layouts[position], container, false);

        image_intro = view.findViewById(R.id.image_intro);
//        title_intro = view.findViewById(R.id.title_intro);
//        title_body = view.findViewById(R.id.title_body);
        fragmentContainer = view.findViewById(R.id.container);
        setUpFragmentData();
        return view;
    }

    public void setUpFragmentData() {

        try {
            if(position == 0){
                final pl.droidsonroids.gif.GifDrawable gifFromResource = new pl.droidsonroids.gif.GifDrawable( getResources(), R.drawable.intro1);
                image_intro.setImageDrawable(gifFromResource);
            }
            if(position == 1) {
                final pl.droidsonroids.gif.GifDrawable gifFromResource = new pl.droidsonroids.gif.GifDrawable( getResources(), R.drawable.intro2);
                image_intro.setImageDrawable(gifFromResource);
            }
            if(position == 2) {
                final pl.droidsonroids.gif.GifDrawable gifFromResource = new pl.droidsonroids.gif.GifDrawable( getResources(), R.drawable.intro3);
                image_intro.setImageDrawable(gifFromResource);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}