package com.ikalangirajeev.telugubiblemessages.ui.roombible;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TamilBibleDao {
    @Query("SELECT * FROM bible_tamil WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<TamilBible> getTamilBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_tamil WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    TamilBible getTamilBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_tamil WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_tamil WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_tamil WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
     List<TamilBible> getQueriedBibleList(String query);
}
