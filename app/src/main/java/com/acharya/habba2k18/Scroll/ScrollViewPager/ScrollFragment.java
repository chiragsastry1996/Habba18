package com.acharya.habba2k18.Scroll.ScrollViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acharya.habba2k18.R;
import com.acharya.habba2k18.Test.Test;
import com.bumptech.glide.Glide;



public class ScrollFragment extends Fragment{
    int position;
    View view;
    Button button;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ImageView imageView;
    private static String name;
    private static String about;
    private static String rules;
    private static String amount;
    private static String image;
    private static String number;
    private static String ename;
    private static String prizemoney;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.scroll_fragment_layout, container, false);

        tv1=(TextView)view.findViewById(R.id.name);
        tv2=(TextView)view.findViewById(R.id.about);
        tv3=(TextView)view.findViewById(R.id.rules);
        tv4=(TextView)view.findViewById(R.id.amount);
        tv5=(TextView)view.findViewById(R.id.number);
        tv6=(TextView)view.findViewById(R.id.ename);
        tv7=(TextView)view.findViewById(R.id.pmoney);
        imageView = (ImageView)view.findViewById(R.id.imageView);

        button = view.findViewById(R.id.button3);
        setUpFragmentData();
        return view;
    }

    public void setUpFragmentData() {
        name = ScrollViewPager.scrollEvents.get(position).get(1);
        about = ScrollViewPager.scrollEvents.get(position).get(3);
        rules = ScrollViewPager.scrollEvents.get(position).get(6);
        number = ScrollViewPager.scrollEvents.get(position).get(7);
        ename = ScrollViewPager.scrollEvents.get(position).get(8);
        image = ScrollViewPager.scrollEvents.get(position).get(2);
        amount = ScrollViewPager.scrollEvents.get(position).get(4);
        prizemoney = ScrollViewPager.scrollEvents.get(position).get(9);

        tv1.setText(name);
        tv2.setText(about);
        tv3.setText(rules);
        tv4.setText(amount);
        tv5.setText(number);
        tv6.setText(ename);
        tv7.setText(prizemoney);
        Glide.with(getContext()).load(image).into(imageView);
    }

    public Fragment setFragmentPosition(int position) {
        this.position = position;
        return this;
    }



}