package com.openclassrooms.realestatemanager.model;

import android.net.Uri;

public class Preferences {

    private String id;
    private String userId;
    private String monetarySystem;
    private String userName;
    private Uri userPhoto;
    private Uri menuImage;

    public Preferences(String id, String userId, String monetarySystem, String userName, Uri userPhoto, Uri menuImage) {
        this.id = id;
        this.userId = userId;
        this.monetarySystem = monetarySystem;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.menuImage = menuImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMonetarySystem() {
        return monetarySystem;
    }

    public void setMonetarySystem(String monetarySystem) {
        this.monetarySystem = monetarySystem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Uri getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Uri userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Uri getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(Uri menuImage) {
        this.menuImage = menuImage;
    }
}
