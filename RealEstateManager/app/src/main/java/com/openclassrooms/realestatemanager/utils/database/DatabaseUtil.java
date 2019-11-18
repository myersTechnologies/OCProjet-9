package com.openclassrooms.realestatemanager.utils.database;

import android.content.Context;
import android.os.AsyncTask;

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
    @Override
    protected String doInBackground(Object... objects) {
        this.service = (RealEstateManagerAPIService) objects[0];
        this.context = (Context) objects[1];
        this.helper = (FirebaseHelper) objects[2];
        this.adapter = (ListFragmentAdapter) objects[3];
        this.database = (SaveToDatabase) objects[4];

        helper.getHouseFromFirebase();
        helper.getDetailsFromFireBase();
        helper.getPhotosFromFirebase();
        helper.getHousesAdressesFromFirebase();
        helper.getUsersFromFireBase();

        service.setHousesList(context);
        service.setHousesDetails(context);
        service.setAdresses(context);
        service.setPhotos(context);
        service.setUsers(helper.getUsers());

        return "ok";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        adapter.notifyDataSetChanged();
        service.setMyHousesList(getMyHouses());
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
