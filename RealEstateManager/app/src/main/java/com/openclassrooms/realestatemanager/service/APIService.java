package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.House;

public class APIService implements RealEstateManagerAPIService {

    private House house;


    @Override
    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public House getHouse() {
        return house;
    }
}
