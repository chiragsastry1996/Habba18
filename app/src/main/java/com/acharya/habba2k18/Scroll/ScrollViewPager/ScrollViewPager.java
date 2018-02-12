package com.acharya.habba2k18.Scroll.ScrollViewPager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.acharya.habba2k18.BlurBuilder;
import com.acharya.habba2k18.CardView.CardView;
import com.acharya.habba2k18.Events.Event;
import com.acharya.habba2k18.Events.adapter.CarPagerAdapter;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ScrollViewPager extends Fragment {
    private  View view;
    ViewPager mViewPager;
    public static String name;
    ScrollPageAdapter ScrollPagerAdapter;
    public static ArrayList<ArrayList<String>> scrollEvents;
    public int currentposition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_scroll_view_pager, container, false);

        name = getArguments().getString("category");
            currentposition = getArguments().getInt("position");


        scrollEvents = new ArrayList<>();

        for(HashMap.Entry<String,ArrayList<String>> entry : Test.subcatList.get(name).entrySet()){
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            scrollEvents.add(value);
            System.out.println("zzzz : " + key + " " + value);
        }

        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        //mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().widthPixels * -0.2));

        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override public void transformPage(View page, float position) {
                page.setScaleX(0.9f - Math.abs(position * 0.3f));
                page.setScaleY(0.9f - Math.abs(position * 0.1f));
                page.setAlpha(1.0f - Math.abs(position * 0.5f));
            }
        });
        mViewPager.setAdapter(ScrollPagerAdapter);
        mViewPager.setCurrentItem(currentposition,true);
        ScrollPagerAdapter = new ScrollPageAdapter(getFragmentManager());


        mViewPager.setAdapter(ScrollPagerAdapter);
        mViewPager.setCurrentItem(currentposition);

        return view;

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {



        final Activity activity = getActivity();
        final View content = activity.findViewById(android.R.id.content).getRootView();
        if (content.getWidth() > 0) {
            Bitmap image = BlurBuilder.blur(content);
            view.setBackground(new BitmapDrawable(activity.getResources(), image));
        } else {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Bitmap image = BlurBuilder.blur(content);
                    view.setBackground(new BitmapDrawable(activity.getResources(), image));
                }
            });
        }
    }

}
