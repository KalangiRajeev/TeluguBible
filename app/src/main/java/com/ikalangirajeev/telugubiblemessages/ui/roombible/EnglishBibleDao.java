package com.ikalangirajeev.telugubiblemessages.ui.roombible;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EnglishBibleDao {

    @Query("SELECT * FROM bible_english WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<EnglishBible> getEnglishBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_english WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    EnglishBible getEnglishBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_english WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_english WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_english WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
    List<EnglishBible> getQueriedBibleList(String query);
}
