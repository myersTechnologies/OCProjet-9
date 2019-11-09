package com.openclassrooms.realestatemanager.db.provider.photo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.openclassrooms.realestatemanager.db.SaveToDatabase;
import com.openclassrooms.realestatemanager.model.Photo;
import com.openclassrooms.realestatemanager.model.User;

public class PhotoContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.openclassrooms.realestatemanager.db.provider";
    public static final String TABLE_NAME = Photo.class.getSimpleName();
    public static final Uri URI_ITEM = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null){
            String photoId = String.valueOf(ContentUris.parseId(uri));
            final Cursor cursor = SaveToDatabase.getInstance(getContext()).photoDao().getPhotoWithCursor(photoId);
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }

        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        if (getContext() != null){
            final long id = SaveToDatabase.getInstance(getContext()).photoDao().insertPhotoProvider(Photo.fromContentValues(contentValues));
            if (id != 0){
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }

        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String where, @Nullable String[] whereStrings) {
        if (getContext() != null){
            final int count = SaveToDatabase.getInstance(getContext()).photoDao().deletePhotoFromProvider(where);
            getContext().getContentResolver().delete(URI_ITEM, where, whereStrings);
            return count;
        }
        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        if (getContext() != null){
            final int count = SaveToDatabase.getInstance(getContext()).photoDao().updatePhotoProvider(Photo.fromContentValues(contentValues));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }
}
