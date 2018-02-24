package com.acharya.habbaregistration.IntroSlider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



public class IntroAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT = 4;

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new IntroFragment().setFragmentPosition(position);
    }

    @Override
    public int getCount() {
        PAGE_COUNT=4;
        return PAGE_COUNT;
    }
}
