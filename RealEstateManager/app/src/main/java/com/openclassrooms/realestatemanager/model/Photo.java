package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = House.class, parentColumns = "id", childColumns = "house_id" ))
public class Photo {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "photo_id")
    private String id;
    @ColumnInfo (name = "photo_url")
    private String photoUrl;
    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "house_id", index = true)
    private String houseId;

    public Photo(String photoUrl, String description, String houseId) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Photo fromContentValues(ContentValues values){
        final Photo photo = new Photo( null, null, null);
        photo.setId(null);
        if (values.containsKey("photo_id")) photo.setId(values.getAsString("photo_id"));
        if (values.containsKey("house_id")) photo.setHouseId(values.getAsString("house_id"));
        if (values.containsKey("photo_url")) photo.setPhotoUrl(values.getAsString("photo_url"));
        if (values.containsKey("description")) photo.setDescription(values.getAsString("description"));
        return photo;
    }

}
