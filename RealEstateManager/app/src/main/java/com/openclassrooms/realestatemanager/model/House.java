package com.openclassrooms.realestatemanager.model;

import java.util.List;

public class House {

    private List<Photo> images;
    private String name;
    private String adress;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private double price;
    private String description;
    private int surface;
    private int roomsNumber;
    private int bathroomsNumber;
    private int bedroomsNumber;
    private int id;
    private boolean available;
    private String onLineDate;

    public House(int id, List<Photo> images,String name, String adress, String city, String state,
                 String country, String zipCode, double price, String description, int surface, int roomsNumber,
                 int bathroomsNumber, int bedroomsNumber, boolean available, String onLineDate) {
        this.images = images;
        this.name = name;
        this.adress =adress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.price = price;
        this.description = description;
        this.surface = surface;
        this.roomsNumber = roomsNumber;
        this.bathroomsNumber = bathroomsNumber;
        this.bedroomsNumber = bedroomsNumber;
        this.id = id;
        this.available = available;
        this.onLineDate = onLineDate;
    }

    public String getOnLineDate() {
        return onLineDate;
    }

    public void setOnLineDate(String onLineDate) {
        this.onLineDate = onLineDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Photo> getImages() {
        return images;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public int getBathroomsNumber() {
        return bathroomsNumber;
    }

    public void setBathroomsNumber(int bathroomsNumber) {
        this.bathroomsNumber = bathroomsNumber;
    }

    public int getBedroomsNumber() {
        return bedroomsNumber;
    }

    public void setBedroomsNumber(int bedroomsNumber) {
        this.bedroomsNumber = bedroomsNumber;
    }

    public void setImages(List<Photo> images) {
        this.images = images;
    }

    public void addImage(Photo image){
        images.add(image);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
