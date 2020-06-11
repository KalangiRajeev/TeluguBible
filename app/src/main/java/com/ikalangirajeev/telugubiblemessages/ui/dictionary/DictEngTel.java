package com.ikalangirajeev.telugubiblemessages.ui.dictionary;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "eng2te")
public class DictEngTel {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @NonNull
    private String eng_word;

    private String pos;

    private String pos_type;
    @NonNull
    private String meaning;

    public DictEngTel(String eng_word, String pos, String pos_type, String meaning) {
        this.eng_word = eng_word;
        this.pos = pos;
        this.pos_type = pos_type;
        this.meaning = meaning;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEng_word() {
        return eng_word;
    }

    public String getPos() {
        return pos;
    }

    public String getPos_type() {
        return pos_type;
    }

    public String getMeaning() {
        return meaning;
    }
}
