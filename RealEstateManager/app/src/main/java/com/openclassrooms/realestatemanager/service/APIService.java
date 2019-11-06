package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
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
    private FirebaseHelper helper = DI.getFirebaseDatabase();

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
    public void addHouseToList(House house) {
        if (housesList == null){
            housesList = new ArrayList<>();
        }
        housesList.add(house);
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
    public void addAdresses(AdressHouse adresses) {
        if (this.adresses == null){
            this.adresses = new ArrayList<>();
        }
        this.adresses.add(adresses);
    }

    @Override
    public void addPhotos(Photo photo) {
        if (photos == null){
            photos = new ArrayList<>();
        }
        photos.add(photo);
        //Firebase here
    }

    @Override
    public List<Photo> getPhotos() {
        return photos;
    }

}
