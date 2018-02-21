package com.acharya.habba2k18.Scroll.ScrollViewPager;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.acharya.habba2k18.BlurBuilder;
import com.acharya.habba2k18.CardView.CardView;
import com.acharya.habba2k18.Events.Event;
import com.acharya.habba2k18.Events.adapter.CarPagerAdapter;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

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
//                Glide.with(getContext())
//                        .load(scrollEvents.get(position).get(2))
//                        .apply(new RequestOptions().placeholder(R.drawable.dynamic_placeholder))
//                        .into(imageView);

                animate(imageView,0,false);
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

    private void animate(final ImageView imageView,final int imageIndex, final boolean forever) {

        //imageView <-- The View which displays the images
        //images[] <-- Holds R references to the images to display
        //imageIndex <-- index of the first image to show in images[]
        //forever <-- If equals true then after the last image it starts all over again with the first image resulting in an infinite loop. You have been warned.

        int fadeInDuration = 500; // Configure time values here
        int timeBetween = 3000;
        int fadeOutDuration = 1000;

        imageView.setVisibility(View.INVISIBLE);    //Visible or invisible by default - this will apply when the animation ends
        Glide.with(getContext())
                .load(scrollEvents.get(imageIndex).get(2))
                .apply(new RequestOptions().placeholder(R.drawable.dynamic_placeholder))
                .into(imageView);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); // add this
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false); // change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (scrollEvents.size() - 1 > imageIndex) {
                    animate(imageView, imageIndex + 1,forever); //Calls itself until it gets to the end of the array
                }
                else {
                    if (forever){
                        animate(imageView, 0,forever);  //Calls itself to start the animation all over again in a loop if forever = true
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
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
