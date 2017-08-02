package com.example.danacoh1.qme;

import java.util.ArrayList;

/**
 * Created by danacoh1 on 6/5/2017.
 */

public class Comments {

    private ArrayList<String> comments;
    private String id;

    public Comments(){
        comments = new ArrayList<>();
    }

    public void addComment(String comment){
        comments.add(comment);
    }

    public String getCommentByIndex(int index){
        return comments.get(index);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
