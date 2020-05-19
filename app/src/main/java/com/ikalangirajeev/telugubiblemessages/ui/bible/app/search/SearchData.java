package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;

import android.text.Spannable;

public class SearchData {
    private Spannable header;
    private String body;
    private int bookNumber;
    private int chapterNumber;
    private int verseNumber;
    private  String bookName;

    public SearchData(Spannable header, String body) {
        this.header = header;
        this.body = body;
    }

    public void setBookNumber(int bookNumber) {
        this.bookNumber = bookNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public void setVerseNumber(int verseNumber) {
        this.verseNumber = verseNumber;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Spannable getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public int getBookNumber() {
        return bookNumber;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public int getVerseNumber() {
        return verseNumber;
    }

    public String getBookName() {
        return bookName;
    }
}
