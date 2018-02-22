package com.acharya.habba2k18.IntroSlider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.bumptech.glide.Glide;

public class IntroSlider extends AppCompatActivity {

    ViewPager mViewPager;
    IntroAdapter introAdapter;
    Button next_intro;
    final String PREFS_EVENTS = "MyIntroFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences settings = getSharedPreferences(PREFS_EVENTS, 0);
        if (settings.getBoolean("my_first_time", true)) {
            settings.edit().putBoolean("my_first_time", false).apply();
        }
        else {
            Intent intent = new Intent(IntroSlider.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

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
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
}
