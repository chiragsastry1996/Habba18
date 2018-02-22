package com.acharya.habba2k18.Events;

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

import com.acharya.habba2k18.Events.adapter.CarPagerAdapter;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
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
    float taptarget2;
    float taptarget4;
    float taptarget5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getSupportActionBar().hide();


        final Display display = getWindowManager().getDefaultDisplay();
        taptarget2 = display.getWidth()/3.5f;
        taptarget4 = display.getWidth()/1.45f;
        taptarget5 = display.getWidth()/1.1f;
        final Rect TapTargetbutton1 = new Rect(0,0,0,0);
        TapTargetbutton1.offset(display.getWidth()/9,display.getHeight());
        final Rect TapTargetbutton2 = new Rect(0,0,0,0);
        TapTargetbutton2.offset((int) taptarget2,display.getHeight());
        final Rect TapTargetbutton3 = new Rect(0,0,0,0);
        TapTargetbutton3.offset(display.getWidth()/2,display.getHeight());
        final Rect TapTargetbutton4 = new Rect(0,0,0,0);
        TapTargetbutton4.offset((int) taptarget4,display.getHeight());
        final Rect TapTargetbutton5 = new Rect(0,0,0,0);
        TapTargetbutton5.offset((int) taptarget5,display.getHeight());
        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        TapTarget.forBounds(TapTargetbutton1,"The First Button", "Click on this to go to the first category")
                        .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                        .outerCircleColor(R.color.colorAccent)
                        .targetCircleColor(R.color.colorPrimaryDark)
                                .targetRadius(50)
                        .transparentTarget(true)
                                .cancelable(false)
                        .textColor(R.color.colorPrimary)
                        .id(1),
                        TapTarget.forBounds(TapTargetbutton2,"The Second Button", "Click on this to skip three categories to the left")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(50)
                                .cancelable(false)
                                .textColor(R.color.colorPrimary)
                                .id(2),
                        TapTarget.forBounds(TapTargetbutton3,"The Third Button", "Click on this to go to the middle category ")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(50)
                                .cancelable(false)
                                .textColor(R.color.colorPrimary)
                                .id(3),
                        TapTarget.forBounds(TapTargetbutton4,"The Fourth Button", "Click on this to skip three categories to the right")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(50)
                                .cancelable(false)
                                .textColor(R.color.colorPrimary)
                                .id(4),
                        TapTarget.forBounds(TapTargetbutton5,"The Fifth Button", "Click on this to go to the last category")
                                .dimColor(android.R.color.holo_blue_dark)  //don't remove android
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(R.color.colorPrimaryDark)
                                .transparentTarget(true)
                                .targetRadius(50)
                                .cancelable(false)
                                .textColor(R.color.colorPrimary)
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
                });  SharedPreferences settings = getSharedPreferences(PREFS_EVENTS, 0);
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