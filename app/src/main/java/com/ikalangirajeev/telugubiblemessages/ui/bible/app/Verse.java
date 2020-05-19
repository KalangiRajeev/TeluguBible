package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

import com.google.gson.annotations.SerializedName;

public class Verse {

    @SerializedName("Verse")
    private String verse;

    @SerializedName("Verseid")
    private String verseId;

    public Verse() {
    }

    public Verse(String verse, String verseId) {
        this.verse = verse;
        this.verseId = verseId;
    }

    public String getVerse() {
        return verse;
    }

    public String getVerseId() {
        return verseId;
    }
}
