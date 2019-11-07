package com.openclassrooms.realestatemanager.firebase;

import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

public interface FirebaseHelper {
    void setUsersList(List<User> users);
    List<User> getUsers();
    void setHouses(List<House> houses);
    List<House> getHouses();
    List<User> getUsersFromFireBase();
    List<House> getHouseFromFirebase();
    void addUserToFireBase(User user);
    void addHouseToFirebase(House house);
    void addAdressToFrirebase(AdressHouse adressHouse);
    List<AdressHouse> getHousesAdresses();
    List<AdressHouse> getHousesAdressesFromFirebase();
    void addDetailsToFireBase(HouseDetails details);
    void getDetailsFromFireBase();
    List<HouseDetails> getDetails();

}
