package com.ikalangirajeev.telugubiblemessages.ui.bible.app.search;

import android.text.Spannable;

public class SearchData {
    private String header;
    private String body;
    private String bookName;
    private Integer bookNumber;
    private Integer chapternumber;
    private Integer verseNumber;

    public SearchData(String header, String body, String bookName, Integer bookNumber, Integer chapternumber, Integer verseNumber) {
        this.header = header;
        this.body = body;
        this.bookName = bookName;
        this.bookNumber = bookNumber;
        this.chapternumber = chapternumber;
        this.verseNumber = verseNumber;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public Integer getChapternumber() {
        return chapternumber;
    }

    public Integer getVerseNumber() {
        return verseNumber;
    }
}
