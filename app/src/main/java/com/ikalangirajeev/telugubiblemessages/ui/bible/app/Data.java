package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

public class Data {
    private String header;
    private String body;
    private int refsLinks;


    public Data(String header, String body, int refsLinks) {
        this.header = header;
        this.body = body;
        this.refsLinks = refsLinks;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public int getRefsLinks() {
        return refsLinks;
    }
}
