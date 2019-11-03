package com.openclassrooms.realestatemanager;

import android.util.Log;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.Console;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {
    @Test
    public void getDateUtilsClass(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        assertEquals(date, "03/11/2019");
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
}