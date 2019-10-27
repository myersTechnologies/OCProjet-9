package com.openclassrooms.realestatemanager.ui.adapters.details;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.realestatemanager.ui.fragments.details.InfoFragment;
import com.openclassrooms.realestatemanager.ui.fragments.details.MediaFragment;

public class DetailsActivityPagerAdapter extends FragmentPagerAdapter {


    public DetailsActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return  MediaFragment.newInstance();
            case 1:
                return InfoFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
