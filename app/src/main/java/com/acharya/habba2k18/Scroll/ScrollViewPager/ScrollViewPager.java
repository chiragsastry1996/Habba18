package com.acharya.habba2k18.Scroll.ScrollViewPager;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.acharya.habba2k18.CardView.CardView;
import com.acharya.habba2k18.Events.Event;
import com.acharya.habba2k18.Events.adapter.CarPagerAdapter;
import com.acharya.habba2k18.MainMenu.MainActivity;
import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class ScrollViewPager extends AppCompatActivity {
    ViewPager mViewPager;
    public static String name;
    ScrollPageAdapter ScrollPagerAdapter;
    public static ArrayList<ArrayList<String>> scrollEvents;
    public int currentposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view_pager);
        getSupportActionBar().hide();

        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        if (bundle != null) {
            name = bundle.getString("category");
            currentposition = bundle.getInt("position");
        }

        scrollEvents = new ArrayList<>();

        for(HashMap.Entry<String,ArrayList<String>> entry : Test.subcatList.get(name).entrySet()){
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            scrollEvents.add(value);
            System.out.println("zzzz : " + key + " " + value);
        }

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(2);
        ScrollPagerAdapter = new ScrollPageAdapter(getSupportFragmentManager());


        mViewPager.setAdapter(ScrollPagerAdapter);
        mViewPager.setCurrentItem(currentposition);

    }

    @Override
    public void onBackPressed() {
        Intent i8 = new Intent(ScrollViewPager.this, CardView.class);
        startActivity(i8);
        finish();
    }
}
