package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DictEngTel.class}, version = 1)
public abstract class DictDatabase extends RoomDatabase {

    private static DictDatabase dictDatabase;

    public abstract DictDao dictDao();

    public static synchronized DictDatabase getDictDatabase(Context context) {

        if(dictDatabase == null){
            dictDatabase = Room.databaseBuilder(context.getApplicationContext(), DictDatabase.class, "eng2tel_dictionary")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("eng2tel_dictionary")
                    .build();
        }
        return dictDatabase;
    }
}
