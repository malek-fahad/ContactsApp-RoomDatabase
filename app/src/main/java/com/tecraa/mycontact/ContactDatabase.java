package com.tecraa.mycontact;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    public abstract ContactDAO getDao();

    static ContactDatabase database = null;

    public static ContactDatabase getDatabase(Context context){

        if (database==null){
            database = Room.databaseBuilder(
                            context,
                            ContactDatabase.class,
                            "contact_database")
                    .allowMainThreadQueries()
                    .build();
        }

        return database;
    }
}
