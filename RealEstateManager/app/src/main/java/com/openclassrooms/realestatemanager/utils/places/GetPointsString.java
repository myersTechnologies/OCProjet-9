package com.openclassrooms.realestatemanager.utils.places;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;
import com.openclassrooms.realestatemanager.ui.adapters.details.PointsAdapter;
import com.openclassrooms.realestatemanager.ui.adapters.modify.PhotoListAdapter;
import com.openclassrooms.realestatemanager.utils.AddModifyHouseHelper;
import com.openclassrooms.realestatemanager.utils.PointsUtils;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetPointsString extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private House house;
    private RealEstateManagerAPIService service;
    private Context context;
    private HouseDetails details;
    private AdressHouse adress;
    private List<Photo> photos;

    @Override
    protected String doInBackground(Object... objects) {
        String url = (String) objects[0];
        house = (House) objects[1];
        service = (RealEstateManagerAPIService)objects[2];
        context = (Context)objects[3];
        details = (HouseDetails) objects[4];
        adress = (AdressHouse) objects[5];
        photos = (List<Photo>)objects[6];
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
        service.addHouseToList(house, context);
        service.setHouse(house);
        service.addHousesDetails(details, context);
        service.addAdresses(adress, context);

        for (int i = 0; i < photos.size(); i++) {
            final Photo photo = photos.get(i);
            if (!photo.getDescription().equals("Add new photo")) {
                service.addPhotos(photo, context);
                final FirebaseHelper helper = DI.getFirebaseDatabase();
                helper.addPhotoToFirebase(photo, Uri.fromFile(new File(Utils.getRealPathFromURI(Uri.parse(photo.getPhotoUrl())))));
                helper.addPhotoToFireStore(photo);
            }
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
        if (type.equals("post_office") || type.equals("post_box")){
            typePoint = "post";
        }
       if (house.getPointsOfInterest() == null){
           house.setPointsOfInterest(typePoint);
       } else {
           if (!house.getPointsOfInterest().contains(typePoint)) {
               house.setPointsOfInterest(house.getPointsOfInterest() + "," + typePoint);
           }
       }
    }
}
