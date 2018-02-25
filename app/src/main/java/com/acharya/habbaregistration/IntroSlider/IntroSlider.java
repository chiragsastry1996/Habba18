package com.acharya.habbaregistration.IntroSlider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.acharya.habbaregistration.MainMenu.MainActivity;
import com.acharya.habbaregistration.R;

public class IntroSlider extends AppCompatActivity {

    ViewPager mViewPager;
    IntroAdapter introAdapter;
    Button next_intro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        overridePendingTransitionEnter();
        setContentView(R.layout.activity_intro_slider);

        getSupportActionBar().hide();

        next_intro = findViewById(R.id.next_intro);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        introAdapter = new IntroAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(introAdapter);

        next_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < 4) {
                    mViewPager.setCurrentItem(current);
                } else {
                    Intent intent = new Intent(IntroSlider.this, MainActivity.class);
                    overridePendingTransitionExit();
                    startActivity(intent);

                    finish();
                }
            }
        });

    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onBackPressed() {

    }

}
