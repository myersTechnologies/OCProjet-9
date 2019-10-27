package com.openclassrooms.realestatemanager.DI;

import com.openclassrooms.realestatemanager.service.APIService;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

public class DI {

    private static RealEstateManagerAPIService service = new APIService();

    public static RealEstateManagerAPIService getService(){
        return service;
    }
}
