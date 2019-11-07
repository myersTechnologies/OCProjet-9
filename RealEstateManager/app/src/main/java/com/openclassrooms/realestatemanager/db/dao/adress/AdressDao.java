package com.openclassrooms.realestatemanager.db.dao.adress;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.model.AdressHouse;

import java.util.List;

@Dao
public interface AdressDao {

   @Query("SELECT * FROM adresshouse")
    List<AdressHouse> getAdresses();

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAdress(AdressHouse adressHouse);

   @Delete
    void deleteAdress(AdressHouse adressHouse);
}
