package com.openclassrooms.realestatemanager.ui.addhouse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.addnewhouse.AddNewHouseAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;
import com.openclassrooms.realestatemanager.ui.details.DetailsActivity;

import java.util.ArrayList;
import java.util.List;


public class AddHouseActivity extends AppCompatActivity {

    private RecyclerView addNewHouseToDoList;
    private LinearLayoutManager layoutManager;
    private static AddNewHouseAdapter adapter;
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

        service.setActivity(this, "AddHouse");
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

        House house = AddNewHouseAdapter.getHouse();
        service.setHouse(house);
        house.setId(service.getHousesList().size() + 1);
        service.addHouseToList(house);
        service.getHouse().getImages().remove(PhotoListAdapter.getAddPhoto());

    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == 100) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 90);
        }


        if (requestCode == 90) {

            AlertDialog.Builder notifyNewPhoto = new AlertDialog.Builder(this);
            notifyNewPhoto.setCancelable(false);
            notifyNewPhoto.setTitle("Add a description");
            notifyNewPhoto.setMessage("What kind of room is it?");
            notifyNewPhoto.setIcon(R.drawable.ic_add_blue_24dp);

            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.house_description_dialog, null);
            final EditText descriptionText = view.findViewById(R.id.description_dialog);
            notifyNewPhoto.setView(view);

            notifyNewPhoto.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri imageUri = data.getData();
                    Photo photo = new Photo(AddNewHouseAdapter.getHouse().getImages().size() + 1, imageUri, descriptionText.getText().toString());
                    AddNewHouseAdapter.getHouse().addImage(photo);
                    adapter.notifyDataSetChanged();
                }
            });

            notifyNewPhoto.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert = notifyNewPhoto.create();
            alert.getWindow().setGravity(Gravity.BOTTOM);
            alert.show();


        }
    }

    public static AddNewHouseAdapter getAdapter(){
        return adapter;
    }


}
