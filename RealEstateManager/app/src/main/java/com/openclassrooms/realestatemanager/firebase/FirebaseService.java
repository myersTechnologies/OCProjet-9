package com.openclassrooms.realestatemanager.firebase;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService implements FirebaseHelper {

    private List<User> users;
    private List<House> houses;
    private static String USERS = "users";
    private static String ID = "id";
    private static String USER_ID = "userId";
    private static String USER_PHOTO = "userPhoto";
    private static String USERNAME = "userName";
    private static String USER_EMAIL = "userEmail";
    private List<AdressHouse> adressHouses;
    private List<HouseDetails> detailsList;
    private List<Photo> photos;

    @Override
    public void setUsersList(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    @Override
    public List<House> getHouses() {
        return houses;
    }

    @Override
    public List<User> getUsersFromFireBase() {
        users = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = firebaseDatabase.getReference(USERS);
        databaseRef.orderByChild("userId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("userId").getValue().toString();
                    String name = postSnapshot.child("name").getValue().toString();
                    String email = postSnapshot.child("email").getValue().toString();
                    String image = postSnapshot.child("photoUri").getValue().toString();
                    User user = new User(id, name, email, image);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return users;
    }

    @Override
    public List<House> getHouseFromFirebase() {

        houses = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("house");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String name = postSnapshot.child("name").getValue().toString();
                    String agentId = postSnapshot.child("agentId").getValue().toString();
                    String available = postSnapshot.child("available").getValue().toString();
                    String monetarySystem = postSnapshot.child("monetarySystem").getValue().toString();
                    String price = postSnapshot.child("price").getValue().toString();
                    String measureUnity = postSnapshot.child("measureUnity").getValue().toString();
                    House house = new House(id,name, price, Boolean.parseBoolean(available), monetarySystem, measureUnity);
                    house.setAgentId(agentId);
                    houses.add(house);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        return houses;
    }


    @Override
    public void addUserToFireBase(User user) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("users");
        databaseRef.child(user.getUserId()).setValue(user);
        this.users.add(user);
    }

    @Override
    public void addHouseToFirebase(House house) {
        if (this.houses == null){
            this.houses = new ArrayList<>();
        }
        houses.add(house);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("house");
        databaseRef.child(String.valueOf(house.getId())).setValue(house);
    }

    @Override
    public void addAdressToFrirebase(AdressHouse adressHouse) {
        if (adressHouses == null){
            adressHouses = new ArrayList<>();
        }
        adressHouses.add(adressHouse);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("adresses");
        databaseRef.child(adressHouse.getId()).setValue(adressHouse);


    }

    @Override
    public List<AdressHouse> getHousesAdresses() {
        return adressHouses;
    }

    @Override
    public List<AdressHouse> getHousesAdressesFromFirebase() {
        if (adressHouses == null){
            adressHouses = new ArrayList<>();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("adresses");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String adress = postSnapshot.child("adress").getValue().toString();
                    String city = postSnapshot.child("city").getValue().toString();
                    String houseId = postSnapshot.child("houseId").getValue().toString();
                    String state = postSnapshot.child("state").getValue().toString();
                    String country = postSnapshot.child("country").getValue().toString();
                    String zipCode = postSnapshot.child("zipCode").getValue().toString();
                    AdressHouse adressHouse = new AdressHouse(id, houseId, adress, city, state, country, zipCode);
                    adressHouses.add(adressHouse);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        return adressHouses;
    }

    @Override
    public void addDetailsToFireBase(HouseDetails details) {
        if (this.detailsList == null){
            this.detailsList = new ArrayList<>();
        }
        this.detailsList.add(details);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("details");
        databaseRef.child(String.valueOf(details.getId())).setValue(details);
    }

    @Override
    public void getDetailsFromFireBase() {
        if (detailsList == null){
            detailsList = new ArrayList<>();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("details");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String id = postSnapshot.child("id").getValue().toString();
                    String houseId = postSnapshot.child("houseId").getValue().toString();
                    String surface = postSnapshot.child("surface").getValue().toString();
                    String roomsNumber = postSnapshot.child("roomsNumber").getValue().toString();
                    String bathroomsNumber = postSnapshot.child("bathroomsNumber").getValue().toString();
                    String bedroomsNumber = postSnapshot.child("bedroomsNumber").getValue().toString();
                    String onLineDate = postSnapshot.child("onLineDate").getValue().toString();
                    String description = postSnapshot.child("description").getValue().toString();
                    HouseDetails details = new HouseDetails(id, houseId);
                    details.setOnLineDate(onLineDate);
                    details.setDescription(description);
                    details.setSurface(surface);
                    details.setRoomsNumber(roomsNumber);
                    details.setBathroomsNumber(bathroomsNumber);
                    details.setBedroomsNumber(bedroomsNumber);
                    String soldDate = null;
                    if(postSnapshot.child("soldDate").exists()){
                        soldDate= postSnapshot.child("soldDate").getValue().toString();
                        details.setSoldDate(soldDate);
                    }
                    detailsList.add(details);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public List<HouseDetails> getDetails() {
        return detailsList;
    }


}
