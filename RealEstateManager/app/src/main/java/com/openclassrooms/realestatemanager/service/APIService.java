package com.openclassrooms.realestatemanager.service;

import android.app.Activity;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.openclassrooms.realestatemanager.model.House;
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
    public String getRealPathFromUri(Uri uri) {
            Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int id_uri = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(id_uri);
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, File imageFile,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
