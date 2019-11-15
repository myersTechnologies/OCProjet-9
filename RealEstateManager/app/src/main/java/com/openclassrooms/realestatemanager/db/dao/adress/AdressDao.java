package com.openclassrooms.realestatemanager.db.dao.adress;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.AdressHouse;

import java.util.List;

@Dao
public interface AdressDao {

   @Query("SELECT * FROM adresshouse")
    List<AdressHouse> getAdresses();

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdress(AdressHouse adressHouse);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAdressProvider(AdressHouse adressHouse);

    @Query("SELECT * FROM AdressHouse WHERE adress_id = :adressId")
    Cursor getAdressWithCursor(String adressId);


    @Update
    int updateAdressProvider(AdressHouse adressHouse);

    @Query("DELETE FROM AdressHouse WHERE adress_id = :adressId")
    int deletAdressFromProvider(String adressId);


}
