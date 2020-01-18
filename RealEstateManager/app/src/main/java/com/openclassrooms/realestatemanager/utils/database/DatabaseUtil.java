package com.openclassrooms.realestatemanager.utils.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.openclassrooms.realestatemanager.DI.DI;
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
    private PullRefreshLayout layout;

    public DatabaseUtil(FirebaseHelper helper, Context context, ListFragmentAdapter adapter, RecyclerView recyclerView, ProgressDialog dialog,
                        PullRefreshLayout layout) {
        this.helper = helper;
        this.context = context;
        this.service = DI.getService();
        this.database = SaveToDatabase.getInstance(context);
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.dialog = dialog;
        this.layout = layout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... objects) {

            helper.getHouseFromFirebase();
            publishProgress("1");
            while (!helper.isHousesTaskFinish()) {
                if (isCancelled()) break;
            }
            publishProgress("2");
            helper.getDetailsFromFireBase();
            while (!helper.isHousesTaskFinish()) {
                if (isCancelled()) break;
            }
            publishProgress("3");
            helper.getPhotosFromFirebase();
            while (!helper.isPhotoEmpty()) {
                if (isCancelled()) break;
            }
            publishProgress("4");
            helper.getHousesAdressesFromFirebase();
            while (!helper.isHousesTaskFinish()) {
                if (isCancelled()) break;
            }
            helper.getUsersFromFireBase();

            service.setUsers(helper.getUsers());


        return "ok";

    }


    @Override
    protected void onProgressUpdate(String... values) {

        if (dialog != null) {
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

    }

    @Override
    protected void onPostExecute(String s) {
        service.setMyHousesList(getMyHouses());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        adapter = new ListFragmentAdapter(database.houseDao().getHouses(), context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
        layout.setRefreshing(false);
        cancel(true);

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
