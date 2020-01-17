package com.openclassrooms.realestatemanager.ui.activities.details;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.DetailsActivityPagerAdapter;
import com.openclassrooms.realestatemanager.ui.activities.addhouse.AddHouseActivity;
import com.openclassrooms.realestatemanager.ui.activities.modify.ModifyActivity;
import com.openclassrooms.realestatemanager.ui.activities.second.SecondActivity;
import com.openclassrooms.realestatemanager.ui.fragments.details.InfoFragment;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;


public class DetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailsActivityPagerAdapter pagerAdapter;
    private RealEstateManagerAPIService service = DI.getService();
    private InfoFragment infoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
        }

        configureAndShowDetailsFragment();

        setToolbar();

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);

        pagerAdapter = new DetailsActivityPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        service.setActivity(this, "Details");
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(service.getHouse().getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        MenuItem item = menu.findItem(R.id.modify);
        item.setVisible(true);
        if (infoFragment != null && infoFragment.isVisible()){
            menu.findItem(R.id.search).setVisible(true);
            menu.findItem(R.id.sync).setVisible(true);
        } else {
            menu.findItem(R.id.search).setVisible(false);
            menu.findItem(R.id.sync).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                Intent intent = new Intent(this, AddHouseActivity.class);
                startActivity(intent);
                AddModifyHouseHelper.setNewAdd(SaveToDatabase.getInstance(this));
                break;
            case R.id.modify:
                if (String.valueOf(service.getHouse().getAgentId()).equals(service.getUser().getUserId())) {
                    Intent modifyIntent = new Intent(this, ModifyActivity.class);
                    startActivity(modifyIntent);
                    AddModifyHouseHelper.setNull();
                }
               break;
            case android.R.id.home:
                Intent listIntent = new Intent(this, SecondActivity.class);
                startActivity(listIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void configureAndShowDetailsFragment() {
        infoFragment = (InfoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_info);
        if (infoFragment == null && findViewById(R.id.fragment_container_details) != null){
            infoFragment = new InfoFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_details, infoFragment).commit();
        }
    }

}
