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
    private ProgressDialog dialog;
    private String load;

    public DatabaseUtil(FirebaseHelper helper, Context context) {
        this.helper = helper;
        this.context = context;
        dialog = new ProgressDialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Loading data");
        load = "Please wait...";
        dialog.setMessage(load);

    }

    @Override
    protected void onPreExecute() {
        while (helper.getHouses() == null) {
            helper.getHouseFromFirebase();
            helper.getDetailsFromFireBase();
            helper.getPhotosFromFirebase();
            helper.getHousesAdressesFromFirebase();
            helper.getUsersFromFireBase();
        }
        dialog.show();
    }

    @Override
    protected String doInBackground(Object... objects) {
        this.service = (RealEstateManagerAPIService) objects[0];
        this.helper = (FirebaseHelper) objects[1];
        this.adapter = (ListFragmentAdapter) objects[2];
        this.database = (SaveToDatabase) objects[3];
        this.recyclerView = (RecyclerView) objects[4];


            if (database.houseDao().getHouses().size() < 1) {
                while (database.houseDao().getHouses().size() < 1) {
                    service.setHousesList(context);

                }
                service.setHousesList(context);

                service.setHousesDetails(context);

                service.setAdresses(context);

                service.setPhotos(context);
            } else {
                service.setHousesList(context);

                service.setHousesList(context);

                service.setHousesDetails(context);

                service.setAdresses(context);

                service.setPhotos(context);
            }

            service.setUsers(helper.getUsers());


        if (helper.getHouses() != null) {
            return "ok";
        } else {
            return "failed";
        }
    }



    @Override
    protected void onPostExecute(String s) {
        service.setMyHousesList(getMyHouses());
        adapter.notifyDataSetChanged();
        adapter = new ListFragmentAdapter(database.houseDao().getHouses(), context);
        recyclerView.setAdapter(adapter);
        if (dialog.isShowing()){
            dialog.dismiss();
        }
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
