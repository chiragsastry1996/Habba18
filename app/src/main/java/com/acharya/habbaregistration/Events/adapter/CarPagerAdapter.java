package com.acharya.habbaregistration.Events.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.acharya.habbaregistration.Events.fragments.CarFragment;
import com.acharya.habbaregistration.Test.Test;

public class CarPagerAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT = 3;

    public CarPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new CarFragment().setFragmentPosition(position);
    }

    @Override
    public int getCount() {
         PAGE_COUNT= Test.eventList.size();
        return PAGE_COUNT;
    }
}