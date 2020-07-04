package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DictEngTel.class}, version = 1, exportSchema = false)
public abstract class DictDatabase extends RoomDatabase {

    private static DictDatabase dictDatabase;

    public abstract DictDao dictDao();

    public static synchronized DictDatabase getDictDatabase(Context context) {

        if(dictDatabase == null){
            dictDatabase = Room.databaseBuilder(context.getApplicationContext(), DictDatabase.class, "eng2tel_dictionary.db")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("eng2tel_dictionary.db")
                    .build();
        }
        return dictDatabase;
    }
}
