package com.ikalangirajeev.telugubiblemessages.ui.roombible;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HindiBibleDao {
    @Query("SELECT * FROM bible_hindi WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<HindiBible> getHindiBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_hindi WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    HindiBible getHindiBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_hindi WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_hindi WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_hindi WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
    List<HindiBible> getQueriedBibleList(String query);
}
