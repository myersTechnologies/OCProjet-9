package com.openclassrooms.realestatemanager.firebase;

import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

public interface FirebaseHelper {
    void setUsersList(List<User> users);
    List<User> getUsers();
    void setHouses(List<House> houses);
    List<House> getHouses();
    void setPreferences(Preferences preferences);
    Preferences getPreferences();
    List<User> getUsersFromFireBase();
    List<House> getHouseFromFirebase();
    Preferences getPreferencesFromFirebase();
    void addUserToFireBase(User user);
    void addHouseToFirebase(House house);
    void addPreferrencesToFirebase(Preferences preferences);
    void addAdressToFrirebase(AdressHouse adressHouse);
    List<AdressHouse> getHousesAdresses();
    List<AdressHouse> getHousesAdressesFromFirebase();

}
