package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.openclassrooms.realestatemanager.model.House;
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
    String getRealPathFromUri(Uri uri);
    int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight);
    Bitmap decodeSampledBitmapFromResource(Resources res, File imageFile, int reqWidth, int reqHeight);
    void setUser(User user);
    User getUser();

}
