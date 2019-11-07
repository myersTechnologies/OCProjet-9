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
    private List<HouseDetails> details;

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

        if (houses == null){
            houses = new ArrayList<>();
        }

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("house");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

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
        houses.add(house);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("house");
        databaseRef.child(String.valueOf(house.getId())).setValue(house);
    }

    @Override
    public void addAdressToFrirebase(AdressHouse adressHouse) {
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
        if (this.details == null){
            this.details = new ArrayList<>();
        }
        this.details.add(details);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference("details");
        databaseRef.child(String.valueOf(details.getId())).setValue(details);
    }

    @Override
    public void getDetailsFromFireBase() {
        if (details == null){
            details = new ArrayList<>();
        }
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("details");
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public List<HouseDetails> getDetails() {
        return details;
    }


}
