package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = House.class, parentColumns = "id", childColumns = "house_id" ))
public class HouseDetails {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    @ColumnInfo(name = "house_id", index = true)
    private String houseId;
    @ColumnInfo(name = "surface")
    private String surface;
    @ColumnInfo(name = "rooms")
    private String roomsNumber;
    @ColumnInfo(name = "bathrooms")
    private String bathroomsNumber;
    @ColumnInfo(name = "bedrooms")
    private String bedroomsNumber;
    @ColumnInfo(name = "online_date")
    private String onLineDate;
    @ColumnInfo(name = "sold_date")
    private String soldDate;
    @ColumnInfo(name = "description")
    private String description;

    public HouseDetails(String id, String houseId) {
        this.id = id;
        this.houseId = houseId;
    }

    public String getOnLineDate() {
        return onLineDate;
    }

    public void setOnLineDate(String onLineDate) {
        this.onLineDate = onLineDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(String roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public String getBathroomsNumber() {
        return bathroomsNumber;
    }

    public void setBathroomsNumber(String bathroomsNumber) {
        this.bathroomsNumber = bathroomsNumber;
    }

    public String getBedroomsNumber() {
        return bedroomsNumber;
    }

    public void setBedroomsNumber(String bedroomsNumber) {
        this.bedroomsNumber = bedroomsNumber;
    }

    public static HouseDetails fromContentValues(ContentValues values){
        final HouseDetails houseDetails = new HouseDetails(null, null);
        if (values.containsKey("id")) houseDetails.setId(values.getAsString("id"));
        if (values.containsKey("house_id")) houseDetails.setHouseId(values.getAsString("house_id"));
        if (values.containsKey("surface")) houseDetails.setSurface(values.getAsString("surface"));
        if (values.containsKey("rooms")) houseDetails.setRoomsNumber(values.getAsString("rooms"));
        if (values.containsKey("bathrooms")) houseDetails.setBathroomsNumber(values.getAsString("bathrooms"));
        if (values.containsKey("bedrooms")) houseDetails.setBedroomsNumber(values.getAsString("bedrooms"));
        if (values.containsKey("online")) houseDetails.setOnLineDate(values.getAsString("online"));
        if (values.containsKey("sold")) houseDetails.setSoldDate(values.getAsString("sold"));
        if (values.containsKey("description")) houseDetails.setDescription(values.getAsString("description"));
        return houseDetails;
    }

}
