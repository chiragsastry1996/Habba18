package com.acharya.habbaregistration.Scroll.ScrollViewPager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class ScrollPageAdapter extends FragmentStatePagerAdapter {

    public static int PAGE_COUNT;

    public ScrollPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ScrollFragment().setFragmentPosition(position);
    }

    @Override
    public int getCount() {
        PAGE_COUNT= ScrollViewPager.scrollEvents.size();
        return PAGE_COUNT;
    }
}