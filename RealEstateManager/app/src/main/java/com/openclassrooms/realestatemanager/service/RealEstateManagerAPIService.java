package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.House;

import java.util.List;


public interface RealEstateManagerAPIService {
    void setHouse(House house);
    House getHouse();
    List<House> getHousesList();
    void addHouseToList(House house);

}
