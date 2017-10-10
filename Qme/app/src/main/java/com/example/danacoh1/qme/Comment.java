package com.example.danacoh1.qme;

import java.util.ArrayList;

/**
 * Created by danacoh1 on 6/5/2017.
 */

public class Comment {

    private String com_text;
    private String id;
    private String userId;
    private String questionId;
    private int num_of_likes;
    private int num_of_unlikes;

    public Comment() {
    }

    public Comment(String text) {
        this.com_text = text;
        this.num_of_likes = 0;
        this.num_of_unlikes = 0;
    }

    public Comment(String com_text, String id, String userId, String questionId) {
        this.com_text = com_text;
        this.id = id;
        this.userId = userId;
        this.questionId = questionId;
        this.num_of_likes = 0;
        this.num_of_unlikes = 0;
    }

    public String getCom_text() {return com_text;}
    public void setCom_text(String com_text) {this.com_text = com_text;}
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}
    public String getQuestionId() {return questionId;}
    public void setQuestionId(String questionId) {this.questionId = questionId;}
    public int getNum_of_likes() {return num_of_likes;}
    public void setNum_of_likes(int num_of_likes) {this.num_of_likes = num_of_likes;}
    public int getNum_of_unlikes() {return num_of_unlikes;}
    public void setNum_of_unlikes(int num_of_unlikes) {this.num_of_unlikes = num_of_unlikes;}
}
