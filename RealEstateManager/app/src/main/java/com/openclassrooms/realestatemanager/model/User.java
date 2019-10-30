package com.openclassrooms.realestatemanager.model;

public class User {
    String userId;
    String name;
    String email;
    String photoUri;


    public User(String userId, String name, String email, String photoUri) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
