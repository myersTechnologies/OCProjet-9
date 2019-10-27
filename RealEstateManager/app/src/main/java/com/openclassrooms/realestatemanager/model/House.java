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

    public House(List<Photo> images,String name, String adress, String city, String state,
                 String country, String zipCode, double price) {
        this.images = images;
        this.name = name;
        this.adress =adress;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.price = price;
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
}
