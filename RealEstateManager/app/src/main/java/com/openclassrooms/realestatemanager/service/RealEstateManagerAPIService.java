package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

import java.io.File;
import java.util.List;


public interface RealEstateManagerAPIService {
    void setHouse(House house);
    House getHouse();
    List<House> getHousesList();
    void addHouseToList(House house);
    void setActivity(Activity activity, String name);
    String activityName();
    Activity getActivity();
    void setUser(User user);
    User getUser();
    void setPreferences(Preferences preferences);
    Preferences getPreferences();
    void setPhoto(Photo photo);
    Photo getPhoto();
    void setMyHousesList(List<House> myHouses);
    List<House> getMyHouses();
    List<User> getUsers();
    void setUsers(List<User> users);
    List<AdressHouse> getAdressesList();
    void addAdresses (AdressHouse adresses);
    void addPhotos(Photo photo);
    List<Photo> getPhotos();

}
