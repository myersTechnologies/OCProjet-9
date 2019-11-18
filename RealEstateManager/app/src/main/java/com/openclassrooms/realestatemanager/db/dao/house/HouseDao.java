package com.openclassrooms.realestatemanager.db.dao.house;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

@Dao
public interface HouseDao {

    @Query("SELECT * FROM house WHERE id = id")
    List<House> getHouses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouse(House house);

    @Query("SELECT * FROM house WHERE id = :houseId")
    House getHouseById(String houseId);
    @Update
    void updateHouse(House house);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertHouseProvider(House photo);

    @Query("SELECT * FROM House WHERE id = :houseId")
    Cursor getHouseWithCursor(String houseId);


    @Update
    int updateHouseProvider(House house);

    @Query("DELETE FROM House WHERE id = :houseId")
    int deleteHouseFromProvider(String houseId);

}
