package com.openclassrooms.realestatemanager;

import android.app.Instrumentation;
import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.util.Log;

import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Console;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class UnitTest {

    @Test
    public void getDateUtilsClass(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        assertEquals(date, "16/01/2020");
    }


    @Test
    public void convertDollarToEuro(){
        String dollars = "1,200.00";
        String valeurBrute = dollars.replaceAll(",", "").replace(".", "");
        int valeur = (int) Math.abs(Integer.parseInt(valeurBrute) * 1.12);
        assertTrue(valeur == 134400);
    }

    @Test
    public void convertEuroToDollar(){
        String euros = "1,200.00";
        String valeurBrute = euros.replaceAll(",", "").replace(".", "");
        int valeur = (int) Math.abs(Integer.parseInt(valeurBrute) * 0.90);
        assertTrue(valeur == 108000);
    }

    @Test
    public void convertSquareToMeters(){
        int square = 11;
        int valeur = (int)  Math.abs(square * 0.092);
        assertEquals(1, valeur);
    }

    @Test
    public void convertMetersToSquare(){
        int meters = 2;
        int valeur = (int)Math.abs(meters * 10.76);
        assertEquals(21, valeur);
    }

    @Test
    public void compareHousesListFirstSendsEmpty(){
        List<House> houses = new ArrayList<>();
        List<House> dbHouses = new ArrayList<>();
        House house = new House("11", "some", "500,000", true, "€", "m" );
        houses.add(house);
        assertTrue(houses.size() > 0);
        assertTrue(dbHouses.isEmpty());
    }
    @Test
    public void compareHousesListHaveSameItems(){
        List<House> houses = new ArrayList<>();
        List<House> dbHouses = new ArrayList<>();
        House house = new House("11", "some", "500,000", true, "€", "m" );
        houses.add(house);
        dbHouses.add(house);
        boolean check = false;
        for (int i = 0; i < houses.size(); i++){
            for (int j =0; j < dbHouses.size(); j++){
                if (houses.get(i).getId().equals(dbHouses.get(j).getId())){
                    check = true;
                }
            }
        }
        assertEquals(String.valueOf(check), "true");
    }

    @Test
    public void compareHousesListHaveNotSameItemsAndAddItem(){
        List<House> houses = new ArrayList<>();
        List<House> dbHouses = new ArrayList<>();
        House house = new House("11", "some", "500,000", true, "€", "m" );
        House house1 = new House("111", "some", "500,000", true, "€", "m" );
        House house2 = new House("1111", "some", "500,000", true, "€", "m" );
        houses.add(house);
        houses.add(house2);
        dbHouses.add(house1);
        houses.add(house1);
        dbHouses.add(house);
        for (int i = 0; i < houses.size(); i++){
            if (!dbHouses.contains(houses.get(i))){
                assertTrue(!dbHouses.contains(house2));
                dbHouses.add(houses.get(i));
            }

        }
        assertTrue(dbHouses.contains(house2));
    }




}