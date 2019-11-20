package com.openclassrooms.realestatemanager.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;

import com.openclassrooms.realestatemanager.DI.DI;
import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.service.RealEstateManagerAPIService;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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



    public static List<House> compareHousesLists(final List<House> housesFb){
        if (housesFb.size() > 0) {
                for (int i = 0; i < housesFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().insertHouse(housesFb.get(i));
                }
            }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).houseDao().getHouses();
    }

    public static List<AdressHouse> compareAdressLists(List<AdressHouse> adressHousesFb){
        if (adressHousesFb.size() > 0) {
                for (int i = 0; i < adressHousesFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().insertAdress(adressHousesFb.get(i));
                }
        }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).adressDao().getAdresses();
    }

    public static List<HouseDetails> compareDetailsLists(List<HouseDetails> detailsFb){
        if (detailsFb.size() > 0) {
                for (int i = 0; i < detailsFb.size(); i++) {
                    SaveToDatabase.getInstance(DI.getService().getActivity()).houseDetailsDao().insertDetails(detailsFb.get(i));

                }
        }
        return SaveToDatabase.getInstance(DI.getService().getActivity()).houseDetailsDao().getDetails();
    }

    public static List<Photo> comparePhotosLists(List<Photo> photosFb){

        if (photosFb.size() > 0) {
            for (int i = 0; i < photosFb.size(); i++) {
                SaveToDatabase.getInstance(DI.getService().getActivity()).photoDao().insertPhoto(photosFb.get(i));

            }
        }

        return SaveToDatabase.getInstance(DI.getService().getActivity()).photoDao().getPhotos();
    }

    public static String getRealPathFromURI(Uri contentURI) {
        String filePath;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = DI.getService().getActivity().getContentResolver().query(contentURI, proj, null, null, null);
        if (cursor == null) {
            filePath = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }
        return filePath;
    }

    public static String getPriceWithMonetarySystem(String valeurBrute, House house, DecimalFormat formatter){
        RealEstateManagerAPIService service = DI.getService();
        String result = null;
        if (service.getPreferences().getMonetarySystem().equals("€") && house.getMonetarySystem().equals("$")) {
            String resultString = formatter.format(Utils.convertDollarToEuro(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            result = "€" + " " + decimalReplacement;
        }
        if (service.getPreferences().getMonetarySystem().equals("$") && house.getMonetarySystem().equals("€")){
            String resultString = formatter.format(Utils.convertEuroToDollar(Integer.parseInt(valeurBrute)));
            String decimalReplacement = resultString.replaceAll("\\s", ",");
            result = "$ " + decimalReplacement;
        }
        if (service.getPreferences().getMonetarySystem().equals(house.getMonetarySystem())){
            result = house.getMonetarySystem() + " " + house.getPrice();
        }
        return result;
    }

    public static String getMeasureWithMeasureSystem(House house, HouseDetails details){
        RealEstateManagerAPIService service = DI.getService();
        String result = null;
        if (service.getPreferences().getMeasureUnity().equals(house.getMeasureUnity())) {
            result = details.getSurface() + " " + house.getMeasureUnity();
        }
        if (service.getPreferences().getMeasureUnity().equals("sq") && house.getMeasureUnity().equals("m")) {
            result = Utils.convertMetersToSquare(Integer.parseInt(details.getSurface())) + " " + "sq";
        }
        if (service.getPreferences().getMeasureUnity().equals("m") && house.getMeasureUnity().equals("sq")) {
            result = Utils.convertSquaresToMeters(Integer.parseInt(details.getSurface())) + " " + "m";
        }

        return result;
    }
}
