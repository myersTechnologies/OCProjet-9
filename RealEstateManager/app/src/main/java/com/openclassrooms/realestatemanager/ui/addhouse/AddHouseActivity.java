package com.openclassrooms.realestatemanager.ui.addhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.addnewhouse.AddNewHouseAdapter;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class AddHouseActivity extends AppCompatActivity {

    private RecyclerView addNewHouseToDoList;
    private LinearLayoutManager layoutManager;
    private AddNewHouseAdapter adapter;
    private RealEstateManagerAPIService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        service = DI.getService();
        Toolbar toolbar = findViewById(R.id.toolbar_new_house);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addNewHouseToDoList = findViewById(R.id.new_house_list);
        layoutManager = new LinearLayoutManager(this);
        addNewHouseToDoList.setLayoutManager(layoutManager);
        adapter = new AddNewHouseAdapter();
        addNewHouseToDoList.setAdapter(adapter);
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
                getViewsAndAddHouse();
                Intent intent = new Intent(this, DetailsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getViewsAndAddHouse() {


        List<Photo> photos = new ArrayList<>();

        Photo photo = new Photo(R.drawable.main_image, "Chambre");
        Photo phot2 = new Photo(R.drawable.main_image_2, "Cuisine");
        photos.add(photo);
        photos.add(phot2);


        House house = AddNewHouseAdapter.getHouse();

        service.setHouse(house);
        house.setImages(photos);
        house.setId(service.getHousesList().size() + 1);
        service.addHouseToList(house);



    }

}
