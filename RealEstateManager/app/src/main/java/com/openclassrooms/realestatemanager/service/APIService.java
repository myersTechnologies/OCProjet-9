package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.Context;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.List;

public class APIService implements RealEstateManagerAPIService {

    private House house;
    private Activity activity;
    private String activityName;
    private User user;
    private Preferences preferences;
    private Photo photo;
    private List<House> myHouses;
    private List<User> users;
    private SaveToDatabase database;
    private FirebaseHelper helper;

    @Override
    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public House getHouse() {
        return house;
    }


    @Override
    public void addHouseToList(House house, Context context) {
        database = SaveToDatabase.getInstance(context);
        database.houseDao().insertHouse(house);
        helper = DI.getFirebaseDatabase();
        helper.addHouseToFirebase(house);
    }

    @Override
    public void setActivity(Activity activity, String name) {
        this.activity = activity;
        this.activityName = name;
    }

    @Override
    public String activityName() {
        return activityName;
    }


    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Preferences getPreferences() {
        return preferences;
    }

    @Override
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public Photo getPhoto() {
        return photo;
    }

    @Override
    public void setMyHousesList(List<House> myHouses) {
        this.myHouses = myHouses;
    }

    @Override
    public List<House> getMyHouses() {
        return myHouses;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void addAdresses(AdressHouse adresses, Context context) {
        database = SaveToDatabase.getInstance(context);
        database.adressDao().insertAdress(adresses);
        helper = DI.getFirebaseDatabase();
        helper.addAdressToFrirebase(adresses);
    }

    @Override
    public void addPhotos(Photo photo, Context context) {
        database = SaveToDatabase.getInstance(context);
        database.photoDao().insertPhoto(photo);
    }

    @Override
    public void addHousesDetails(HouseDetails houseDetails, Context context) {
        database = SaveToDatabase.getInstance(context);
        database.houseDetailsDao().insertDetails(houseDetails);
        helper = DI.getFirebaseDatabase();
        helper.addDetailsToFireBase(houseDetails);
    }

    @Override
    public void setHousesDetails(Context context) {
        database = SaveToDatabase.getInstance(context);
        helper = DI.getFirebaseDatabase();
        Utils.compareDetailsLists(helper.getDetails(), database.houseDetailsDao().getDetails());

    }

    @Override
    public void setPhotos(Context context) {
        helper = DI.getFirebaseDatabase();
        database = SaveToDatabase.getInstance(context);
        Utils.comparePhotosLists(helper.getPhotos(), database.photoDao().getPhotos());

    }

    @Override
    public void setAdresses(Context context) {
        database = SaveToDatabase.getInstance(context);
        helper = DI.getFirebaseDatabase();
        Utils.compareAdressLists(helper.getHousesAdresses(),database.adressDao().getAdresses());

    }

    @Override
    public void setHousesList(Context context) {
        database = SaveToDatabase.getInstance(context);
        helper = DI.getFirebaseDatabase();
        Utils.compareHousesLists(helper.getHouses(), database.houseDao().getHouses());
    }

    @Override
    public void removePhoto(Photo photo, Context context) {
        database = SaveToDatabase.getInstance(context);
        database.photoDao().deletePhoto(photo);
        FirebaseHelper helper = DI.getFirebaseDatabase();
        helper.removePhoto(photo);
    }



}
