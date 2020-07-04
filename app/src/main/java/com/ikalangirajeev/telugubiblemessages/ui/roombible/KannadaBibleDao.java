package com.ikalangirajeev.telugubiblemessages.ui.roombible;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface KannadaBibleDao {
    @Query("SELECT * FROM bible_kannada WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    List<KannadaBible> getKannadaBibleList(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_kannada WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo AND Versecount LIKE :verseNo")
    KannadaBible getKannadaBibleVerse(int bookNo, int chapterNo, int verseNo);

    @Query("SELECT MAX(Chapter) FROM bible_kannada WHERE Book IN (:bookNo)")
    Integer getChaptersCount(int bookNo);

    @Query("SELECT  MAX(Versecount) FROM bible_kannada WHERE Book LIKE :bookNo AND Chapter LIKE :chapterNo")
    Integer getVersesCount(int bookNo, int chapterNo);

    @Query("SELECT * FROM bible_kannada WHERE verse LIKE '%'||:query||'%' ORDER BY Book ASC")
    List<KannadaBible> getQueriedBibleList(String query);
}
