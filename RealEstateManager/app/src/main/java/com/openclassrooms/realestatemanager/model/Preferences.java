package com.openclassrooms.realestatemanager.model;

import android.net.Uri;

public class Preferences {

    private String id;
    private String userId;
    private String monetarySystem;
    private String userName;
    private String userPhoto;
    private String menuImage;
    private String measureUnity;

    public Preferences(String id, String userId, String monetarySystem, String userName, String userPhoto, String menuImage, String measureUnity) {
        this.id = id;
        this.userId = userId;
        this.monetarySystem = monetarySystem;
        this.userName = userName;
        this.userPhoto = userPhoto;
        this.menuImage = menuImage;
        this.measureUnity = measureUnity;
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

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public String getMeasureUnity() {
        return measureUnity;
    }

    public void setMeasureUnity(String measureUnity) {
        this.measureUnity = measureUnity;
    }
}
