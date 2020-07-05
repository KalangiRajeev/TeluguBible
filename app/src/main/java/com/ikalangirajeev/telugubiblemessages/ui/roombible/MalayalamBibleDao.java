package com.ikalangirajeev.telugubiblemessages.ui.roombible;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MalayalamBibleDao {
    @Query("SELECT * FROM bible_malayalam WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<MalayalamBible> getMalayalamBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_malayalam WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    MalayalamBible getMalayalamBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_malayalam WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_malayalam WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_malayalam WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
    List<MalayalamBible> getQueriedBibleList(String query);
}
