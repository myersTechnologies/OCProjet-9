package com.openclassrooms.realestatemanager.model;

public class Photo {
    private int photoUrl;
    private String description;

    public Photo(int photoUrl, String description) {
        this.photoUrl = photoUrl;
        this.description = description;
    }

    public int getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(int photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
