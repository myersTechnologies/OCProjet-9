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
    private static List<House> housesList;
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

    private static void checkSurface(HouseDetails details){
        if (Integer.parseInt(details.getSurface()) >= Integer.parseInt(search.getSurfaceMin())
                && Integer.parseInt(details.getSurface()) <= Integer.parseInt(search.getSurfaceMax())) {
           checkRooms(details);
        }
    }

    private static void checkRooms(HouseDetails details){
        if (Integer.parseInt(details.getRoomsNumber()) >= Integer.parseInt(search.getRoomsMin())
                && Integer.parseInt(details.getRoomsNumber()) <= Integer.parseInt(search.getRoomsMax())) {
            checkBathrooms(details);
        }
    }

    private static void checkBathrooms(HouseDetails details) {
        if (Integer.parseInt(details.getBathroomsNumber()) >= Integer.parseInt(search.getBathroomsMin())
                && Integer.parseInt(details.getBathroomsNumber()) <= Integer.parseInt(search.getBathroomsMax())) {
            checkBedrooms(details);
        }
    }

    private static void checkBedrooms(HouseDetails details) {
        if (Integer.parseInt(details.getBedroomsNumber()) >= Integer.parseInt(search.getBedroomsMin())
                && Integer.parseInt(details.getBedroomsNumber()) <= Integer.parseInt(search.getBedroomsMax())) {
            checkPrice(database.houseDao().getHouseById(details.getHouseId()), details);
        }

    }

    private static void checkPrice(House house, HouseDetails details){
        int housePrice = Integer.parseInt(house.getPrice().replaceAll(",", ""));
            if (housePrice >= Integer.parseInt(search.getPriceMin())
                    && housePrice <= Integer.parseInt(search.getPriceMax())) {
                checkAvailability(house, details);
            }
    }

    private static void checkAvailability(House house, HouseDetails details) {
        if (search.isAvailability() != "none") {
            if (search.isAvailability() == "true" && house.isAvailable()) {
                checkName(house, details);
            } else {
                if (search.isAvailability() == "false" && !house.isAvailable()) {
                    checkName(house, details);
                }
            }
        } else {
            checkName(house, details);
        }
    }

    private static void checkName(House house, HouseDetails details) {
        if (!search.getName().equals("Select...")) {
            if (house.getName().equals(search.getName())) {
                checkCity(house, details);
            }
        } else {
            checkCity(house, details);
        }

    }

    private static void checkCity(House house, HouseDetails details) {
        if (!search.getCity().equals("none")){
            AdressHouse adressHouse = database.adressDao().getAdressWithHouseId(house.getId());
            if (search.getCity().equals(adressHouse.getCity())){
                addHouse(details);
            }
        } else {
            addHouse(details);
        }
    }

    private static void addHouse(HouseDetails details){
        House house = getHouse(details.getHouseId());
        if (!houses.contains(house)) {
            houses.add(house);
        }
    }



    private static House getHouse(String id){
        House house = database.houseDao().getHouseById(id);
        return house;
    }

    public static void setNewSearch(){
        search = new Search();
        houses = new ArrayList<>();
        housesList = new ArrayList<>();
    }

    public static void setNull(){
        search = null;
        houses = null;
    }

}
