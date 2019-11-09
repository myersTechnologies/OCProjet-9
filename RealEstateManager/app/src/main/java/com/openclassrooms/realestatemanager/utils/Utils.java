package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    public static int convertDollarToEuro(int dollars){
        return (int) Math.abs(dollars * 0.90);
    }

    public static int convertEuroToDollar(int euros){
        return (int) Math.abs(euros * 1.12);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    //changed simpledateformat yyyy/MM/dd to ss/MM/yyyy
    public static String getTodayDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    public static Boolean isInternetAvailable(Context context){
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        return connected;
    }

    public static int convertSquaresToMeters(int square){
        return (int) Math.abs(square * 0.092);
    }

    public static int convertMetersToSquare(int meter){
        return (int) Math.abs(meter * 10.76);
    }

    public static List<House> compareHousesLists(List<House> housesFb, List<House> houses1Db){
        if (housesFb.size() > 0) {
                for (int i = 0; i < housesFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().insertHouse(housesFb.get(i));
                }
            }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses();
    }

    public static List<AdressHouse> compareAdressLists(List<AdressHouse> adressHousesFb, List<AdressHouse> adressHouses1Db){
        if (adressHousesFb.size() > 0) {
                for (int i = 0; i < adressHousesFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().insertAdress(adressHousesFb.get(i));
                }
        }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses();
    }

    public static List<HouseDetails> compareDetailsLists(List<HouseDetails> detailsFb, List<HouseDetails> detailsDb){
        if (detailsFb.size() > 0) {
                for (int i = 0; i < detailsFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).houseDetailsDao().insertDetails(detailsFb.get(i));

                }
        }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).houseDetailsDao().getDetails();
    }

    public static List<Photo> comparePhotosLists(List<Photo> photosFb, List<Photo> photosDb){
        if (photosFb.size() > 0) {
            for (int i = 0; i < photosFb.size(); i++) {
                SaveToDatabase.getInstance(DI.getService().getActivity()).photoDao().insertPhoto(photosFb.get(i));
            }
        }

        return SaveToDatabase.getInstance(DI.getService().getActivity()).photoDao().getPhotos();
    }
}
