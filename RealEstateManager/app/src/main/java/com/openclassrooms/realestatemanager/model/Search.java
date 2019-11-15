package com.openclassrooms.realestatemanager.model;

public class Search {
    private String name;
    private String surfaceMin;
    private String surfaceMax;
    private String roomsMin;
    private String roomsMax;
    private String bathroomsMin;
    private String bathroomsMax;
    private String bedroomsMin;
    private String bedroomsMax;
    private String priceMin;
    private String priceMax;
    private String availability;
    private String city;

    public Search() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurfaceMin() {
        return surfaceMin;
    }

    public void setSurfaceMin(String surfaceMin) {
        this.surfaceMin = surfaceMin;
    }

    public String getRoomsMin() {
        return roomsMin;
    }

    public void setRoomsMin(String roomsMin) {
        this.roomsMin = roomsMin;
    }

    public String getBathroomsMin() {
        return bathroomsMin;
    }

    public void setBathroomsMin(String bathroomsMin) {
        this.bathroomsMin = bathroomsMin;
    }

    public String getBedroomsMin() {
        return bedroomsMin;
    }

    public void setBedroomsMin(String bedroomsMin) {
        this.bedroomsMin = bedroomsMin;
    }

    public String getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String priceMin) {
        this.priceMin = priceMin;
    }

    public String isAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getSurfaceMax() {
        return surfaceMax;
    }

    public void setSurfaceMax(String surfaceMax) {
        this.surfaceMax = surfaceMax;
    }

    public String getRoomsMax() {
        return roomsMax;
    }

    public void setRoomsMax(String roomsMax) {
        this.roomsMax = roomsMax;
    }

    public String getBathroomsMax() {
        return bathroomsMax;
    }

    public void setBathroomsMax(String bathroomsMax) {
        this.bathroomsMax = bathroomsMax;
    }

    public String getBedroomsMax() {
        return bedroomsMax;
    }

    public void setBedroomsMax(String bedroomsMax) {
        this.bedroomsMax = bedroomsMax;
    }

    public String getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String priceMax) {
        this.priceMax = priceMax;
    }
}
