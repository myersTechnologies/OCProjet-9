package com.openclassrooms.realestatemanager.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.openclassrooms.realestatemanager.db.dao.adress.AdressDao;
import com.openclassrooms.realestatemanager.db.dao.details.HouseDetailsDao;
import com.openclassrooms.realestatemanager.db.dao.house.HouseDao;
import com.openclassrooms.realestatemanager.db.dao.photo.PhotoDao;
import com.openclassrooms.realestatemanager.db.dao.preferences.PreferencesDao;
import com.openclassrooms.realestatemanager.db.dao.user.UserDao;
import com.openclassrooms.realestatemanager.model.AdressHouse;
import com.openclassrooms.realestatemanager.model.House;
import com.openclassrooms.realestatemanager.model.HouseDetails;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.Preferences;
import com.openclassrooms.realestatemanager.model.User;

@Database(entities = {User.class, Preferences.class, AdressHouse.class, House.class, Photo.class, HouseDetails.class}, version = 1, exportSchema = false)
public abstract class SaveToDatabase extends RoomDatabase {
    public static volatile SaveToDatabase INSTANCE;

    public abstract PreferencesDao preferencesDao();
    public abstract UserDao userDao();
    public abstract AdressDao adressDao();
    public abstract HouseDao houseDao();
    public abstract PhotoDao photoDao();
    public abstract HouseDetailsDao houseDetailsDao();

    public static SaveToDatabase getInstance(Context context){
        if (INSTANCE ==null){
            synchronized (SaveToDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SaveToDatabase.class, "RealEstateManager.db").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
