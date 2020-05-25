package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

import com.google.gson.annotations.SerializedName;

public class Verse {

    @SerializedName("verse")
    private String verse;

    @SerializedName("verseid")
    private Integer verseId;

    public Verse() {
    }

    public Verse(String verse, Integer verseId) {
        this.verse = verse;
        this.verseId = verseId;
    }

    public String getVerse() {
        return verse;
    }

    public Integer getVerseId() {
        return verseId;
    }
}
