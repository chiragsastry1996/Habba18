package com.acharya.habbaregistration.Events;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.acharya.habbaregistration.Events.adapter.CarPagerAdapter;
import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;
import com.acharya.habbaregistration.Test.Test;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class Event extends AppCompatActivity {

    ViewPager mViewPager;
    CarPagerAdapter carPagerAdapter;
    private FirebaseAnalytics mFirebaseAnalytics;
    public int currentposition;
    Button first, three_prev, middle, three_next, last;
    final String PREFS_EVENTS = "MyEventsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_event);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportActionBar().hide();
        first = (Button) findViewById(R.id.first);
        three_prev = (Button) findViewById(R.id.back3);
        middle = (Button) findViewById(R.id.middle);
        three_next = (Button) findViewById(R.id.jump3);
        last = (Button) findViewById(R.id.last);
        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(first,"The First Button", "Click on this to go to the first category")
                        .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                        .outerCircleColor(R.color.tap)
                        .targetCircleColor(R.color.colorPrimaryDark)
                                .targetRadius(30)
                        .transparentTarget(true)
                                .cancelable(false)
                        .textColor(R.color.white)
                        .id(1),
                        TapTarget.forView(three_prev,"The Second Button", "Click on this to skip three categories to the left")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.tap)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(30)
                                .cancelable(false)
                                .textColor(R.color.white)
                                .id(2),
                        TapTarget.forView(middle,"The Third Button", "Click on this to go to the middle category ")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.tap)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(30)
                                .cancelable(false)
                                .textColor(R.color.white)
                                .id(3),
                        TapTarget.forView(three_next,"The Fourth Button", "Click on this to skip three categories to the right")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.tap)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(30)
                                .cancelable(false)
                                .textColor(R.color.white)
                                .id(4),
                        TapTarget.forView(last,"The Fifth Button", "Click on this to go to the last category")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.tap)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(30)
                                .cancelable(false)
                                .textColor(R.color.white)
                                .id(5)

                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        //do nothing
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                    Log.d("TapTargetView","Clicked on"+lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                    final AlertDialog dialog = new AlertDialog.Builder(Event.this)
                            .setTitle("Uh oh")
                            .setMessage("You canceled the sequence")
                            .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });
        SharedPreferences settings = getSharedPreferences(PREFS_EVENTS, 0);
        if (settings.getBoolean("my_first_time", true)) {
            sequence.start();
            settings.edit().putBoolean("my_first_time", false).apply();
        }


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
            try{
                int position = mViewPager.getCurrentItem();
                if(position-3 <= 0)
                    mViewPager.setCurrentItem(0,true);
                else  mViewPager.setCurrentItem(position-3,true);
            }catch (Exception e) {
                mViewPager.setCurrentItem(0,true);
            }

            }
        });
        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                mViewPager.setCurrentItem((Test.eventList.size()-1)/2,true);
            }catch (Exception e) {
                mViewPager.setCurrentItem(0,true);
            }

            }
        });
        three_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                int position = mViewPager.getCurrentItem();
                if(position+3 >= Test.eventList.size()-1)
                    mViewPager.setCurrentItem(Test.eventList.size()-1,true);
                else  mViewPager.setCurrentItem(position+3,true);
            }catch (Exception e) {
                mViewPager.setCurrentItem(0,true);
            }

            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try{
                mViewPager.setCurrentItem(Test.eventList.size()-1,true);
            }catch (Exception e) {
                mViewPager.setCurrentItem(0,true);
            }

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
        overridePendingTransitionExit();
        finish();
    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}