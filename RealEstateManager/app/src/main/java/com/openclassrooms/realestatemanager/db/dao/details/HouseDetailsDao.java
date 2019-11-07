package com.openclassrooms.realestatemanager.db.dao.details;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;

import java.util.List;

@Dao
public interface HouseDetailsDao {
    @Query("SELECT * FROM housedetails")
    List<HouseDetails> getDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(HouseDetails houseDetails);

    @Delete
    void deleteDetails(HouseDetails houseDetails);
}
