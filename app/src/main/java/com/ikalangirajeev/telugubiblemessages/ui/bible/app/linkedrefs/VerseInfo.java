package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

public class VerseInfo {
    private Integer verseId;
    private Integer bookNumber;
    private Integer chapterNumber;
    private Integer verseNumber;

    public VerseInfo(Integer verseId, Integer bookNumber, Integer chapterNumber, Integer verseNumber) {
        this.verseId = verseId;
        this.bookNumber = bookNumber;
        this.chapterNumber = chapterNumber;
        this.verseNumber = verseNumber;
    }

    public Integer getVerseId() {
        return verseId;
    }

    public Integer getBookNumber() {
        return bookNumber;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public Integer getVerseNumber() {
        return verseNumber;
    }

}
