package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.support.annotation.NonNull;


@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "preferences_id" ))
public class Preferences {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "preferences_id")
    private String id;
    @ColumnInfo(name = "user_id", index = true)
    private String userId;
    @ColumnInfo(name = "monetary_system")
    private String monetarySystem;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "user_photo")
    private String userPhoto;
    @ColumnInfo(name = "menu_image")
    private String menuImage;
    @ColumnInfo(name = "measure_unit")
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
