package com.acharya.habba2k18.Maps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.acharya.habba2k18.Test.Test;


public class MapsAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT = 3;

    public MapsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MapsFragment().setFragmentPosition(position);
    }

    @Override
    public int getCount() {
        PAGE_COUNT= Test.eventList.size();
        return PAGE_COUNT;
    }
}