package com.acharya.habbaregistration.Scroll.ScrollViewPager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.acharya.habbaregistration.BlurBuilder;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.Test;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class ScrollViewPager extends Fragment {
    private  View view;
    ViewPager mViewPager;
    public static String name;
    ImageView imageView;
    ScrollPageAdapter ScrollPagerAdapter;
    public static ArrayList<ArrayList<String>> scrollEvents;
    public int currentposition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_scroll_view_pager, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        imageView = (ImageView)view.findViewById(R.id.imageView);

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
                page.setScaleX(1f - Math.abs(position * 0.3f));
                page.setScaleY(1f - Math.abs(position * 0.1f));
               // page.setAlpha(1.0f - Math.abs(position * 0.5f));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Glide.with(getContext())
                        .load(scrollEvents.get(position).get(2))
                        .transition(DrawableTransitionOptions.withCrossFade(500))
                        .into(imageView);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
