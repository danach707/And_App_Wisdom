package com.example.danacoh1.qme;

/**
 * Created by danacoh1 on 5/26/2017.
 */

public class Question {

    private String question;
    private int yes_counter, no_counter;
    private String id;

    public Question(String question) {
        this.question = question;
        yes_counter = 0;
        no_counter = 0;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getYes_counter() {
        return yes_counter;
    }

    public void setYes_counter(int yes_counter) {
        this.yes_counter = yes_counter;
    }

    public int getNo_counter() {
        return no_counter;
    }

    public void setNo_counter(int no_counter) {
        this.no_counter = no_counter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
