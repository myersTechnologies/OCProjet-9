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


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    //changed simpledateformat yyyy/MM/dd to dd/MM/yyyy
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



    public static void compareHousesLists(House house){
       SaveToDatabase database = SaveToDatabase.getInstance(DI.getService().getActivity());

       database.houseDao().saveHouse(house);

    }

    public static void compareAdressLists(AdressHouse adressHouse){
        SaveToDatabase database = SaveToDatabase.getInstance(DI.getService().getActivity());

        database.adressDao().saveAdress(adressHouse);

    }

    public static void compareDetailsLists(HouseDetails details){
        SaveToDatabase database = SaveToDatabase.getInstance(DI.getService().getActivity());

        database.houseDetailsDao().saveDetails(details);


    }

    public static void comparePhotosLists(Photo photo){
        SaveToDatabase database = SaveToDatabase.getInstance(DI.getService().getActivity());
        database.photoDao().savePhoto(photo);

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
