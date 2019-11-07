package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class APIService implements RealEstateManagerAPIService {

    private House house;
    private List<House> housesList;
    private Activity activity;
    private String activityName;
    private User user;
    private Preferences preferences;
    private Photo photo;
    private List<House> myHouses;
    private List<User> users;
    private List<AdressHouse> adresses;
    private List<Photo> photos;
    private List<HouseDetails> houseDetails;
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
    public List<House> getHousesList() {
        if (housesList == null){
            housesList = new ArrayList<>();
        }
        return housesList;
    }

    @Override
    public void addHouseToList(House house, Context context) {
        if (housesList == null){
            housesList = new ArrayList<>();
        }
        database = SaveToDatabase.getInstance(context);
        database.houseDao().insertHouse(house);
        helper = DI.getFirebaseDatabase();
        helper.addHouseToFirebase(house);
        if (!housesList.contains(house)) {
            housesList.add(house);
        }
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
    public List<AdressHouse> getAdressesList() {
        return adresses;
    }

    @Override
    public void addAdresses(AdressHouse adresses, Context context) {
        if (this.adresses == null){
            this.adresses = new ArrayList<>();
        }
        database = SaveToDatabase.getInstance(context);
        database.adressDao().insertAdress(adresses);
        helper = DI.getFirebaseDatabase();
        helper.addAdressToFrirebase(adresses);
        this.adresses.add(adresses);
    }

    @Override
    public void addPhotos(Photo photo, Context context) {
        if (photos == null){
            photos = new ArrayList<>();
        }
        database = SaveToDatabase.getInstance(context);
        database.photoDao().insertPhoto(photo);
        photos.add(photo);
        //Firebase here
    }

    @Override
    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public List<HouseDetails> getHousesDetails() {
        return houseDetails;
    }

    @Override
    public void addHousesDetails(HouseDetails houseDetails, Context context) {
        if (this.houseDetails == null){
            this.houseDetails = new ArrayList<>();
        }
        database = SaveToDatabase.getInstance(context);
        database.houseDetailsDao().insertDetails(houseDetails);
        helper = DI.getFirebaseDatabase();
        helper.addDetailsToFireBase(houseDetails);
        this.houseDetails.add(houseDetails);
    }

    @Override
    public void setHousesDetails(Context context) {
        database = SaveToDatabase.getInstance(context);
        if (database.houseDetailsDao().getDetails() != null) {
            houseDetails = database.houseDetailsDao().getDetails();
        } else {
            houseDetails = new ArrayList<>();
        }
    }

    @Override
    public void setPhotos(Context context) {
        database = SaveToDatabase.getInstance(context);
        if (database.photoDao().getPhotos() != null) {
            photos = database.photoDao().getPhotos();
        } else {
            photos = new ArrayList<>();
        }
    }

    @Override
    public void setAdresses(Context context) {
        database = SaveToDatabase.getInstance(context);
        if (database.adressDao().getAdresses() != null) {
            adresses = database.adressDao().getAdresses();
        } else {
            adresses = new ArrayList<>();
        }
    }

    @Override
    public void setHousesList(Context context) {
        database = SaveToDatabase.getInstance(context);
        if (database.houseDao().getHouses() != null) {
            housesList = database.houseDao().getHouses();
        } else {
            housesList = new ArrayList<>();
        }
    }

    @Override
    public void removePhoto(Photo photo, Context context) {
        database = SaveToDatabase.getInstance(context);
        photos.remove(photo);
        database.photoDao().deletePhoto(photo);
    }

}
