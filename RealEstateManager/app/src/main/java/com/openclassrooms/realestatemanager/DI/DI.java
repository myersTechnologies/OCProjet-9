package com.openclassrooms.realestatemanager.DI;

import com.openclassrooms.realestatemanager.firebase.FirebaseHelper;
import com.openclassrooms.realestatemanager.firebase.FirebaseService;
import com.openclassrooms.realestatemanager.service.APIService;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

public class DI {

    private static RealEstateManagerAPIService service = new APIService();
    private static FirebaseHelper firebaseDatabase = new FirebaseService();

    public static RealEstateManagerAPIService getService(){
        return service;
    }
    public static FirebaseHelper getFirebaseDatabase(){
        return firebaseDatabase;
    }
}
