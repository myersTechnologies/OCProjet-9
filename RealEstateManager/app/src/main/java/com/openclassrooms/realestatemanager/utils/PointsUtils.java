package com.openclassrooms.realestatemanager.utils;

import com.openclassrooms.realestatemanager.model.Search;

import java.util.ArrayList;
import java.util.List;

public class PointsUtils {
    public static List<String> getTransports(){
        List<String> tranportPoints = new ArrayList<>();
        tranportPoints.add("airport");
        tranportPoints.add("bus_station");
        tranportPoints.add("taxi_stand");
        tranportPoints.add("train_station");
        tranportPoints.add("transit_station");
        tranportPoints.add("subway_station");
        tranportPoints.add("light_rail_station");
        return tranportPoints;
    }

    public static List<String> getEstablissementsPoints(){
        List<String> establissements = new ArrayList<>();
        establissements.add("bicycle_store");
        establissements.add("book_store");
        establissements.add("car_rental");
        establissements.add("car_dealer");
        establissements.add("car_wash");
        establissements.add("clothing_store");
        establissements.add("department_store");
        establissements.add("electronics_store");
        establissements.add("florist");
        establissements.add("furniture_store");
        establissements.add("hardware_store");
        establissements.add("home_goods_store");
        establissements.add("jewelry_store");
        establissements.add("liquor_store");
        establissements.add("pet_store");
        establissements.add("shoe_store");
        establissements.add("store");
        establissements.add("travel_agency");
        establissements.add("library");
        establissements.add("bakery");
        establissements.add("insurance_agency");
        establissements.add("establishment");
        return establissements;
    }

    public static List<String> getEducationPoints(){
        List<String> educationPoints = new ArrayList<>();
        educationPoints.add("school");
        educationPoints.add("primary_school");
        educationPoints.add("secondary_school");
        educationPoints.add("university");
        return educationPoints;
    }

    public static List<String> getAmusementPoints(){
        List<String> amusementPoints = new ArrayList<>();
        amusementPoints.add("amusement_park");
        amusementPoints.add("aquarium");
        amusementPoints.add("art_gallery");
        amusementPoints.add("beauty_salon");
        amusementPoints.add("bowling_alley");
        amusementPoints.add("casino");
        amusementPoints.add("hair_care");
        amusementPoints.add("movie_rental");
        amusementPoints.add("movie_theater");
        amusementPoints.add("night_club");
        amusementPoints.add("museum");
        amusementPoints.add("night_club");
        amusementPoints.add("park");
        amusementPoints.add("spa");
        amusementPoints.add("zoo");
        amusementPoints.add("tourist_attraction");
        amusementPoints.add("travel_agency");
        amusementPoints.add("neighborhood");
        return amusementPoints;

    }

    public static List<String> getHealthPoints(){
        List<String> healthPoints = new ArrayList<>();
        healthPoints.add("dentist");
        healthPoints.add("doctor");
        healthPoints.add("pharmacy");
        healthPoints.add("drugstore");
        healthPoints.add("hospital");
        healthPoints.add("physiotherapist");
        healthPoints.add("health");
        return healthPoints;
    }

    public static List<String> getUtilsPoints(){
        List<String> utilsPoints = new ArrayList<>();
        utilsPoints.add("electrician");
        utilsPoints.add("laundry");
        utilsPoints.add("locksmith");
        utilsPoints.add("moving_company");
        utilsPoints.add("painter");
        utilsPoints.add("plumber");
        utilsPoints.add("roofing_contractor");
        utilsPoints.add("storage");
        return utilsPoints;
    }

    public static List<String> getPoliticalPoints(){
        List<String> politicalPoints = new ArrayList<>();
        politicalPoints.add("city_hall");
        politicalPoints.add("embassy");
        politicalPoints.add("locality");
        politicalPoints.add("political");
        politicalPoints.add("local_government_office");
        return politicalPoints;
    }

    public static List<String> getReligiousPoints(){
        List<String> religiousPoints = new ArrayList<>();
        religiousPoints.add("cemetery");
        religiousPoints.add("church");
        religiousPoints.add("funeral_home");
        religiousPoints.add("hindu_temple");
        religiousPoints.add("mosque");
        religiousPoints.add("synagogue");
        religiousPoints.add("religious");
        return religiousPoints;
    }

    public static List<String> getMarketPoints(){
        List<String> marketPoints = new ArrayList<>();
        marketPoints.add("convenience_store");
        marketPoints.add("grocery_or_supermarket");
        marketPoints.add("supermarket");
        return marketPoints;
    }

    public static List<String> getMoneyPoints(){
        List<String> moneyPoints = new ArrayList<>();
        moneyPoints.add("accounting");
        moneyPoints.add("atm");
        moneyPoints.add("bank");
        return moneyPoints;
    }

    public static List<String> getCourtPoints(){
        List<String> courtPoints = new ArrayList<>();
        courtPoints.add("courthouse");
        courtPoints.add("lawyer");
        return courtPoints;
    }

    public static List<String> getRestaurantsPoints(){
        List<String> restaurantPoints = new ArrayList<>();
        restaurantPoints.add("bakery");
        restaurantPoints.add("cafe");
        restaurantPoints.add("bar");
        restaurantPoints.add("meal_delivery");
        restaurantPoints.add("food");
        restaurantPoints.add("meal_takeaway");
        restaurantPoints.add("restaurant");
        return restaurantPoints;
    }

    public static List<String> getCampsAnHotels(){
        List<String> campAndHotel = new ArrayList<>();
        campAndHotel.add("campground");
        campAndHotel.add("rv_park");
        campAndHotel.add("hotel");
        return campAndHotel;
    }



}
