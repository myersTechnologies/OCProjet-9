package com.openclassrooms.realestatemanager.db.dao.user;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.openclassrooms.realestatemanager.model.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void createUserTable(User user);

    @Query("SELECT * FROM user WHERE user_id = user_id")
    User getUser();



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUserProvider(User user);

    @Query("SELECT * FROM user WHERE user_id = :userId")
    Cursor getUserWithCursor(String userId);


    @Update
    int updateUserProvider(User user);

}
