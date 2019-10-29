package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.House;

import java.util.ArrayList;
import java.util.List;

public class APIService implements RealEstateManagerAPIService {

    private House house;
    private List<House> housesList;

    @Override
    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public House getHouse() {
        return house;
    }

    @Override
    public List<House> getHousesList() {
        if (housesList == null){
            housesList = new ArrayList<>();
        }
        return housesList;
    }

    @Override
    public void addHouseToList(House house) {
        if (housesList == null){
            housesList = new ArrayList<>();
        }
        housesList.add(house);
    }


}
