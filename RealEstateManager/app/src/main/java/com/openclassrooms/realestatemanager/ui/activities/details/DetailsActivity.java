package com.openclassrooms.realestatemanager.ui.activities.details;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.DetailsActivityPagerAdapter;
import com.openclassrooms.realestatemanager.ui.activities.addhouse.AddHouseActivity;
import com.openclassrooms.realestatemanager.ui.activities.modify.ModifyActivity;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;


public class DetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsActivityPagerAdapter pagerAdapter;
    private RealEstateManagerAPIService service = DI.getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(service.getHouse().getName());
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);
        pagerAdapter = new DetailsActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        service.setActivity(this, "Details");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        MenuItem item = menu.findItem(R.id.modify);
        item.setVisible(true);
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
                if (String.valueOf(service.getHouse().getAgentId()).equals(service.getUser().getUserId())) {
                    Intent modifyIntent = new Intent(this, ModifyActivity.class);
                    startActivity(modifyIntent);
                }
                return true;
            case R.id.search:

                return true;
            case android.R.id.home:
                Intent listIntent = new Intent(this, SecondActivity.class);
                startActivity(listIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
