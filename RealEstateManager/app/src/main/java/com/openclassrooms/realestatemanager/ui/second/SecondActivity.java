package com.openclassrooms.realestatemanager.ui.second;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.addhouse.AddHouseActivity;
import com.openclassrooms.realestatemanager.ui.fragments.second.ListFragment;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userName, userEmail;
    private ImageView userPhoto;
    private RealEstateManagerAPIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        service = DI.getService();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        userName = navigationView.getHeaderView(0).findViewById(R.id.user_name_nav_text);
        userEmail = navigationView.getHeaderView(0).findViewById(R.id.textView);
        userPhoto = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        changeFragment(new ListFragment(), "ListFragment");

        userName.setText(service.getUser().getName());
        userEmail.setText(service.getUser().getEmail());
        if (service.getUser().getPhotoUri().contains("google")) {
            Glide.with(this).load(service.getUser().getPhotoUri()).apply(RequestOptions.circleCropTransform()).into(userPhoto);
        } else {
            Glide.with(this).load(service.getUser().getPhotoUri() + "?" + "type=large")
                    .apply(RequestOptions.circleCropTransform()).into(userPhoto);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment, String value) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_second, fragment, value).addToBackStack(value).commit();

    }

}
