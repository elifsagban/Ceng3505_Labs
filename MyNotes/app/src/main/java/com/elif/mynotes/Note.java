package com.elif.mynotes;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;

public class Note {
    @Exclude
    private String id;
    private Timestamp date;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
