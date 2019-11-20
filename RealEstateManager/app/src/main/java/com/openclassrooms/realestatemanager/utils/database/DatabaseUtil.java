package com.openclassrooms.realestatemanager.utils.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.second.ListFragmentAdapter;
import com.openclassrooms.realestatemanager.ui.fragments.second.ListFragment;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil extends AsyncTask<Object, String, String> {

    private RealEstateManagerAPIService service;
    private Context context;
    private FirebaseHelper helper;
    private ListFragmentAdapter adapter;
    private SaveToDatabase database;
    private RecyclerView recyclerView;

    public DatabaseUtil(FirebaseHelper helper) {
        this.helper = helper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        while (helper.getHouses() == null) {
            helper.getHouseFromFirebase();
            helper.getDetailsFromFireBase();
            helper.getPhotosFromFirebase();
            helper.getHousesAdressesFromFirebase();
            helper.getUsersFromFireBase();
        }
    }

    @Override
    protected String doInBackground(Object... objects) {
        this.service = (RealEstateManagerAPIService) objects[0];
        this.context = (Context) objects[1];
        this.helper = (FirebaseHelper) objects[2];
        this.adapter = (ListFragmentAdapter) objects[3];
        this.database = (SaveToDatabase) objects[4];
        this.recyclerView = (RecyclerView) objects[5];

        if (database.houseDao().getHouses().size() < 1) {
            while (database.houseDao().getHouses().size() < 1) {
                service.setHousesList(context);

            }
        } else {
            while (database.houseDao().getHouses().size() < helper.getHouses().size()){
                service.setHousesList(context);
            }
        }
        service.setHousesDetails(context);

        service.setAdresses(context);

        service.setPhotos(context);

        service.setUsers(helper.getUsers());


        if (helper.getHouses() != null) {
            return "ok";
        } else {
            return "failed";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        service.setMyHousesList(getMyHouses());
        adapter = new ListFragmentAdapter(database.houseDao().getHouses(), context);
        recyclerView.setAdapter(adapter);
    }

    public List<House> getMyHouses(){
      List<House> myHouses = new ArrayList<>();
        for (int i = 0; i < database.houseDao().getHouses().size(); i++){
            House house = database.houseDao().getHouses().get(i);
            if (house.getAgentId().equals(service.getUser().getUserId())){
                myHouses.add(house);
            }
        }
        return myHouses;
    }
}
