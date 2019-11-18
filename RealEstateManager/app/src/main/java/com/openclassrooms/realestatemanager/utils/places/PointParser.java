package com.openclassrooms.realestatemanager.utils.places;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PointParser {
    public HashMap<String, String> getPlace(JSONObject googlePlaceJson){
        HashMap<String, String > googlePlaceMap = new HashMap<>();

        String type = "-NA";

        try {
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
