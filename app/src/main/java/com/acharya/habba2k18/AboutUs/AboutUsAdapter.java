package com.acharya.habba2k18.AboutUs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AboutUsAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT = 2;

    public AboutUsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new AboutUsFragment().setFragmentPosition(position);
    }

    @Override
    public int getCount() {
        PAGE_COUNT = 2;
        return PAGE_COUNT;
    }
}