package com.acharya.habba2k18.MainMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acharya.habba2k18.R;
import com.bumptech.glide.Glide;
import com.special.ResideMenu.ResideMenu;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.IOException;

public class HomeFragment extends Fragment {

    private View parentView;
    private ImageView imageView1;
    private CarouselView carouselView;

    private String[] sampleImages = {"http://acharyahabba.in/habba18/images/slider1.jpg","http://acharyahabba.in/habba18/images/slider2.jpg","http://acharyahabba.in/habba18/images/slider3.jpg","http://acharyahabba.in/habba18/images/slider4.jpg"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        setUpViews();
        return parentView;

    }

    private void setUpViews() {
        MainActivity parentActivity = (MainActivity) getActivity();

        carouselView = (CarouselView) parentView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(getContext()).load(sampleImages[position]).into(imageView);
            }
        });

        imageView1 = (ImageView)parentView.findViewById(R.id.imageView11);
        try {
            final pl.droidsonroids.gif.GifDrawable gifFromResource = new pl.droidsonroids.gif.GifDrawable( getResources(), R.drawable.image_gif);
            imageView1.setImageDrawable(gifFromResource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
