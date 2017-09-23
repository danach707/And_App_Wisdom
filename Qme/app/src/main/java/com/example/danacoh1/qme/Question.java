package com.example.danacoh1.qme;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by danacoh1 on 5/26/2017.
 */

public class Question {
    private String UID;
    private String question;
    private long yes_counter, no_counter;
    private String id;
    private String type;
    private Comments comments;
    private String questionOwner;


    //=============================================================================================

    public Question(){
        question = Constants.NULL_STRING;
        yes_counter = 0;
        type = "General";
        no_counter = 0;
        comments = new Comments();
        questionOwner = "";
    }

    public Question(String question) {
        this.question = question;
        type = "General";
        yes_counter = 0;
        no_counter = 0;
        comments = new Comments();
        questionOwner = "";
    }
    public Question(String question, String type) {
        this.question = question;
        this.type = type;
        yes_counter = 0;
        no_counter = 0;
        comments = new Comments();
        questionOwner = "";
    }

    //=============================================================================================

    public String getQuestion() {
        return question;
    }

    //=============================================================================================

    public void setQuestion(String question) {
        this.question = question;
    }

    //=============================================================================================

    public long getYes_counter() {
        return yes_counter;
    }

    //=============================================================================================

    public void setYes_counter(long yes_counter) {
        this.yes_counter = yes_counter;
    }

    //=============================================================================================

    public long getNo_counter() {
        return no_counter;
    }

    //=============================================================================================

    public void setNo_counter(long no_counter) {
        this.no_counter = no_counter;
    }

    //=============================================================================================

    public String getId() {
        return id;
    }


    //=============================================================================================

    public void setId(String id) {
        this.id = id;
    }

    //=============================================================================================

    public String getType() {return type;}

    //=============================================================================================

    public void setType(String type) {this.type = type;}

    //=============================================================================================

    public String getQuestionOwner() {
        return questionOwner;
    }

    //=============================================================================================

    public void setQuestionOwner(String questionOwner) {
        this.questionOwner = questionOwner;
    }

    //=============================================================================================

    public String toString(){
        return "id: " + id + "\nquestionView: " + question + "\nyesC: " + yes_counter + "\nnoC: " + no_counter;
    }

    //=============================================================================================

    public Comments getComments() {
        return comments;
    }

}

