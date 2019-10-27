package com.openclassrooms.realestatemanager.ui.adapters.second;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.openclassrooms.realestatemanager.ui.fragments.second.DetailsFragment;
import com.openclassrooms.realestatemanager.ui.fragments.second.ListFragment;
import com.openclassrooms.realestatemanager.ui.fragments.second.MediaFragment;

public class SecondActivityPagerAdapter extends FragmentPagerAdapter {


    public SecondActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ListFragment.newInstance();
            case 1:
                return MediaFragment.newInstance();
            case 2:
                return DetailsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
