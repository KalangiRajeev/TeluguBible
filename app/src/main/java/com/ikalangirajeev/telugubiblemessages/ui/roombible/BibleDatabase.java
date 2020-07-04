package com.ikalangirajeev.telugubiblemessages.ui.roombible;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {EnglishBible.class, TamilBible.class, TeluguBible.class, KannadaBible.class}, version = 1, exportSchema = false)
public abstract class BibleDatabase extends RoomDatabase {

    private static BibleDatabase bibleDatabase;

    public abstract EnglishBibleDao englishBibleDao();
    public abstract TeluguBibleDao teluguBibleDao();
    public abstract TamilBibleDao tamilBibleDao();
    public abstract KannadaBibleDao kannadaBibleDao();

    public static synchronized BibleDatabase getBibleDatabase(Context context){

        if(bibleDatabase ==null){
            bibleDatabase = Room.databaseBuilder(context.getApplicationContext(), BibleDatabase.class, "indian_bibles.db")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("indian_bibles.db")
                    .build();
        }

        return bibleDatabase;
    }
}
