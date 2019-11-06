package com.openclassrooms.realestatemanager.model;

import java.util.List;

public class House {

    private int id;
    private String name;
    private String price;
    private String description;
    private int surface;
    private int roomsNumber;
    private int bathroomsNumber;
    private int bedroomsNumber;
    private boolean available;
    private String onLineDate;
    private String soldDate;
    private String AgentId;
    private String monetarySystem;
    private String measureUnity;

    public House(int id, String name, String price, String description, int surface, int roomsNumber,
                 int bathroomsNumber, int bedroomsNumber, boolean available, String onLineDate, String monetarySystem, String measureUnity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.surface = surface;
        this.roomsNumber = roomsNumber;
        this.bathroomsNumber = bathroomsNumber;
        this.bedroomsNumber = bedroomsNumber;
        this.id = id;
        this.available = available;
        this.onLineDate = onLineDate;
        this.monetarySystem = monetarySystem;
        this.measureUnity = measureUnity;
    }

    public String getOnLineDate() {
        return onLineDate;
    }

    public void setOnLineDate(String onLineDate) {
        this.onLineDate = onLineDate;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonetarySystem() {
        return monetarySystem;
    }

    public void setMonetarySystem(String monetarySystem) {
        this.monetarySystem = monetarySystem;
    }

    public String getMeasureUnity() {
        return measureUnity;
    }

    public void setMeasureUnity(String measureUnity) {
        this.measureUnity = measureUnity;
    }
}
