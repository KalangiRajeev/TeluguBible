package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Book{

    @SerializedName("Chapter")
    private List<Chapter> chapters;

    public Book(){
    }

    public Book(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
