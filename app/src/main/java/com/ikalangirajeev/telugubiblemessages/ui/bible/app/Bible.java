package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bible {

    @SerializedName("book")
    private List<Book> books;

    public Bible() {
    }

    public Bible(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
