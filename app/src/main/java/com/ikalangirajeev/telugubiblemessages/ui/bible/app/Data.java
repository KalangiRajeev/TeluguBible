package com.ikalangirajeev.telugubiblemessages.ui.bible.app;

public class Data {
    private String header;
    private String body;
    private int refLink;


    public Data(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public Data(String header, String body, int refLink) {
        this.header = header;
        this.body = body;
        this.refLink = refLink;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public int getRefLink() {
        return refLink;
    }
}
