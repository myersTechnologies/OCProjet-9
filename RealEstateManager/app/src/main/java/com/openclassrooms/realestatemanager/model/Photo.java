package com.openclassrooms.realestatemanager.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Photo {
    private int id;
    private Uri photoUrl;
    private String description;
    private String houseId;

    public Photo(int id, Uri photoUrl, String description, String houseId) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.description = description;
        this.houseId = houseId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
