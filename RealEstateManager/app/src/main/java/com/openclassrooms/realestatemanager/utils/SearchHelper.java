package com.openclassrooms.realestatemanager.utils;



import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.R;
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
                checkPoints(house, details);
            }
        } else {
            checkPoints(house, details);
        }
    }

    private static void checkPoints(House house, HouseDetails details){
        if (!search.getPointsOfInterest().equals("none")){
            String points = search.getPointsOfInterest();
            String housePoint = house.getPointsOfInterest();
            List<String> searchPoints = new ArrayList<>();
            for (int i = 0; i < points.split(",").length; i++){
                searchPoints.add(points.split(",")[i]);
            }


            List<String> housePoints = new ArrayList<>();
            for (int j = 0; j < housePoint.split(",").length; j++){
                housePoints.add(housePoint.split(",")[j]);
            }

            List<String> common = new ArrayList<>(searchPoints);
            common.retainAll(housePoints);
            if (housePoints.size() == common.size()) {
               checkOnSaleDate(house, details);
            }
        } else {
            checkOnSaleDate(house, details);
        }
    }

    private static void checkOnSaleDate(House house, HouseDetails details){
        if (!search.getOnSaleDate().equals("none")){
            if (search.getOnSaleDate().equals(details.getOnLineDate())){
                checkSoldDate(house, details);
            }
        }else {
            checkSoldDate(house, details);
        }
    }

    private static void checkSoldDate(House house, HouseDetails details){
        if (!search.getSoldDate().equals("none")){
            if (search.getSoldDate().equals(details.getSoldDate())){
                addHouse(house);
            }
        } else {
            addHouse(house);
        }
    }

    private static void addHouse(House house){
        if (!houses.contains(house)) {
            houses.add(house);
        }
    }



    private static House getHouse(String id){
        House house = database.houseDao().getHouseById(id);
        return house;
    }

    public static void setNewSearch(){
        if (search == null) {
            search = new Search();
            houses = new ArrayList<>();
        }
    }

    public static void setNull(){
        search = null;
        houses = null;
    }

    public static List<Integer> getPointsTypesImage(){
        List<Integer> points = new ArrayList<>();
        points.add(R.drawable.transports);
        points.add(R.drawable.parc_amusant);
        points.add(R.drawable.education);
        points.add(R.drawable.store);
        points.add(R.drawable.health);
        points.add(R.drawable.utils);
        points.add(R.drawable.political);
        points.add(R.drawable.pray);
        points.add(R.drawable.food);
        points.add(R.drawable.hotel);
        points.add(R.drawable.court);
        points.add(R.drawable.cart);
        points.add(R.drawable.money);
        points.add(R.drawable.police);
        points.add(R.drawable.fire);
        points.add(R.drawable.gym);
        points.add(R.drawable.gas);
        points.add(R.drawable.logo);
        points.add(R.drawable.stadium);
        points.add(R.drawable.parking);
        points.add(R.drawable.post_offce);
        points.add(R.drawable.car);
        return points;
    }
    public static List<String> getPointsTypesString(){
        List<String> points = new ArrayList<>();
        points.add("Transports");
        points.add("Diversion");
        points.add("School");
        points.add("Establishment");
        points.add("Health");
        points.add("Utils");
        points.add("Politic");
        points.add("Religion");
        points.add("Food");
        points.add("Hotel");
        points.add("Court");
        points.add("Market");
        points.add("Bank");
        points.add("Police");
        points.add("Fire Station");
        points.add("Gym");
        points.add("Gas station");
        points.add("Agency");
        points.add("Stadium");
        points.add("Parking");
        points.add("Post Office");
        points.add("Car Workshop");
        return points;
    }

}
