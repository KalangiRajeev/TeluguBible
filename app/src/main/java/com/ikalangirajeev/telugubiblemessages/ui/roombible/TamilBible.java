package com.ikalangirajeev.telugubiblemessages.ui.roombible;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "bible_tamil")
public class TamilBible {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int verseid;

    @NonNull
    private int Book;

    @NonNull
    private int Chapter;

    @NonNull
    private int Versecount;

    @NonNull
    private String verse;

    public TamilBible(int Book, int Chapter, int Versecount, String verse) {
        this.Book = Book;
        this.Chapter = Chapter;
        this.Versecount = Versecount;
        this.verse = verse;
    }

    public void setVerseid(int verseid) {
        this.verseid = verseid;
    }

    public int getVerseid() {
        return verseid;
    }

    public int getBook() {
        return Book;
    }

    public int getChapter() {
        return Chapter;
    }

    public int getVersecount() {
        return Versecount;
    }

    public String getVerse() {
        return verse;
    }
}
