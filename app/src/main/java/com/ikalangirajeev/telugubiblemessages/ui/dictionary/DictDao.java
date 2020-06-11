package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DictDao {

    @Query("SELECT * FROM eng2te WHERE eng_word LIKE '%'||:searchQuery||'%' OR meaning LIKE '%'||:searchQuery||'%' ORDER BY eng_word ASC")
    LiveData<List<DictEngTel>> getDictEngTelList(String searchQuery);

}
