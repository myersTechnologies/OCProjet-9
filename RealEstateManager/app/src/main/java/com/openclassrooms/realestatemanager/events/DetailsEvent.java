package com.openclassrooms.realestatemanager.events;

import com.openclassrooms.realestatemanager.model.House;

public class DetailsEvent {

    public House house;

    public  DetailsEvent(House house){
        this.house = house;
    }
}
