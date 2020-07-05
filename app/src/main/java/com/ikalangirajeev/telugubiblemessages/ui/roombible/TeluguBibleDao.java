package com.ikalangirajeev.telugubiblemessages.ui.roombible;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TeluguBibleDao {

    @Query("SELECT * FROM bible_telugu WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<TeluguBible> getTeluguBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_telugu WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    TeluguBible getTeluguBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_telugu WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_telugu WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_telugu WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
    List<TeluguBible> getQueriedBibleList(String query);
}
