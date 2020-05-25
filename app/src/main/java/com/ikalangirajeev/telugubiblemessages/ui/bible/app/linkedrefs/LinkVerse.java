package com.ikalangirajeev.telugubiblemessages.ui.bible.app.linkedrefs;

public class LinkVerse {
private String header;
private String body;

    public LinkVerse(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
