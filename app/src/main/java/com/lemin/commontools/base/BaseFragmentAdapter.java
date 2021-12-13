package com.lemin.commontools.base;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * To Change The World
 * 2018/7/14 16:34:06
 * Created by Mr.Wang
 */

public class BaseFragmentAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public BaseFragmentAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
