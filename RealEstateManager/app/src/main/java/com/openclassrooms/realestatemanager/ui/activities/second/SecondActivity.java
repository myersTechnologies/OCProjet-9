package com.openclassrooms.realestatemanager.ui.activities.second;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.activities.addhouse.AddHouseActivity;
import com.openclassrooms.realestatemanager.ui.activities.analitycs.AnalitycsActivity;
import com.openclassrooms.realestatemanager.ui.activities.map.MapActivity;
import com.openclassrooms.realestatemanager.ui.activities.modify.ModifyActivity;
import com.openclassrooms.realestatemanager.ui.activities.search.SearchActivity;
import com.openclassrooms.realestatemanager.ui.activities.settings.Settings;
import com.openclassrooms.realestatemanager.ui.fragments.map.MapFragment;
import com.openclassrooms.realestatemanager.ui.fragments.search.SearchFragment;
import com.openclassrooms.realestatemanager.ui.fragments.second.ListFragment;
import com.openclassrooms.realestatemanager.utils.Utils;


public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView userName, userEmail;
    private ImageView userPhoto, imageHeader;
    private RealEstateManagerAPIService service;
    private  Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
        }

        service = DI.getService();
        service.setActivity(this, "Second");

        toolbar = findViewById(R.id.toolbar);
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
        imageHeader = navigationView.getHeaderView(0).findViewById(R.id.header_image_view);

       changeFragment(new ListFragment(), "ListFragment");


        userEmail.setText(service.getUser().getEmail());

        userName.setText(service.getPreferences().getUserName());
        userEmail.setText(service.getUser().getEmail());

        //check kind of photo
        if (service.getPreferences().getUserPhoto() != null) {
                Glide.with(this).load(Uri.parse(service.getPreferences().getUserPhoto())).apply(RequestOptions.circleCropTransform()).into(userPhoto);
        } else {
            if (service.getUser().getPhotoUri().contains("google")) {
                Glide.with(this).load(service.getUser().getPhotoUri()).apply(RequestOptions.circleCropTransform()).into(userPhoto);
            } else {
                Glide.with(this).load(service.getUser().getPhotoUri() + "?" + "type=large")
                            .apply(RequestOptions.circleCropTransform()).into(userPhoto);
                }
            }
        //check if user set a custom menu image
            if (service.getPreferences().getMenuImage() != null) {
                Glide.with(this).load(Uri.parse(service.getPreferences().getMenuImage())).into(imageHeader);
            } else {
                Glide.with(this).load(R.drawable.main_image).into(imageHeader);
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            changeFragment(new ListFragment(), "ListFragment");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        if(findViewById(R.id.fragment_container_media) != null){
            menu.findItem(R.id.modify).setVisible(true);
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
                return true;
            case R.id.modify:
                if (service.getHouse() != null) {
                    if (String.valueOf(service.getHouse().getAgentId()).equals(service.getUser().getUserId())) {
                        Intent modifyIntent = new Intent(this, ModifyActivity.class);
                        startActivity(modifyIntent);
                    }
                }
                return true;
            case R.id.search:
                if (findViewById(R.id.fragment_container_media) == null) {
                    changeFragment(new SearchFragment(), "Search");
                } else {
                    Intent searchIntent = new Intent(this, SearchActivity.class);
                    startActivity(searchIntent);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:
                changeFragment(new ListFragment(), "ListFragment");
                break;
            case R.id.nav_camera:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 100);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 105);
                }
                break;
            case R.id.nav_gallery:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 90);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 95);
                }
                break;
            case R.id.nav_analytics:
                Intent analitycs = new Intent(this, AnalitycsActivity.class);
                startActivity(analitycs);
                break;
            case R.id.nav_tools:
                Intent settings = new Intent(this, Settings.class);
                startActivity(settings);
                break;
            case R.id.nav_map:
                if (Utils.isInternetAvailable(this)) {
                    if (findViewById(R.id.fragment_container_media) == null) {
                        changeFragment(new MapFragment(), "MapFragment");
                    }else {
                        Intent mapIntent = new Intent(this, MapActivity.class);
                        startActivity(mapIntent);
                    }
                }
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment, String value) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_list, fragment, value).addToBackStack(value).commit();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 105){
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 95){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }
    }

}
