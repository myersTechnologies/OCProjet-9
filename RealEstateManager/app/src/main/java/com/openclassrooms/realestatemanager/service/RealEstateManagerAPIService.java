package com.openclassrooms.realestatemanager.service;

import com.openclassrooms.realestatemanager.model.House;

public interface RealEstateManagerAPIService {
    void setHouse(House house);
    House getHouse();
}
