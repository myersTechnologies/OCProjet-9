package com.openclassrooms.realestatemanager.utils;

import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchHelper {

    private static Search search;
    private static List<House> houses;
    private static SaveToDatabase database = SaveToDatabase.getInstance(DI.getService().getActivity());

    public static Search getSearch(){
        return search;
    }

    public static List<House> getHousesList(){
        return houses;
    }

    public static List<House> getHouses() {
            houses.clear();
            for (int i = 0; i < database.houseDetailsDao().getDetails().size(); i++) {
                HouseDetails details = database.houseDetailsDao().getDetails().get(i);
                checkSurface(details);
            }
        return houses;
    }

    private static void checkName(House house, HouseDetails details) {
        if (search.getName() != null){
            if (house.getName().equals(search.getName())){
                checkCity(house, details);
            }
        } else {
            checkCity(house, details);
        }
    }

    private static void checkCity(House house, HouseDetails details) {
        if (search.getCity() != null){
            for (int i = 0; i < database.adressDao().getAdresses().size(); i++){
                AdressHouse adressHouse = database.adressDao().getAdresses().get(i);
                if (adressHouse.getHouseId().equals(house.getId())){
                    if (search.getCity().equals(adressHouse.getCity())){
                        addHouse(details);
                    }
                }
            }
        } else {
            addHouse(details);
        }
    }

    private static void checkBedrooms(HouseDetails details) {
            if (Integer.parseInt(details.getBedroomsNumber()) >= Integer.parseInt(search.getBedroomsMin())
                    && Integer.parseInt(details.getBedroomsNumber()) <= Integer.parseInt(search.getBedroomsMax())) {
                for (int i = 0; i < database.houseDao().getHouses().size(); i++){
                    if (database.houseDao().getHouses().get(i).getId().equals(details.getHouseId())){
                        checkPrice(database.houseDao().getHouses().get(i), details);
                    }
                }

            }
    }

    private static void checkPrice(House house, HouseDetails details){
        if (search.getPriceMin() != null) {
            if (Integer.parseInt(house.getPrice().replaceAll(",", "")) >= Integer.parseInt(search.getPriceMin())) {
                if (search.getPriceMax() != null){
                    if (Integer.parseInt(house.getPrice().replaceAll(",", "")) <= Integer.parseInt(search.getPriceMax())){
                       addHouse(details);
                       checkAvailability(house, details);
                    }
                }else {
                    checkAvailability(house, details);
                }
            }
        } else {
            checkAvailability(house, details);
        }
    }

    private static void checkAvailability(House house, HouseDetails details) {
        if (search.isAvailability() == "true" && house.isAvailable()){
            checkName(house, details);
        } else {
            if (search.isAvailability() == "false"&& !house.isAvailable()){
                checkName(house, details);
            }
        }

        if (search.isAvailability() == "none"){
            checkName(house, details);
        }
    }

    private static void addHouse(HouseDetails details){
        House house = getHouse(details.getHouseId());
        if (!houses.contains(house)) {
            houses.add(house);
        }
    }

    private static void checkBathrooms(HouseDetails details) {
        if ( search.getBathroomsMin() != null) {
            if (Integer.parseInt(details.getBathroomsNumber()) >= Integer.parseInt(search.getBathroomsMin())) {
                if (search.getBathroomsMax() != null){
                  if (  Integer.parseInt(details.getBathroomsNumber()) <= Integer.parseInt(search.getBathroomsMax())){
                      checkBedrooms(details);
                  } else {
                      checkBedrooms(details);
                  }
                }
            }
        } else {
            checkBedrooms(details);
        }
    }

    private static void checkSurface(HouseDetails details){
            if (Integer.parseInt(details.getSurface()) >= Integer.parseInt(search.getSurfaceMin())
                    && Integer.parseInt(details.getSurface()) <= Integer.parseInt(search.getSurfaceMax())) {
                checkRooms(details);
            }

    }

    private static void checkRooms(HouseDetails details){
            if (Integer.parseInt(details.getRoomsNumber()) >= Integer.parseInt(search.getRoomsMin())
                    && Integer.parseInt(details.getRoomsNumber()) <= Integer.parseInt(search.getRoomsMax())){
                    checkBathrooms(details);
            }

    }

    private static House getHouse(String id){
        House house = null;
        for (int j = 0; j < database.houseDao().getHouses().size(); j++){
            if (database.houseDao().getHouses().get(j).getId().equals(id)){
                house = database.houseDao().getHouses().get(j);
            }

        }
        return house;
    }

    public static void setNewSearch(){
        search = new Search();
        houses = new ArrayList<>();
    }

}
