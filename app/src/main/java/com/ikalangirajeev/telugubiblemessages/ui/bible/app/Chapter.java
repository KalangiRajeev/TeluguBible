package com.ikalangirajeev.telugubiblemessages.ui.bible.app;


import com.google.gson.annotations.SerializedName;


import java.util.List;

public class Chapter {

    @SerializedName("verse")
    private List<Verse> verses;

    public Chapter() {
    }

    public Chapter(List<Verse> verses) {
        this.verses = verses;
    }

    public List<Verse> getVerses() {
        return verses;
    }
}
