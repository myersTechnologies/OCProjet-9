package com.openclassrooms.realestatemanager.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected abstract void configureAndShowMediaFragment();
    protected abstract void configureAndShowDetailsFragment();
    protected abstract void configureAndShowListFragment();
}
