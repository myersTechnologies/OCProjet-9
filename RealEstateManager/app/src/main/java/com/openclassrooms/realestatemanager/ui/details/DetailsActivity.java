package com.openclassrooms.realestatemanager.ui.details;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.details.DetailsActivityPagerAdapter;
import com.openclassrooms.realestatemanager.ui.addhouse.AddHouseActivity;


public class DetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsActivityPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);
        pagerAdapter = new DetailsActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(this, AddHouseActivity.class);
                startActivity(intent);
                return true;
            case R.id.modify:
                return true;
            case R.id.search:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
