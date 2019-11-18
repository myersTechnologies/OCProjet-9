package com.openclassrooms.realestatemanager.utils.places;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.adapters.details.PointsAdapter;
import com.openclassrooms.realestatemanager.utils.PointsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetPointsString extends AsyncTask<Object, String, String> {
    private Context context;
    private List<String> points;
    private String googlePlacesData;
    private PointsAdapter adapter;

    @Override
    protected String doInBackground(Object... objects) {
        String url = (String) objects[0];
        context = (Context) objects[1];
        points = (List<String>)objects[2];
        adapter = (PointsAdapter) objects[3];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList;
        PointParser parser = new PointParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int i = 0; i < nearbyPlaceList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            addPoint(googlePlace);
        }
    }

    private void addPoint(HashMap<String, String> googlePlace){
        String type = googlePlace.get("type");
        Log.d("TYPEPOINT", type);
        String typePoint = "other";
        if (PointsUtils.getTransports().contains(type)){
            typePoint = "transport";
        }
        if (PointsUtils.getAmusementPoints().contains(type)){
            typePoint = "amusement";
        }
        if (PointsUtils.getEducationPoints().contains(type)){
            typePoint = "education";
        }
        if (PointsUtils.getEstablissementsPoints().contains(type)){
            typePoint = "establissement";
        }
        if (PointsUtils.getHealthPoints().contains(type)){
            typePoint = "health";
        }
        if (PointsUtils.getUtilsPoints().contains(type)){
            typePoint = "utils";
        }
        if (PointsUtils.getPoliticalPoints().contains(type)){
            typePoint = "political";
        }
        if (PointsUtils.getReligiousPoints().contains(type)){
            typePoint = "pray";
        }
        if (PointsUtils.getRestaurantsPoints().contains(type)){
            typePoint = "food";
        }
        if (PointsUtils.getCampsAnHotels().contains(type)){
            typePoint = "hotel";
        }
        if (PointsUtils.getCourtPoints().contains(type)){
            typePoint = "court";
        }
        if (PointsUtils.getMarketPoints().contains(type)){
            typePoint = "cart";
        }
        if (PointsUtils.getMoneyPoints().contains(type)){
            typePoint = "money";
        }
        if (type.equals("police")){
            typePoint = "police";
        }
        if (type.equals("fire_station")){
            typePoint = "fire";
        }
        if (type.equals("gym")){
            typePoint = "gym";
        }
        if (type.equals("gas_station")){
            typePoint = "gas";
        }
        if (type.equals("real_estate_agency")){
            typePoint = "estate";
        }
        if (type.equals("stadium")){
            typePoint = "stadium";
        }
        if (type.equals("parking")){
            typePoint = "parking";
        }
        if (type.equals("post_office")){
            typePoint = "post";
        }
        if (!points.contains(typePoint)){
            points.add(typePoint);
            adapter.notifyDataSetChanged();
        }
    }
}
