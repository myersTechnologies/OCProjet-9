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
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService implements FirebaseHelper {

    private List<User> users;
    private List<House> houses;
    private Preferences preferences;
    private RealEstateManagerAPIService service = DI.getService();
    private static String USERS = "users";
    private static String ID = "id";
    private static String USER_ID = "userId";
    private static String USER_PHOTO = "userPhoto";
    private static String USERNAME = "userName";
    private static String USER_EMAIL = "userEmail";
    private static String IMAGE_URL = "imageUrl";
    private static String PREFERENCES = "preferences";
    private static String MENU_IMAGE = "menuImage";
    private static String MONETARY_SYSTEM = "monetarySystem";
    private List<AdressHouse> adressHouses;

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
    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public Preferences getPreferences() {
        return preferences;
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
    public Preferences getPreferencesFromFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(PREFERENCES);
        databaseReference.orderByChild(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.child(USER_ID).getValue().toString().equals(service.getUser().getUserId())) {
                        String id = postSnapshot.child(ID).getValue().toString();
                        String userId = postSnapshot.child(USER_ID).getValue().toString();
                        String name = postSnapshot.child(USERNAME).getValue().toString();
                        String image = null;
                        if (postSnapshot.child("userPhoto").exists()) {
                            image = postSnapshot.child("userPhoto").getValue().toString();
                        }
                        String menuImage = null;
                        if (postSnapshot.child(MENU_IMAGE).exists()) {
                            menuImage = postSnapshot.child(MENU_IMAGE).getValue().toString();
                        }
                        String monetarySystem = postSnapshot.child(MONETARY_SYSTEM).getValue().toString();
                        String measureUnity = null;
                        if (postSnapshot.child("measureUnity").exists()) {
                            measureUnity = postSnapshot.child("measureUnity").getValue().toString();
                        }
                        preferences = new Preferences(id, userId, monetarySystem, name, image, menuImage, measureUnity);
                        service.setPreferences(preferences);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        return preferences;
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
    public void addPreferrencesToFirebase(Preferences preferences) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = firebaseDatabase.getReference(PREFERENCES);
        databaseRef.child(preferences.getId()).setValue(preferences);
        this.preferences = preferences;
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


}
