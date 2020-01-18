package com.openclassrooms.realestatemanager.utils.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.second.ListFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseUtil extends AsyncTask<Object, String, String> {

    private RealEstateManagerAPIService service;
    private Context context;
    private FirebaseHelper helper;
    private ListFragmentAdapter adapter;
    private SaveToDatabase database;
    private RecyclerView recyclerView;
    private ProgressDialog dialog;

    public DatabaseUtil(FirebaseHelper helper, Context context) {
        this.helper = helper;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... objects) {
        this.service = (RealEstateManagerAPIService) objects[0];
        this.helper = (FirebaseHelper) objects[1];
        this.adapter = (ListFragmentAdapter) objects[2];
        this.database = (SaveToDatabase) objects[3];
        this.recyclerView = (RecyclerView) objects[4];
        this.dialog = (ProgressDialog)objects[5];

        helper.getHouseFromFirebase();
        publishProgress("1");
        while (!helper.isHousesTaskFinish()) {
        }
        publishProgress("2");
        helper.getDetailsFromFireBase();
        while (!helper.isHousesTaskFinish()) {
        }
        publishProgress("3");
        helper.getPhotosFromFirebase();
        while (!helper.isPhotoEmpty()) {
        }
        publishProgress("4");
        helper.getHousesAdressesFromFirebase();
        while (!helper.isHousesTaskFinish()) {
        }
        helper.getUsersFromFireBase();

        service.setUsers(helper.getUsers());


        if (helper.getHouses() != null) {
            return "ok";
        } else {
            return "failed";
        }
    }


    @Override
    protected void onProgressUpdate(String... values) {

        if (values[0] == "1") {
            dialog.setMessage("Downloading Estates");
        }


        if (values[0] == "2") {
            dialog.setMessage("Downloading Details");
        }

        if (values[0] == "3") {
            dialog.setMessage("Downloading Photos");
        }


        if (values[0] == "4") {
            dialog.setMessage("Downloading Locations");
        }

    }

    @Override
    protected void onPostExecute(String s) {
        service.setMyHousesList(getMyHouses());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter = new ListFragmentAdapter(database.houseDao().getHouses(), context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        if (dialog.isShowing()){
            dialog.dismiss();
        }
        super.onPostExecute(s);
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
