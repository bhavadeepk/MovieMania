package com.bhavadeep.moviemania;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by bhava on 5/25/2017.
 */
public class PlaceSlidesFragmentAdapter extends FragmentPagerAdapter  {





 //   protected static final int[] ICONS = new int[] { R.drawable.marker,
   //         R.drawable.marker, R.drawable.marker, R.drawable.marker };

    private int mCount ;

    public PlaceSlidesFragmentAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return mCount;
    }



    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}