package com.openclassrooms.realestatemanager.model;

import android.graphics.Bitmap;
import android.net.Uri;

public class Photo {
    private int id;
    private Uri photoUrl;
    private String description;

    public Photo(int id, Uri photoUrl, String description) {
        this.id = id;
        this.photoUrl = photoUrl;
        this.description = description;
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
