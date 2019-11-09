package com.openclassrooms.realestatemanager;

import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.db.provider.user.UserContentProvider;
import com.openclassrooms.realestatemanager.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserContentProviderTest {

    private ContentResolver contentResolver;

    private static String USER_ID = "1";

    @Before
    public void setUp(){
        Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SaveToDatabase.class).allowMainThreadQueries().build();
        contentResolver = InstrumentationRegistry.getContext().getContentResolver();
    }

    @Test
    public void getItemsWhenNoUserInserted() {
        final Cursor cursor = contentResolver.query(ContentUris.withAppendedId(UserContentProvider.URI_ITEM, Long.parseLong(USER_ID)),
                null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        cursor.close();
    }


    @Test
    public void insertAndGetUser() {
        // BEFORE : Adding demo item
        final Uri userUri = contentResolver.insert(UserContentProvider.URI_ITEM, generateUser());
        // TEST
        final Cursor cursor = contentResolver.query(ContentUris.withAppendedId(UserContentProvider.URI_ITEM, Long.parseLong(USER_ID)),
                null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("user_name")), is("Marco"));
        cursor.close();
    }

    @Test
    public void updateAndGetUser() {
        final Uri userUri1 = contentResolver.insert(UserContentProvider.URI_ITEM, generateUser());
        // TEST
        final Cursor cursor1 = contentResolver.query(ContentUris.withAppendedId(UserContentProvider.URI_ITEM, Long.parseLong(USER_ID)),
                null, null, null, null);
        assertThat(cursor1, notNullValue());
        assertThat(cursor1.getCount(), is(1));
        assertThat(cursor1.moveToFirst(), is(true));
        assertThat(cursor1.getString(cursor1.getColumnIndexOrThrow("user_name")), is("Marco"));
        cursor1.close();
        // BEFORE : Adding demo item
        final int userUri = contentResolver.update(UserContentProvider.URI_ITEM, generateUser1(), generateUser().getAsString("user_id"), null);
        // TEST
        final Cursor cursor = contentResolver.query(ContentUris.withAppendedId(UserContentProvider.URI_ITEM, Long.parseLong(USER_ID)),
                null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("user_name")), is("Marco da Silva"));
        cursor.close();
    }



    private ContentValues generateUser(){
        final ContentValues values = new ContentValues();
        values.put("user_id", "1");
        values.put("user_name", "Marco");
        values.put("user_photo", "photo.png");
        values.put("user_email", "user@gmail.com");
        return values;
    }

    private ContentValues generateUser1(){
        final ContentValues values = new ContentValues();
        values.put("user_id", "1");
        values.put("user_name", "Marco da Silva");
        values.put("user_photo", "photo.png");
        values.put("user_email", "user@gmail.com");
        return values;
    }

}
