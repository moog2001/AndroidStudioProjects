package com.example.languagecard;

import java.io.Serializable;

public class Word implements Serializable {
    private String mongolia;
    private String english;
    long id;

    public Word(String mongolia, String english, DataBaseHandler save) {
        this.mongolia = mongolia;
        this.english = english;
        setId(save.insertData(this));
    }

    public Word(){

    }



    public String getMongolia() {
        return mongolia;
    }

    public void setMongolia(String mongolia) {
        this.mongolia = mongolia;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
