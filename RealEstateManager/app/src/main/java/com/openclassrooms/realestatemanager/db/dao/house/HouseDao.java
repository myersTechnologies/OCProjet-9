package com.openclassrooms.realestatemanager.db.dao.house;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;

import java.util.List;

@Dao
public interface HouseDao {

    @Query("SELECT * FROM house WHERE id = id")
    List<House> getHouses();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHouse(House house);

    @Delete
    void deleteHouse(House house);
}
