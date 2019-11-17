package com.openclassrooms.realestatemanager.utils.places;



import android.util.Log;

import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DataParser {

    public HashMap<String, String> getPlace(JSONObject googlePlaceJson){
        HashMap<String, String > googlePlaceMap = new HashMap<>();

        String latitude = "-NA-";
        String longitude = "-NA-";
        String type = "-NA";

        try {

            if (!googlePlaceJson.getJSONObject("geometry").getJSONObject("location").isNull("lat")){
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            }

            if (!googlePlaceJson.getJSONObject("geometry").getJSONObject("location").isNull("lng")){
                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            }

            if(!googlePlaceJson.isNull("types")){
                JSONArray types = googlePlaceJson.getJSONArray("types");
                String typePlace = types.get(0).toString();
                if (!typePlace.equals("point_of_interest")) {
                   type = typePlace;
                }  else {
                    type = types.get(1).toString();
                }

            }


            googlePlaceMap.put("type", type);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray){
        int count = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placemap = null;

        for (int i = 0; i < count; i++){
            try {
                placemap = getPlace((JSONObject) jsonArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            placesList.add(placemap);
        }

        return placesList;
    }

    public List<HashMap<String, String>> parse(String string){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(string);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }



}
