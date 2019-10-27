package com.openclassrooms.realestatemanager.ui.modify;

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
import com.openclassrooms.realestatemanager.ui.adapters.modify.ModifyAdapter;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {

    private RecyclerView modifyHouseList;
    private LinearLayoutManager layoutManager;
    private ModifyAdapter adapter;
    private RealEstateManagerAPIService service;
    private int surface;
    private int rooms;
    private int bathrooms;
    private int bedrooms;
    private String road;
    private String city;
    private String zipcode;
    private String state;
    private String country;
    private String description;
    private double price;

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
                getViewsAndAddHouse();
                Intent intent = new Intent(this, DetailsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getViewsAndAddHouse(){
        View view = modifyHouseList.getChildAt(0);
        EditText surfaceET = view.findViewById(R.id.surface_edit_text);
        EditText roomsET = view.findViewById(R.id.rooms_edit_text);
        EditText bathRoomsET = view.findViewById(R.id.bathrooms_edit_text);
        EditText bedRoomsRT = view.findViewById(R.id.bedrooms_edit_text);
        EditText roadET = view.findViewById(R.id.location_edit_text);
        EditText cityET = view.findViewById(R.id.location_edit_city_text);
        EditText zipCodeET = view.findViewById(R.id.location_edit_zc_text);
        EditText stateET = view.findViewById(R.id.location_edit_state_text);
        EditText countryET = view.findViewById(R.id.location_edit_country_text);
        EditText descriptionET = view.findViewById(R.id.description_edit_text);
        EditText priceET = view.findViewById(R.id.price_text);

        surface = Integer.parseInt(surfaceET.getText().toString());
        rooms = Integer.parseInt(roomsET.getText().toString());
        bathrooms = Integer.parseInt(bathRoomsET.getText().toString());
        bedrooms = Integer.parseInt(bedRoomsRT.getText().toString());
        road = roadET.getText().toString();
        city = cityET.getText().toString();
        zipcode = zipCodeET.getText().toString();
        state = stateET.getText().toString();
        country = countryET.getText().toString();
        description = descriptionET.getText().toString();
        price = Double.parseDouble(priceET.getText().toString());
        List<Photo> photos = new ArrayList<>();
        Photo photo = new Photo(R.drawable.main_image, "Cour");
        photos.add(photo);


        House house = new House(service.getHouse().getImages(), service.getHouse().getName(), road, city, state, country, zipcode, price, description, surface,
                rooms, bathrooms, bedrooms);


        service.setHouse(house);
    }
}
