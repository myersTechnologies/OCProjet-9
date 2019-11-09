package com.openclassrooms.realestatemanager.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = House.class, parentColumns = "id", childColumns = "house_id" ))
public class AdressHouse {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "adress_id")
    private String id;
    @ColumnInfo (name = "house_id", index = true)
    private String houseId;
    @ColumnInfo(name = "adress")
    private String adress;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "zipcode")
    private String zipCode;

    public AdressHouse(String id, String houseId, String adress, String city, String state, String country, String zipCode) {
        this.id = id;
        this.houseId = houseId;
        this.adress = adress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public static AdressHouse fromContentValues(ContentValues values){
        final AdressHouse adressHouse = new AdressHouse(null, null, null, null, null, null, null);
        if (values.containsKey("id")) adressHouse.setId(values.getAsString("id"));
        if (values.containsKey("house_id")) adressHouse.setHouseId(values.getAsString("house_id"));
        if (values.containsKey("adress")) adressHouse.setAdress(values.getAsString("adress"));
        if (values.containsKey("city")) adressHouse.setCity(values.getAsString("city"));
        if (values.containsKey("state")) adressHouse.setState(values.getAsString("state"));
        if (values.containsKey("country")) adressHouse.setCountry(values.getAsString("country"));
        if (values.containsKey("zip_code")) adressHouse.setZipCode(values.getAsString("zip_code"));

        return adressHouse;
    }
}
