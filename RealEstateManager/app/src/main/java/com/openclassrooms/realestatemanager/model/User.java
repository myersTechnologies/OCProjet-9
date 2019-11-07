package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    private String userId;
    @NonNull
    @ColumnInfo(name = "user_name")
    private String name;
    @NonNull
    @ColumnInfo(name = "user_email")
    private String email;
    @NonNull
    @ColumnInfo(name = "user_photo")
    private String photoUri;


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

    public static User fromContentValues(ContentValues values){
        final User user = new User(null, null, null, null);
        if (values.containsKey("user_id")) user.setUserId(values.getAsString("user_id"));
        if (values.containsKey("user_name")) user.setName(values.getAsString("user_name"));
        if (values.containsKey("user_email")) user.setEmail(values.getAsString("user_email"));
        if (values.containsKey("user_photo")) user.setPhotoUri(values.getAsString("user_photo"));
        return user;
    }
}
