package com.acharya.habba2k18.Events;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.acharya.habba2k18.Events.adapter.CarPagerAdapter;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class Event extends AppCompatActivity {

    ViewPager mViewPager;
    CarPagerAdapter carPagerAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;
    public int currentposition;
    Button first, three_prev, middle, three_next, last;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }


        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        if (bundle != null) {
            currentposition = bundle.getInt("currentposition");
        }

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        carPagerAdapter = new CarPagerAdapter(getSupportFragmentManager());
//        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().widthPixels * -0.28));
//
//        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
//            @Override public void transformPage(View page, float position) {
//                page.setScaleX(0.9f - Math.abs(position * 0.3f));
//                page.setScaleY(0.9f - Math.abs(position * 0.1f));
//                page.setAlpha(1.0f - Math.abs(position * 0.5f));
//            }
//        });
//        mViewPager.setAdapter(carPagerAdapter);
//        mViewPager.setCurrentItem(currentposition,true);

        mViewPager.setPageTransformer(true, new CardStackTransformer());

        mViewPager.setOffscreenPageLimit(7);

        mViewPager.setAdapter(carPagerAdapter);

        mViewPager.setCurrentItem(currentposition, true);

        first = (Button) findViewById(R.id.first);
        three_prev = (Button) findViewById(R.id.back3);
        middle = (Button) findViewById(R.id.middle);
        three_next = (Button) findViewById(R.id.jump3);
        last = (Button) findViewById(R.id.last);

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // carPagerAdapter.getCount() - 1 because, of using Constant.mutedColor[position] + 1
//                if (position < carPagerAdapter.getCount() - 1) {
//                            Glide.with(getBaseContext()).load(Test.eventList.get(position).get(2)).apply(bitmapTransform(new BlurTransformation(40))).into(new SimpleTarget<Drawable>() {
//                                @Override
//                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                        mViewPager.setBackground(resource);
//                     }
//                    }
//                 });
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0,true);
            }
        });

        three_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewPager.getCurrentItem();
                if(position-3 <= 0)
                mViewPager.setCurrentItem(0,true);
                else  mViewPager.setCurrentItem(position-3,true);
            }
        });
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem((Test.eventList.size()-1)/2,true);
            }
        });
        three_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mViewPager.getCurrentItem();
                if(position+3 >= Test.eventList.size()-1)
                    mViewPager.setCurrentItem(Test.eventList.size()-1,true);
                else  mViewPager.setCurrentItem(position+3,true);
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Test.eventList.size()-1,true);
            }
        });

    }


    private class CardStackTransformer implements ViewPager.PageTransformer
    {
        @Override
        public void transformPage(View page, float position)
        {
            if(position>=0)
            {

                page.setScaleX(1f - 0.02f * position);

                page.setScaleY(1f);

                page.setTranslationX(- page.getWidth()*position);

                page.setTranslationY(30 * position);

                page.setAlpha(1.0f - Math.abs(position * 0.25f));
            }

        }
    }


    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(Event.this, MainActivity.class);
        startActivity(i8);
        finish();
    }
}