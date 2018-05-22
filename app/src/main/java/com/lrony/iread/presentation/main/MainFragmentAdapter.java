package com.lrony.iread.presentation.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lrony.iread.presentation.main.local.LocalFragment;
import com.lrony.iread.presentation.main.online.OnlineFragment;

/**
 * Created by Lrony on 18-5-22.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    public MainFragmentAdapter(FragmentManager fm, String... titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return LocalFragment.newInstance();
        } else if (position == 1) {
            return OnlineFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
