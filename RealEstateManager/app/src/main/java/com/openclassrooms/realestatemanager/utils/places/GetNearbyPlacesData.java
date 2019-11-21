package com.openclassrooms.realestatemanager.utils.places;


import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private Context context;
    private GoogleMap mapView;

    @Override
    protected String doInBackground(Object... objects) {
        String url = (String) objects[0];
        context = (Context) objects[1];
        mapView = (GoogleMap) objects[2];

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
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int i = 0; i < nearbyPlaceList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            setPlaceMarkerInfo(googlePlace);
        }
    }

    private void setPlaceMarkerInfo(HashMap<String, String> googlePlace) {

        String name = googlePlace.get("name");
        String latitude = googlePlace.get("lat");
        String longitude = googlePlace.get("lng");
        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        mapView.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))).setTitle(name);

    }

}
