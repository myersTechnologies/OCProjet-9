package com.openclassrooms.realestatemanager.db.dao.photo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.User;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("SELECT * FROM photo")
    List<Photo> getPhotos();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPhoto(Photo photo);

    @Query("SELECT * FROM photo WHERE house_id = :houseId")
    Photo getPhotoWithHouseId(String houseId);

    @Query("SELECT * FROM photo WHERE photo_id = :id")
    Photo getPhototWithId(String id);

    @Delete
    void deletePhoto(Photo photo);

    @Update
    void updatePhoto(Photo photo);


    //For content provider
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPhotoProvider(Photo photo);

    @Query("SELECT * FROM Photo WHERE photo_id = :photoId")
    Cursor getPhotoWithCursor(String photoId);


    @Update
    int updatePhotoProvider(Photo photo);

    @Query("DELETE FROM Photo WHERE photo_id = :photoId")
    int deletePhotoFromProvider(String photoId);

}
