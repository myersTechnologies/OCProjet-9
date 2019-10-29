package com.openclassrooms.realestatemanager.ui.modify;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.modify.ModifyAdapter;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {

    private RecyclerView modifyHouseList;
    private LinearLayoutManager layoutManager;
    private ModifyAdapter adapter;
    private RealEstateManagerAPIService service;
    private LinkedHashMap<String, List<String>> listHouseDetails;
    private LinkedHashMap<String, String> houseDescription;
    private House house;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        service = DI.getService();
        Toolbar toolbar = findViewById(R.id.toolbar_modify_house);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        modifyHouseList = findViewById(R.id.modify_list);
        layoutManager = new LinearLayoutManager(this);
        modifyHouseList.setLayoutManager(layoutManager);
        adapter = new ModifyAdapter(service.getHouse());
        modifyHouseList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_house_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.confirm_add:
                Intent intent = new Intent(this, DetailsActivity.class);
                startActivity(intent);
                getViewsAndAddHouse();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getViewsAndAddHouse() {

        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < service.getHouse().getImages().size(); i++) {
            Photo photo = service.getHouse().getImages().get(i);
            photos.add(photo);
        }

        House house = ModifyAdapter.gethouse();
        service.setHouse(house);


    }
}
