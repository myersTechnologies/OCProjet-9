package com.openclassrooms.realestatemanager.events;

import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;

public class ImageEvent {

    public Photo photo;

    public ImageEvent(Photo photo){
        this.photo = photo;
    }
}
