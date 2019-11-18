package com.openclassrooms.realestatemanager.db.dao.preferences;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

@Dao
public interface PreferencesDao {

   @Query("SELECT * FROM preferences WHERE user_id = user_id")
   Preferences getPreferences();

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPreferences(Preferences preferences);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPreferencesProvider(Preferences preferences);


    @Query("SELECT * FROM Preferences WHERE preferences_id = :preferencesId")
    Cursor getPreferencesWithCursor(String preferencesId);

    @Update
    int updatePreferencesProvider(Preferences preferences);

    @Query("DELETE FROM Preferences WHERE preferences_id = :preferencesId")
    int deletePreferencesFromProvider(String preferencesId);
}
