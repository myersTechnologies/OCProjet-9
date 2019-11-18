package com.openclassrooms.realestatemanager.db.dao.details;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

@Dao
public interface HouseDetailsDao {

    @Query("SELECT * FROM housedetails")
    List<HouseDetails> getDetails();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDetails(HouseDetails houseDetails);

    @Query("SELECT * FROM housedetails WHERE house_id = :houseId")
    HouseDetails getDetailsWithHouseId(String houseId);


    @Query("SELECT * FROM housedetails WHERE id = :detailsId")
    HouseDetails getDetailsWithId(String detailsId);

    @Update
    void updateDetails(HouseDetails houseDetails);

    //Content provider
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHouseDetailsProvider(HouseDetails houseDetails);

    @Query("SELECT * FROM HouseDetails WHERE id = :detailsId")
    Cursor getHouseDetailsWithCursor(String detailsId);

    @Update
    int updateHouseDetailsProvider(HouseDetails houseDetails);

}
